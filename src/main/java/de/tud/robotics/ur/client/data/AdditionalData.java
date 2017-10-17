package de.tud.robotics.ur.client.data;

import com.igormaznitsa.jbbp.mapper.Bin;

import de.tud.robotics.ur.client.RobotPackageType;

public class AdditionalData extends RobotPackageData {

	/*
	 * because the Parser try to map inherited attributes 
	 */
	public static int packageLength = 8;
	public static RobotPackageType packageType = RobotPackageType.ADDITIONAL_INFO;
	
	@Bin private boolean teachButtonPressed;
	@Bin private boolean teachButtonEnabled;			
	@Bin private boolean ioEnabledFreedrive;
	
	public boolean isTeachButtonPressed() {
		return teachButtonPressed;
	}
	public boolean isTeachButtonEnabled() {
		return teachButtonEnabled;
	}
	public boolean isIoEnabledFreedrive() {
		return ioEnabledFreedrive;
	}
	@Override
	public String getRobotPackageType() {
		return packageType.toString();
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (ioEnabledFreedrive ? 1231 : 1237);
		result = prime * result + (teachButtonEnabled ? 1231 : 1237);
		result = prime * result + (teachButtonPressed ? 1231 : 1237);
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AdditionalData other = (AdditionalData) obj;
		if (ioEnabledFreedrive != other.ioEnabledFreedrive)
			return false;
		if (teachButtonEnabled != other.teachButtonEnabled)
			return false;
		if (teachButtonPressed != other.teachButtonPressed)
			return false;
		return true;
	}
	
	
}
