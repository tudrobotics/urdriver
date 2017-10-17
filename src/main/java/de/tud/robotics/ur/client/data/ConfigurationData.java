package de.tud.robotics.ur.client.data;

import de.tud.robotics.ur.client.RobotPackageType;

public class ConfigurationData extends RobotPackageData {

	/*
	 * because the Parser try to map inherited attributes 
	 */
	public static int packageLength = 445;
	public static RobotPackageType packageType = RobotPackageType.CONFIGURATION_DATA;
	
	@Override
	public String getRobotPackageType() {
		return packageType.toString();
	}
}
