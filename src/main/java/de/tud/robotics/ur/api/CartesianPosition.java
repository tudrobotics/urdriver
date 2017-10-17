package de.tud.robotics.ur.api;

import java.util.Arrays;

import de.tud.robotics.ur.helper.DoubleHelper;


public class CartesianPosition {

	/**
	 * important in m!
	 */
	private double x;
	/**
	 * important in m!
	 */
	private double y;
	/**
	 * important in m!
	 */
	private double z;
	/**
	 * important in rad!
	 */
	private double rx;
	/**
	 * important in rad!
	 */
	private double ry;
	/**
	 * important in rad!
	 */
	private double rz;

	public CartesianPosition(double[] arr) {
		this.x = arr[0];
		this.y = arr[1];
		this.z = arr[2];
		this.rx = arr[3];
		this.ry = arr[4];
		this.rz = arr[5];
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getZ() {
		return z;
	}

	public void setZ(double z) {
		this.z = z;
	}

	public double getRx() {
		return rx;
	}

	public void setRx(double rx) {
		this.rx = rx;
	}

	public double getRy() {
		return ry;
	}

	public void setRy(double ry) {
		this.ry = ry;
	}

	public double getRz() {
		return rz;
	}

	public void setRz(double rz) {
		this.rz = rz;
	}

	@Override
	public String toString() {
		return "p" + Arrays.toString(new double[] { x, y, z, rx, ry, rz });
	}

	public static CartesianPosition fromMMArray(double[] array) {
		for (int i = 0; i < 3; i++) {
			array[i] = array[i] / 1000d;
		}
		return new CartesianPosition(array);
	}

	public double[] toArray() {
		return new double[] { x, y, z, rx, ry, rz };
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
		CartesianPosition other = (CartesianPosition) obj;
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

	public boolean equals(Object obj, int places) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CartesianPosition other = (CartesianPosition) obj;
		if (DoubleHelper.round(rx, places) != DoubleHelper.round(other.rx, places))
			return false;
		if (DoubleHelper.round(ry, places) != DoubleHelper.round(other.ry, places))
			return false;
		if (DoubleHelper.round(rz, places) != DoubleHelper.round(other.rz, places))
			return false;
		if (DoubleHelper.round(x, places) != DoubleHelper.round(other.x, places))
			return false;
		if (DoubleHelper.round(y, places) != DoubleHelper.round(other.y, places))
			return false;
		if (DoubleHelper.round(z, places) != DoubleHelper.round(other.z, places))
			return false;
		return true;
	}

}
