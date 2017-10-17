package de.tud.robotics.ur.client.data;

import com.igormaznitsa.jbbp.mapper.Bin;
import com.igormaznitsa.jbbp.mapper.BinType;

import de.tud.robotics.ur.client.RobotPackageType;
import de.tud.robotics.ur.client.ToolMode;

public class ToolData extends RobotPackageData {
	
	public static int packageLength = 37;
	public static RobotPackageType packageType = RobotPackageType.TOOL_DATA;
	
	@Bin(type=BinType.UBYTE) private int analogInputRange2;
	@Bin(type=BinType.UBYTE) private int analogInputRange3;
	@Bin(custom=true) private double analogInput2;		
	@Bin(custom=true) private double analogInput3;
	@Bin(custom=true) private float	toolVoltage48V;		
	@Bin(type=BinType.UBYTE) private int toolOutputVoltage;
	@Bin(custom=true) private float toolCurrent;		
	@Bin(custom=true) private float	toolTemperature;
	@Bin(type=BinType.UBYTE) private int toolMode;
	public int getAnalogInputRange2() {
		return analogInputRange2;
	}
	public int getAnalogInputRange3() {
		return analogInputRange3;
	}
	public double getAnalogInput2() {
		return analogInput2;
	}
	public double getAnalogInput3() {
		return analogInput3;
	}
	public float getToolVoltage48V() {
		return toolVoltage48V;
	}
	public int getToolOutputVoltage() {
		return toolOutputVoltage;
	}
	public float getToolCurrent() {
		return toolCurrent;
	}
	public float getToolTemperature() {
		return toolTemperature;
	}
	public ToolMode getToolMode() {
		return ToolMode.from(toolMode);
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
		temp = Double.doubleToLongBits(analogInput2);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(analogInput3);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + analogInputRange2;
		result = prime * result + analogInputRange3;
		result = prime * result + Float.floatToIntBits(toolCurrent);
		result = prime * result + toolMode;
		result = prime * result + toolOutputVoltage;
		result = prime * result + Float.floatToIntBits(toolTemperature);
		result = prime * result + Float.floatToIntBits(toolVoltage48V);
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
		ToolData other = (ToolData) obj;
		if (Double.doubleToLongBits(analogInput2) != Double.doubleToLongBits(other.analogInput2))
			return false;
		if (Double.doubleToLongBits(analogInput3) != Double.doubleToLongBits(other.analogInput3))
			return false;
		if (analogInputRange2 != other.analogInputRange2)
			return false;
		if (analogInputRange3 != other.analogInputRange3)
			return false;
		if (Float.floatToIntBits(toolCurrent) != Float.floatToIntBits(other.toolCurrent))
			return false;
		if (toolMode != other.toolMode)
			return false;
		if (toolOutputVoltage != other.toolOutputVoltage)
			return false;
		if (Float.floatToIntBits(toolTemperature) != Float.floatToIntBits(other.toolTemperature))
			return false;
		if (Float.floatToIntBits(toolVoltage48V) != Float.floatToIntBits(other.toolVoltage48V))
			return false;
		return true;
	}
	
	
}
