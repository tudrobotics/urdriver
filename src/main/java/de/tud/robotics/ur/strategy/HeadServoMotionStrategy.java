package de.tud.robotics.ur.strategy;

import de.tud.robotics.ur.api.JointPosition;

public class HeadServoMotionStrategy extends DefaultServoMotionStrategy {

	/**
	 * is the max distance of the Joints Wrist_1, Wrist_2, Wrist_3
	 */
	@Override
	protected double calcDistanceIntern(JointPosition currentPosition, JointPosition targetPosition) {		
		double distance = 0;
		double temp = 0;
		for(int i = 3;i < 6;i++) {
			temp = Math.abs(targetPosition.getJoint(i)-currentPosition.getJoint(i));
			distance = Math.max(distance, temp);
		}
		return distance;
	}
	
	@Override
	protected int calcGainIntern(double speed) {
		return 500;		
	}
	
	@Override
	protected float calcLookaheadTimeIntern(float time) {
		return 0.03f;
	}

}
