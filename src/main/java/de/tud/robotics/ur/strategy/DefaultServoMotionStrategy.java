package de.tud.robotics.ur.strategy;

import de.tud.robotics.ur.api.JointPosition;

public class DefaultServoMotionStrategy extends AbstractServoMotionStrategy {

	protected float calcLookaheadTimeIntern(float time) {
		return time / 25f;
	}
	protected int calcGainIntern(double speed) {
		return 300;
	}
	/**
	 * t = s/v
	 * returns time in s
	 */
	protected float calcTimeIntern(double distance) {
		return calcTimeIntern(distance, 0.1);
	}
	@Override
	protected float calcTimeIntern(double distance, double speedPercentage) {
		return (float) distance/speedFunction(distance, speedPercentage);
	}
	/**
	 * lineare geschwindigkeitsfunktion
	 * v
	 * @param distance
	 * @return
	 */
	protected float speedFunctionIntern(double distance, double speedPercentage) {
		 return (float) (30f * Math.min(speedPercentage, 1.0) * distance + 0.008f);		 
	}
	/**
	 * distance die max distance aus den ersten 4 joints
	 */
	protected double calcDistanceIntern(JointPosition currentPosition, JointPosition targetPosition) {
		double distance = 0;
		double temp = 0;
		for(int i = 0;i < 6;i++) {
			temp = Math.abs(targetPosition.getJoint(i)-currentPosition.getJoint(i));
			distance = Math.max(distance, temp);
		}
		return distance;
	}
	
}
