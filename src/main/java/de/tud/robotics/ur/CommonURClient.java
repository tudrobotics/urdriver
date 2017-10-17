package de.tud.robotics.ur;

import com.igormaznitsa.jbbp.model.JBBPFieldStruct;

import de.tud.robotics.ur.client.RobotMessageType;
import de.tud.robotics.ur.client.RobotPackageType;
import de.tud.robotics.ur.client.data.CartesianData;
import de.tud.robotics.ur.client.data.JointData;
import de.tud.robotics.ur.client.data.RealtimeCartesianData;
import de.tud.robotics.ur.client.data.RealtimeJointData;

public abstract class CommonURClient extends URClient {

	// data 
	protected JointData jointData;
	protected CartesianData cartesianData;
	
	// process Variables
	protected JBBPFieldStruct messageHeader = null;
	protected int length = 0;
	protected RobotMessageType type = null;
	protected int parsingErrorCount = 0;

	protected JBBPFieldStruct p;
	protected int packageLength = 0;
	protected RobotPackageType packageType;
	protected byte[] a;
	protected long currentTime;
	
	
	public CommonURClient(String host, int port) {
		super(host,port);
		jointData = new RealtimeJointData();
		cartesianData = new RealtimeCartesianData();
	}
	
	public JointData getJointData() {
		return jointData;
	}

	public CartesianData getCartesianData() {
		return cartesianData;
	}
}
