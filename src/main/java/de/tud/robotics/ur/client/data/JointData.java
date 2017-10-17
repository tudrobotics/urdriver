package de.tud.robotics.ur.client.data;

import java.util.Arrays;

import com.igormaznitsa.jbbp.mapper.Bin;

import de.tud.robotics.ur.api.Joint;
import de.tud.robotics.ur.api.JointPosition;
import de.tud.robotics.ur.client.RobotPackageType;

public class JointData extends RobotPackageData {
	
	public static int packageLength = 251;
	public static RobotPackageType packageType = RobotPackageType.JOINT_DATA;
	
	@Bin
	protected SingleJointData [] joints;
	
	public SingleJointData getJointData(Joint joint ) {
		switch(joint) {
			case BASE: return joints[0];
			case SHOULDER: return joints[1];
			case ELBOW: return joints[2];
			case WRIST1: return joints[3];
			case WRIST2: return joints[4];
			case WRIST3: return joints[5];
			default: return null;
		}
	}
	
	
	public SingleJointData[] getJoints() {
		return joints;
	}


	public double[] jointPositionArray() {
		double[] r = new double[joints.length];
		for(int i = 0;i < joints.length; i++) {
			r[i] = joints[i].qactual;
		}
		return r;
	}
	public JointPosition toJointPosition() {
		return new JointPosition(jointPositionArray());
	}
	
	public double[] jointVelocityArray() {
		double[] r = new double[joints.length];
		for(int i = 0;i < joints.length; i++) {
			r[i] = joints[i].qdactual;
		}
		return r;
	}
	public JointPosition toJointVeloctiy() {
		return new JointPosition(jointVelocityArray());
	}
	
	public double[] jointTargetPositionArray() {
		double[] r = new double[joints.length];
		for(int i = 0;i < joints.length; i++) {
			r[i] = joints[i].qtarget;
		}
		return r;
	}
	public JointPosition toJointTargetPosition() {
		return new JointPosition(jointTargetPositionArray());
	}
	
	@Override
	public String getRobotPackageType() {
		return packageType.toString();
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
		JointData other = (JointData) obj;
		if(joints == null && other.joints == null) return true;
		if(joints == null && other.joints != null) return false;
		if(joints != null && other.joints == null) return false;
		for(int i = 0; i < joints.length; i++ ) {
			if(!joints[i].equals(other.joints[i])) return false;
		}
		return true;
	}

	
}