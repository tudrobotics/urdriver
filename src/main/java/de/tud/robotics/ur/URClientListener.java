package de.tud.robotics.ur;

import de.tud.robotics.ur.client.data.RobotPackageData;

public interface URClientListener {

	void notify(RobotPackageData data);

}
