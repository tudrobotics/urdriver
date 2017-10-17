package de.tud.robotics.ur;

import java.util.logging.Logger;

import org.apache.commons.lang3.Validate;

import de.tud.robotics.ur.api.JointPosition;
import de.tud.robotics.ur.api.MotionExtended;
import de.tud.robotics.ur.strategy.DefaultServoMotionStrategy;
import de.tud.robotics.ur.strategy.MotionStrategy;

public abstract class AbstractURServoControl {

	protected static final Logger LOG = Logger.getLogger(AbstractURServoControl.class.getSimpleName());

	protected CommonURClient client;

	protected float time;
	protected float lookaheadTime;
	protected int gain;

	protected volatile JointPosition jointPosition;

	protected JointPosition lastJointPosition;
	
	protected JointPosition robotJointPosition;
	protected double distance = 0;
	protected double speed = 0;
	private double lastSpeed;
	protected double[] velocity;
	
	private static int a = -1;
	private static final int v = -1;

	private volatile MotionStrategy strategy;

	private URServoThread thread;
	
	private volatile float speedPercentage = 0.5f;

	private MotionExtended mx;
	
	public AbstractURServoControl(CommonURClient client) {
		this.client = client;
		Validate.notNull(client);
		this.strategy = new DefaultServoMotionStrategy();
		this.mx = client.getProxy(MotionExtended.class);
	}

	public void setTarget(JointPosition pos) {
		if (jointPosition != null && pos.equals(jointPosition, 5))
			return;
		this.jointPosition = pos;
		synchronized (thread) {
			thread.notify();
		}
	}
	
	public void setSpeedPercentage(float speedPercentage) {
		this.speedPercentage = speedPercentage;
	}

	public String getName() {
		return client.getName();
	}
	public void setStrategy(MotionStrategy strategy) {
		this.strategy = strategy;
	}

	public void start() {
		if(thread == null) thread = new URServoThread();
		if(thread.isAlive()) return;
		thread.start();
	}
	
	public void dispose() {
		thread.interrupt();
		synchronized (thread) {
			thread.notify();
		}
		thread = null;
	}

	private class URServoThread extends Thread {

		private int repeatCount;
		
		public URServoThread() {
			super(client.getName() + "-URServoThread");
		}

		@Override
		public void run() {
			while (!isInterrupted()) {
				if (jointPosition == null) {
					syncWait(1000);
					continue;
				}
				robotJointPosition = client.getJointData().toJointPosition();
				velocity = client.getJointData().jointVelocityArray();
				
				jointPosition = manipulateTarget(jointPosition);
				
				distance = strategy.calcDistance(robotJointPosition,jointPosition);
				time = strategy.calcTime(distance, speedPercentage);
				lookaheadTime = strategy.calcLookaheadTime(time);
				lastSpeed = speed;
				speed = distance * time;
				// check if the new speed is more than 10 % higher than the speed of the last motion
				/// than cap the times
				/*if(lastSpeed != 0.0 ) {
					if(speed > lastSpeed * 1.1d) {
						time = (float) (lastSpeed * 1.1d / distance);
						speed = lastSpeed *1.10d;
					}
					if(speed < lastSpeed * 0.9d) {
						time = (float) (lastSpeed * 0.9d / distance);
						speed = lastSpeed *0.9d;
					}
				}*/
				gain = strategy.calcGain(speed);
				// get current velocity
				for (int i = 0; i < velocity.length; i++) {
					if (velocity[i] < 0 && jointPosition.getJoint(i) - robotJointPosition.getJoint(i) > 0) {
						// es ist eine Richtungsänderung eines Joints
						time = time + (0.1f * time);
						break;
					}
				}
				//LOG.log(Level.FINER, distance + " " + time + "   " + lookaheadTime + "   " + gain);
				//System.out.println("NEUERPUNKT: " + distance + " " + time + "   " + lookaheadTime + "   " + gain);

				mx.servoj(jointPosition, a, v, time, lookaheadTime, gain);
				if(jointPosition.equals(lastJointPosition)) {
					repeatCount++;
					// stop sending position after 8s
					if(repeatCount > 1000) jointPosition = null;
				} else {
					lastJointPosition = jointPosition;
					repeatCount = 0;
				}
				try {
					Thread.sleep(8);
				} catch (InterruptedException e) {
					return;
				}
			}
		}

		private void syncWait(long time) {
			synchronized (this) {
				try {
					this.wait(time);
				} catch (InterruptedException e) {
				}
			}
		}
	}
	protected abstract JointPosition manipulateTarget(JointPosition pos);

}
