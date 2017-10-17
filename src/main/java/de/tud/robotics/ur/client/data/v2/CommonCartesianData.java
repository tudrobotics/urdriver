package de.tud.robotics.ur.client.data.v2;

import com.igormaznitsa.jbbp.mapper.Bin;

import de.tud.robotics.ur.client.RobotPackageType;

public abstract class CommonCartesianData extends RobotPackageData {
	
	@Bin(custom=true)
	protected double x;
	@Bin(custom=true)
	protected double y;
	@Bin(custom=true)
	protected double z;		
	@Bin(custom=true)
	protected double rx;			
	@Bin(custom=true)
	protected double ry;	
	@Bin(custom=true)
	protected double rz;		
	
	public CommonCartesianData(String sender) {
		super(sender);
	}
	
	public double getX() {
		return x*1000d;
	}
	public double getY() {
		return y*1000d;
	}
	public double getZ() {
		return z*1000d;
	}
	public double getRx() {
		return rx;
	}
	public double getRy() {
		return ry;
	}
	public double getRz() {
		return rz;
	}
	
	
}
