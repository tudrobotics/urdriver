package de.tud.robotics.ur.client.data.v2;

import com.igormaznitsa.jbbp.mapper.Bin;

import de.tud.robotics.ur.client.RobotPackageType;

public final class CartesianData extends CommonCartesianData {
			
	@Bin(custom=true)
	private double tcpOffsetX;	
	@Bin(custom=true)
	private double tcpOffsetY;
	@Bin(custom=true)
	private double tcpOffsetZ;
	@Bin(custom=true)
	private double tcpOffsetRX;
	@Bin(custom=true)
	private double tcpOffsetRY;
	@Bin(custom=true)
	private double tcpOffsetRZ;
	
	public CartesianData(String sender) {
		super(sender);
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
		if (Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x))
			return false;
		if (Double.doubleToLongBits(y) != Double.doubleToLongBits(other.y))
			return false;
		if (Double.doubleToLongBits(z) != Double.doubleToLongBits(other.z))
			return false;
		return true;
	}
}
