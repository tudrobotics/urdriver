package de.tud.robotics.ur.strategy;

import de.tud.robotics.ur.api.JointPosition;

public interface MotionStrategy {

	double calcDistance(JointPosition currentPosition, JointPosition targetPosition);
	
	float calcLookaheadTime(float time);
	
	int calcGain(double endSpeed);
	
	float calcTime(double distance);
	
	float calcTime(double distance, double currentSpeed);
	
	
}
