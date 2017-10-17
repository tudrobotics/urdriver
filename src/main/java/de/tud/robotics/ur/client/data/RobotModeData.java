package de.tud.robotics.ur.client.data;

import com.igormaznitsa.jbbp.mapper.Bin;
import com.igormaznitsa.jbbp.mapper.BinType;

import de.tud.robotics.ur.client.ControlMode;
import de.tud.robotics.ur.client.RobotMode;
import de.tud.robotics.ur.client.RobotPackageType;


public class RobotModeData extends RobotPackageData {

	public static int packageLength = 46;
	public static RobotPackageType packageType = RobotPackageType.ROBOT_MODE_DATA;
	
	@Bin
	protected long timestamp;
	@Bin
	protected boolean physicalRobotConnected;
	@Bin
	protected boolean realRobotEnabled;
	@Bin
	protected boolean robotPowerOn;
	@Bin
	protected boolean emergencyStopped;
	@Bin
	protected boolean protectiveStopped;
	@Bin
	protected boolean programRunning;
	@Bin
	protected boolean programPaused;
	@Bin(type = BinType.UBYTE)
	protected int robotMode;
	@Bin(type = BinType.UBYTE)
	protected int controlMode;
	@Bin(custom = true)
	protected double targetSpeedFraction;
	@Bin(custom = true)
	protected double speedScaling;
	@Bin(custom = true)
	protected double targetSpeedFractionLimit;
	
	public long getTimestamp() {
		return timestamp;
	}
	public boolean isPhysicalRobotConnected() {
		return physicalRobotConnected;
	}
	public boolean isRealRobotEnabled() {
		return realRobotEnabled;
	}
	public boolean isRobotPowerOn() {
		return robotPowerOn;
	}
	public boolean isEmergencyStopped() {
		return emergencyStopped;
	}
	public boolean isProtectiveStopped() {
		return protectiveStopped;
	}
	public boolean isProgramRunning() {
		return programRunning;
	}
	public boolean isProgramPaused() {
		return programPaused;
	}
	public RobotMode getRobotMode() {
		return RobotMode.from(robotMode);
	}
	public ControlMode getControlMode() {
		return ControlMode.from(controlMode);
	}
	public double getTargetSpeedFraction() {
		return targetSpeedFraction;
	}
	public double getSpeedScaling() {
		return speedScaling;
	}
	public double getTargetSpeedFractionLimit() {
		return targetSpeedFractionLimit;
	}
	
	@Override
	public String getRobotPackageType() {
		return packageType.toString();
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + controlMode;
		result = prime * result + (emergencyStopped ? 1231 : 1237);
		result = prime * result + (physicalRobotConnected ? 1231 : 1237);
		result = prime * result + (programPaused ? 1231 : 1237);
		result = prime * result + (programRunning ? 1231 : 1237);
		result = prime * result + (protectiveStopped ? 1231 : 1237);
		result = prime * result + (realRobotEnabled ? 1231 : 1237);
		result = prime * result + robotMode;
		result = prime * result + (robotPowerOn ? 1231 : 1237);
		long temp;
		temp = Double.doubleToLongBits(speedScaling);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(targetSpeedFraction);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(targetSpeedFractionLimit);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		RobotModeData other = (RobotModeData) obj;
		if (controlMode != other.controlMode)
			return false;
		if (emergencyStopped != other.emergencyStopped)
			return false;
		if (physicalRobotConnected != other.physicalRobotConnected)
			return false;
		if (programPaused != other.programPaused)
			return false;
		if (programRunning != other.programRunning)
			return false;
		if (protectiveStopped != other.protectiveStopped)
			return false;
		if (realRobotEnabled != other.realRobotEnabled)
			return false;
		if (robotMode != other.robotMode)
			return false;
		if (robotPowerOn != other.robotPowerOn)
			return false;
		if (Double.doubleToLongBits(speedScaling) != Double.doubleToLongBits(other.speedScaling))
			return false;
		if (Double.doubleToLongBits(targetSpeedFraction) != Double.doubleToLongBits(other.targetSpeedFraction))
			return false;
		if (Double.doubleToLongBits(targetSpeedFractionLimit) != Double
				.doubleToLongBits(other.targetSpeedFractionLimit))
			return false;
		return true;
	}
	
	
	
	
}