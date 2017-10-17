package de.tud.robotics.ur.strategy;

import de.tud.robotics.ur.api.JointPosition;

public abstract class AbstractServoMotionStrategy implements MotionStrategy {
	
	protected float minLookaheadTime = 0.03f;
	protected float maxLookaheadTime = 0.2f;
	
	protected int minGain = 100;
	protected int maxGain = 400;
	// speed in rad/s
	protected double minSpeed = Math.toRadians(1);
	protected double maxSpeed = Math.toRadians(190);

	private float rangeLookheadTime(float lookaheadTime) {
		lookaheadTime = Math.min(maxLookaheadTime, lookaheadTime);
		lookaheadTime = Math.max(minLookaheadTime,lookaheadTime);
		return lookaheadTime;
	}
	
	private int rangeGain(int gain) {
		gain = Math.min(maxGain, gain);
		gain = Math.max(minGain,gain);
		return gain;
	}
	
	protected float speedFunction(double distance) {
		return (float) Math.min(maxSpeed, speedFunctionIntern(distance, 0.1));
	}
	
	protected float speedFunction(double distance, double speedPercentage) {
		float speed = (float)Math.min(maxSpeed * speedPercentage, speedFunctionIntern(distance, speedPercentage));
		return speed;
	}

	protected abstract float speedFunctionIntern(double distance, double speedPercentage);

	protected abstract double calcDistanceIntern(JointPosition currentPosition, JointPosition targetPosition);
	
	@Override
	public double calcDistance(JointPosition currentPosition, JointPosition targetPosition) {
		return Math.abs(calcDistanceIntern(currentPosition, targetPosition));
	}

	protected abstract float calcLookaheadTimeIntern(float time);
	
	@Override
	public float calcLookaheadTime(float time) {
		return rangeLookheadTime(calcLookaheadTimeIntern(time));
	}

	protected abstract int calcGainIntern(double speed);
	
	@Override
	public int calcGain(double endSpeed) {
		return rangeGain(calcGainIntern(endSpeed));
	}
	
	protected abstract float calcTimeIntern(double distance);
	
	@Override
	public float calcTime(double distance) {
		return calcTime(distance, 0.1);
	}
	
	protected abstract float calcTimeIntern(double distance, double speedPercentage);
	
	@Override
	public float calcTime(double distance, double speedPercentage) {
		double time = Math.max(0.008f, calcTimeIntern(distance, speedPercentage));
		return (float)time;
	}
	
	public int getMinGain() {
		return minGain;
	}

	public void setMinGain(int minGain) {
		this.minGain = minGain;
	}

	public int getMaxGain() {
		return maxGain;
	}

	public void setMaxGain(int maxGain) {
		this.maxGain = maxGain;
	}

	public double getMinSpeed() {
		return minSpeed;
	}

	public void setMinSpeed(double minSpeed) {
		this.minSpeed = minSpeed;
	}

	public double getMaxSpeed() {
		return maxSpeed;
	}

	public void setMaxSpeed(double maxSpeed) {
		this.maxSpeed = maxSpeed;
	}
	
}
