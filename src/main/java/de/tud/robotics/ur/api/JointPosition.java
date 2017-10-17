package de.tud.robotics.ur.api;

import java.util.Arrays;

import de.tud.robotics.ur.helper.DoubleHelper;

public class JointPosition {
	
	private double[] joints;
	
	public JointPosition() {
		joints = new double[6];
	}
	
	
	public JointPosition(double[] joints) {
		this.joints = joints;
	}

	public void setJoint(int index, double value) {
		if(index < 0) throw new IllegalArgumentException("index >= 0");
		if(index >= joints.length) new IllegalArgumentException("index to big");
		joints[index] = value;
	}

	public double getJoint(int index) {
		if(index < 0) throw new IllegalArgumentException("index >= 0");
		if(index >= joints.length) new IllegalArgumentException("index >= "+joints.length);
		return joints[index];
	}
	
	public double[] toArray(){
		return Arrays.copyOf(joints, joints.length);
	}
	
	@Override
	public String toString() {
		return Arrays.toString(joints);
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(joints);
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
		JointPosition other = (JointPosition) obj;
		if (!Arrays.equals(joints, other.joints))
			return false;
		return true;
	}
	
	
	public boolean equals(JointPosition pos, int places) {
		for(int i = 0; i < joints.length; i++) {
			if (DoubleHelper.round(joints[i], places) !=  DoubleHelper.round(pos.joints[i], places))
				return false;
		}
		return true;
	}
	
}
