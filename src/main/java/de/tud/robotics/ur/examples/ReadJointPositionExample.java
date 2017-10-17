package de.tud.robotics.ur.examples;

import java.io.IOException;
import java.math.RoundingMode;
import java.net.UnknownHostException;
import java.util.LinkedList;

import de.tud.robotics.ur.SecondaryURClient;
import de.tud.robotics.ur.URClient;
import de.tud.robotics.ur.URClientListener;
import de.tud.robotics.ur.api.Internals;
import de.tud.robotics.ur.api.JointPosition;
import de.tud.robotics.ur.api.Motion;
import de.tud.robotics.ur.api.MotionExtended;
import de.tud.robotics.ur.client.RobotPackageType;
import de.tud.robotics.ur.client.data.JointData;
import de.tud.robotics.ur.client.data.RobotPackageData;

public class ReadJointPositionExample implements URClientListener {

	public static void main(String[] args) throws UnknownHostException, IOException {
		new ReadJointPositionExample().run();
	}
	
	public void run() {
		URClient c = new SecondaryURClient("172.16.152.218");
		c.setUpdateFrequence(RobotPackageType.JOINT_DATA, 1);
		c.addListener(this);
		try {
			c.connect();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Motion m = c.getProxy(Motion.class);
		MotionExtended mx = c.getProxy(MotionExtended.class);
		Internals i = c.getProxy(Internals.class); 
	}

	@Override
	public void notify(RobotPackageData data) {
		if(data instanceof JointData) {
			JointData d = (JointData) data;
			System.out.println("Joint data every 1 second");
			System.out.println(d.toJointPosition().toString());
		}
		
	}
}
