package de.tud.robotics.ur.client.data.v2;

import com.igormaznitsa.jbbp.mapper.Bin;

import de.tud.robotics.ur.client.RobotPackageType;


public final class ForceModeData extends RobotPackageData {

	@Bin(custom=true)private double x;
	@Bin(custom=true)private double y;
	@Bin(custom=true)private double z;
	@Bin(custom=true)private double rx;			
	@Bin(custom=true)private double ry;		
	@Bin(custom=true)private double rz;		
	@Bin(custom=true)private double robotDexterity;
	
	public ForceModeData(String sender) {
		super(sender);
	}
	
	public double getX() {
		return x;
	}
	public double getY() {
		return y;
	}
	public double getZ() {
		return z;
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
	public double getRobotDexterity() {
		return robotDexterity;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(robotDexterity);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(rx);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(ry);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(rz);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(x);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(y);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(z);
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
		ForceModeData other = (ForceModeData) obj;
		if (Double.doubleToLongBits(robotDexterity) != Double.doubleToLongBits(other.robotDexterity))
			return false;
		if (Double.doubleToLongBits(rx) != Double.doubleToLongBits(other.rx))
			return false;
		if (Double.doubleToLongBits(ry) != Double.doubleToLongBits(other.ry))
			return false;
		if (Double.doubleToLongBits(rz) != Double.doubleToLongBits(other.rz))
			return false;
		if (Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x))
			return false;
		if (Double.doubleToLongBits(y) != Double.doubleToLongBits(other.y))
			return false;
		if (Double.doubleToLongBits(z) != Double.doubleToLongBits(other.z))
			return false;
		return true;
	}
	
	
}
