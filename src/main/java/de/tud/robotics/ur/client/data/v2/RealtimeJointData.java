package de.tud.robotics.ur.client.data.v2;

import de.tud.robotics.ur.api.JointPosition;
import de.tud.robotics.ur.client.data.RobotModeData;

public final class RealtimeJointData extends RobotPackageData {

	private RealtimeSingleJointData[] joints = new RealtimeSingleJointData[0];
	
	public RealtimeJointData(String sender, RealtimeSingleJointData[] joints) {
		super(sender);
		this.joints = joints;
	}
	
	public double[] getTargetJointVelocityArray() {
		double[] r = new double[joints.length];
		for(int i = 0;i < joints.length; i++) {
			r[i] = joints[i].getQdtarget();
		}
		return r;
	}
	
	public double[] getTargetJointAccelerationArray() {
		double[] r = new double[joints.length];
		for(int i = 0;i < joints.length; i++) {
			r[i] = joints[i].getQddtarget();
		}
		return r;
	}
	
}
