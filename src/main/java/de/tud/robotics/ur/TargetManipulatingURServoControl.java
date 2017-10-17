package de.tud.robotics.ur;

import java.util.logging.Logger;

import org.apache.commons.lang3.Validate;

import de.tud.robotics.ur.api.JointPosition;
import de.tud.robotics.ur.api.MotionExtended;
import de.tud.robotics.ur.strategy.DefaultServoMotionStrategy;
import de.tud.robotics.ur.strategy.MotionStrategy;

public class TargetManipulatingURServoControl extends AbstractURServoControl {

	/**
	 * max allowed acceleration per joint in rad /s^2
	 */
	private static final double[] allowedAcceleration = new double[]{2.0,2.0,3.0,3.0,4.0,4.0};
	
	public TargetManipulatingURServoControl(CommonURClient client) {
		super(client);
	}
	
	/**
	 * Impuls - Drehimpuls
	 * Winkelgeschwindigkeit * Trägheitsmoment --> masse
	 * 
	 */
	@Override
	protected JointPosition manipulateTarget(JointPosition pos) {
		double expectedTime;
		double accelleration;
		// kann ich basierend aud dem weg die ich pro joint fahren muss überhaupt die momentan geschindigkeit halten
		//S=a/2 t^2 + v t + x
		// V = geschwindigkeit
		// 
		for (int i = 0; i < robotJointPosition.toArray().length; i++) {
			distance = Math.abs(robotJointPosition.getJoint(i) - pos.getJoint(i));
			expectedTime = Math.abs(distance / (velocity[i] + 0.000000000000000001));
			accelleration = distance / ((expectedTime * expectedTime) + 0.0000000000000000001);
			if(accelleration > allowedAcceleration[i]) {
				//System.out.println("joint änderung "+i+" "+pos.getJoint(i)+"-->"+(allowedAcceleration[i]/2.0 * expectedTime * expectedTime + velocity[i] * expectedTime + pos.getJoint(i)));
				pos.setJoint(i, allowedAcceleration[i]/2.0d * expectedTime * expectedTime + velocity[i] * expectedTime + pos.getJoint(i) );
			}
		}
		return pos;
	}


}
