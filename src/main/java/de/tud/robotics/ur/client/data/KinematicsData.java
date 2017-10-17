package de.tud.robotics.ur.client.data;

import de.tud.robotics.ur.client.RobotPackageType;

public class KinematicsData extends RobotPackageData {

	public static int packageLength = 225;
	public static RobotPackageType packageType = RobotPackageType.KINEMATICS_DATA;
	
	@Override
	public String getRobotPackageType() {
		return packageType.toString();
	}
}
