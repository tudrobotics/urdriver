package de.tud.robotics.ur.strategy;

public class NewServoMotionStrategy extends DefaultServoMotionStrategy {

	protected float calcLookaheadTimeIntern(float time) {
		return time / 15f;
	}
	protected int calcGainIntern(double speed) {
		return (int) (200.0 * speed);
	}
	
	/*
	@Override
	protected int calcGainIntern(double speed) {
		// damit mein der maxgain nur bis 1000
		//int minSpeed = 10;
		//int maxSpeed = 60;
		// log funktion umgestellt
		double a = Math.pow(minSpeed/maxSpeed, 1/(minGain - maxGain));
		return (int) MathHelper.logb(speed, a);
	}*/
	

	/**
	 * t = s/v
	 * returns time in s
	 */
	protected float calcTimeIntern(double distance) {
		return calcTime(distance, 0.1);
	}
	
	/**
	 * t = s/v
	 * returns time in s
	 */
	protected float calcTimeIntern(double distance, double speedPercentage) {
		return (float) distance/speedFunction(distance, speedPercentage);
	}
	
	/**
	 * version 1 großer anstieg
	 * @param distance
	 * @return
	 */
	protected float speedFunctionIntern(double distance, double speedPercentage) {
		if (speedPercentage <= 0.0)
			speedPercentage = 0.05;
		speedPercentage = 4.0;
		double speedM = maxSpeed * speedPercentage;
		double v1 = speedM / 1.3;
		double vp = 0.2;
		double zw = Math.log( (speedM - v1) / (speedM * (v1 + vp)));		
		float speed = (float)( (1.0 / speedM) * (speedM + vp) / ( (1.0 / speedM) + Math.exp( zw * distance ) ) - vp);
		return speed;
	}
		
}
