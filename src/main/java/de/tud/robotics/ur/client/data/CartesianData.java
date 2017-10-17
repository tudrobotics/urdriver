package de.tud.robotics.ur.client.data;

import com.igormaznitsa.jbbp.mapper.Bin;

import de.tud.robotics.ur.client.RobotPackageType;

public class CartesianData extends RobotPackageData {
	
	public static int packageLength = 101;
	public static RobotPackageType packageType = RobotPackageType.CARTESIAN_DATA;
	
	@Bin(custom=true)protected double x;
	@Bin(custom=true)protected double y;
	@Bin(custom=true)protected double z;		
	@Bin(custom=true)protected double rx;			
	@Bin(custom=true)protected double ry;	
	@Bin(custom=true)protected double rz;		
	@Bin(custom=true)protected double tcpOffsetX;	
	@Bin(custom=true)protected double tcpOffsetY;
	@Bin(custom=true)protected double tcpOffsetZ;
	@Bin(custom=true)protected double tcpOffsetRX;
	@Bin(custom=true)protected double tcpOffsetRY;
	@Bin(custom=true)protected double tcpOffsetRZ;
	
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
	public double getTcpOffsetX() {
		return tcpOffsetX;
	}
	public double getTcpOffsetY() {
		return tcpOffsetY;
	}
	public double getTcpOffsetZ() {
		return tcpOffsetZ;
	}
	public double getTcpOffsetRX() {
		return tcpOffsetRX;
	}
	public double getTcpOffsetRY() {
		return tcpOffsetRY;
	}
	public double getTcpOffsetRZ() {
		return tcpOffsetRZ;
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
		temp = Double.doubleToLongBits(rx);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(ry);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(rz);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(tcpOffsetRX);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(tcpOffsetRY);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(tcpOffsetRZ);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(tcpOffsetX);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(tcpOffsetY);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(tcpOffsetZ);
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
		CartesianData other = (CartesianData) obj;
		if (Double.doubleToLongBits(rx) != Double.doubleToLongBits(other.rx))
			return false;
		if (Double.doubleToLongBits(ry) != Double.doubleToLongBits(other.ry))
			return false;
		if (Double.doubleToLongBits(rz) != Double.doubleToLongBits(other.rz))
			return false;
		if (Double.doubleToLongBits(tcpOffsetRX) != Double.doubleToLongBits(other.tcpOffsetRX))
			return false;
		if (Double.doubleToLongBits(tcpOffsetRY) != Double.doubleToLongBits(other.tcpOffsetRY))
			return false;
		if (Double.doubleToLongBits(tcpOffsetRZ) != Double.doubleToLongBits(other.tcpOffsetRZ))
			return false;
		if (Double.doubleToLongBits(tcpOffsetX) != Double.doubleToLongBits(other.tcpOffsetX))
			return false;
		if (Double.doubleToLongBits(tcpOffsetY) != Double.doubleToLongBits(other.tcpOffsetY))
			return false;
		if (Double.doubleToLongBits(tcpOffsetZ) != Double.doubleToLongBits(other.tcpOffsetZ))
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
