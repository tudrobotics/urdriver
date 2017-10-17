package de.tud.robotics.ur.client.data;

import de.tud.robotics.ur.api.JointPosition;

public class RealtimeJointData extends JointData {

	
	public void setJoints(RealtimeSingleJointData[] joints) {
		this.joints = joints;
	}
	
	public double[] jointVelocityArray() {
		double[] r = new double[joints.length];
		for(int i = 0;i < joints.length; i++) {
			r[i] = ((RealtimeSingleJointData)joints[i]).qdactual;
		}
		return r;
	}
	
	public double[] jointTargetVelocityArray() {
		double[] r = new double[joints.length];
		for(int i = 0;i < joints.length; i++) {
			r[i] = ((RealtimeSingleJointData)joints[i]).qdtarget;
		}
		return r;
	}
	public JointPosition toJointTargetVelocity() {
		return new JointPosition(jointTargetVelocityArray());
	}
	
	public double[] jointTargetAccelerationArray() {
		double[] r = new double[joints.length];
		for(int i = 0;i < joints.length; i++) {
			r[i] = ((RealtimeSingleJointData)joints[i]).qddtarget;
		}
		return r;
	}
	public double[] jointTargetMomentumArray() {
		double[] r = new double[joints.length];
		for(int i = 0;i < joints.length; i++) {
			r[i] = ((RealtimeSingleJointData)joints[i]).Mtarget;
		}
		return r;
	}
	
	public JointPosition toJointTargetAcceleration() {
		return new JointPosition(jointTargetVelocityArray());
	}
	
}
