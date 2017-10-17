package de.tud.robotics.ur;

import java.util.logging.Logger;

import org.apache.commons.lang3.Validate;

import de.tud.robotics.ur.api.JointPosition;
import de.tud.robotics.ur.api.MotionExtended;
import de.tud.robotics.ur.strategy.DefaultServoMotionStrategy;
import de.tud.robotics.ur.strategy.MotionStrategy;

public class URServoControl extends AbstractURServoControl {
	
	public URServoControl(CommonURClient client) {
		super(client);
	}

	@Override
	protected JointPosition manipulateTarget(JointPosition pos) {
		return pos;
	}


}
