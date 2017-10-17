package de.tud.robotics.ur.client.data;

import com.igormaznitsa.jbbp.mapper.Bin;
import com.igormaznitsa.jbbp.mapper.BinType;

import de.tud.robotics.ur.client.RobotPackageType;
import de.tud.robotics.ur.client.SafetyMode;

public class MasterboardData extends RobotPackageData {

	public static int packageLength = 74;
	public static RobotPackageType packageType = RobotPackageType.MASTERBOARD_DATA;
	
	@Bin private int digitalInputBits;
	@Bin private int digitalOutputBits;
	@Bin private byte analogInputRange0;
	@Bin private byte analogInputRange1;
	@Bin(custom=true) private double analogInput0;
	@Bin(custom=true) private double analogInput1;
	@Bin private byte analogOutputDomain0;
	@Bin private byte analogOutputDomain1;
	@Bin(custom=true)private double analogOutput0;
	@Bin(custom=true)private double AnalogOutput1;
	@Bin(custom=true)private float masterboardTemperature;
	@Bin(custom=true)private float robotVoltage48V;
	@Bin(custom=true)private float robotCurrent;
	@Bin(custom=true)private float masterIOCurrent;
	@Bin(type=BinType.UBYTE)private int safetymode;
	@Bin(type=BinType.UBYTE)private int inReducedMode;
	@Bin private byte euromap67Installed;
	@Bin private int unknown;
	@Bin(type=BinType.UBYTE) private int operationalModeSelectorInput;
	@Bin(type=BinType.UBYTE) private int threePositionEnablingDeviceInput;
	
	public int getDigitalInputBits() {
		return digitalInputBits;
	}
	public int getDigitalOutputBits() {
		return digitalOutputBits;
	}
	public byte getAnalogInputRange0() {
		return analogInputRange0;
	}
	public byte getAnalogInputRange1() {
		return analogInputRange1;
	}
	public double getAnalogInput0() {
		return analogInput0;
	}
	public double getAnalogInput1() {
		return analogInput1;
	}
	public byte getAnalogOutputDomain0() {
		return analogOutputDomain0;
	}
	public byte getAnalogOutputDomain1() {
		return analogOutputDomain1;
	}
	public double getAnalogOutput0() {
		return analogOutput0;
	}
	public double getAnalogOutput1() {
		return AnalogOutput1;
	}
	public float getMasterboardTemperature() {
		return masterboardTemperature;
	}
	public float getRobotVoltage48V() {
		return robotVoltage48V;
	}
	public float getRobotCurrent() {
		return robotCurrent;
	}
	public float getMasterIOCurrent() {
		return masterIOCurrent;
	}
	public SafetyMode getSafetymode() {
		return SafetyMode.from(safetymode);
	}
	public int getInReducedMode() {
		return inReducedMode;
	}
	public byte getEuromap67Installed() {
		return euromap67Installed;
	}
	public int getOperationalModeSelectorInput() {
		return operationalModeSelectorInput;
	}
	public int getThreePositionEnablingDeviceInput() {
		return threePositionEnablingDeviceInput;
	}
	
	@Override
	public String getRobotPackageType() {
		return packageType.toString();
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(AnalogOutput1);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(analogInput0);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(analogInput1);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + analogInputRange0;
		result = prime * result + analogInputRange1;
		temp = Double.doubleToLongBits(analogOutput0);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + analogOutputDomain0;
		result = prime * result + analogOutputDomain1;
		result = prime * result + digitalInputBits;
		result = prime * result + digitalOutputBits;
		result = prime * result + euromap67Installed;
		result = prime * result + inReducedMode;
		result = prime * result + Float.floatToIntBits(masterIOCurrent);
		result = prime * result + Float.floatToIntBits(masterboardTemperature);
		result = prime * result + operationalModeSelectorInput;
		result = prime * result + Float.floatToIntBits(robotCurrent);
		result = prime * result + Float.floatToIntBits(robotVoltage48V);
		result = prime * result + safetymode;
		result = prime * result + threePositionEnablingDeviceInput;
		result = prime * result + unknown;
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
		MasterboardData other = (MasterboardData) obj;
		if (Double.doubleToLongBits(AnalogOutput1) != Double.doubleToLongBits(other.AnalogOutput1))
			return false;
		if (Double.doubleToLongBits(analogInput0) != Double.doubleToLongBits(other.analogInput0))
			return false;
		if (Double.doubleToLongBits(analogInput1) != Double.doubleToLongBits(other.analogInput1))
			return false;
		if (analogInputRange0 != other.analogInputRange0)
			return false;
		if (analogInputRange1 != other.analogInputRange1)
			return false;
		if (Double.doubleToLongBits(analogOutput0) != Double.doubleToLongBits(other.analogOutput0))
			return false;
		if (analogOutputDomain0 != other.analogOutputDomain0)
			return false;
		if (analogOutputDomain1 != other.analogOutputDomain1)
			return false;
		if (digitalInputBits != other.digitalInputBits)
			return false;
		if (digitalOutputBits != other.digitalOutputBits)
			return false;
		if (euromap67Installed != other.euromap67Installed)
			return false;
		if (inReducedMode != other.inReducedMode)
			return false;
		if (Float.floatToIntBits(masterIOCurrent) != Float.floatToIntBits(other.masterIOCurrent))
			return false;
		if (Float.floatToIntBits(masterboardTemperature) != Float.floatToIntBits(other.masterboardTemperature))
			return false;
		if (operationalModeSelectorInput != other.operationalModeSelectorInput)
			return false;
		if (Float.floatToIntBits(robotCurrent) != Float.floatToIntBits(other.robotCurrent))
			return false;
		if (Float.floatToIntBits(robotVoltage48V) != Float.floatToIntBits(other.robotVoltage48V))
			return false;
		if (safetymode != other.safetymode)
			return false;
		if (threePositionEnablingDeviceInput != other.threePositionEnablingDeviceInput)
			return false;
		if (unknown != other.unknown)
			return false;
		return true;
	}
	
	
}
