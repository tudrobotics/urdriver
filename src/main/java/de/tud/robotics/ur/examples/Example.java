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

public class Example implements URClientListener {

	public static void main(String[] args) throws UnknownHostException, IOException {
		new Example().run();
	}
	
	public void run() {
		URClient c = new SecondaryURClient("172.31.1.141");
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
		//JointPosition p = new JointPosition(new double[] {1.0,2.3,1.0,1.0,1.2,1.0});
		//CartesianPosition cart = new CartesianPosition(new double[] {1.0,2.3,1.0,1.0,1.2,1.0});
		LinkedList<JointPosition> list = new LinkedList<JointPosition>();
		JointPosition pos = new JointPosition(new double[] {3.1468286413,-0.8990190977,1.9971802631,-2.7342328062,-1.5739379194,3.1386255939});
		list.add(pos);
		pos = new JointPosition(new double[] {2.9497809688,0.0958185759,1.234296847,-2.9124309228,-1.5739379194,3.1386255939});
		list.add(pos);
		pos = new JointPosition(new double[] {3.2344441698,-0.5845107665,1.6077973069,-2.5652849346,-1.5760323146,3.1386255939});
		list.add(pos);
		pos = new JointPosition(new double[] {3.4971162222,0.1420698011,0.9756390519,-2.7003734187,-1.5751596499,3.1386255939});
		list.add(pos);
		pos = new JointPosition(new double[] {3.1468286413,-0.8990190977,1.9971802631,-2.7342328062,-1.5739379194,3.1386255939});
		list.add(pos);
		pos = new JointPosition(new double[] {2.9497809688,0.0958185759,1.234296847,-2.9124309228,-1.5739379194,3.1386255939});
		list.add(pos);
		pos = new JointPosition(new double[] {2.6808257311,-0.5845107665,1.6077973069,-2.5652849346,-1.5760323146,3.1386255939});
		list.add(pos);
		pos = new JointPosition(new double[] {2.4581217185,0.1862266312,0.8848819308,-2.5797711674,-1.5781267097,3.1386255939});
		list.add(pos);
		pos = new JointPosition(new double[] {3.1468286413,-0.8990190977,1.9971802631,-2.7342328062,-1.5739379194,3.1386255939});
		list.add(pos);
		pos = new JointPosition(new double[] {2.9497809688,0.0958185759,1.234296847,-2.9124309228,-1.5739379194,3.1386255939});
		list.add(pos);
		pos = new JointPosition(new double[] {2.9513517651,-0.5270894341,2.0724039538,-4.4717080765,-1.4184290831,3.1386255939});
		list.add(pos);
		pos = new JointPosition(new double[] {3.1468286413,-0.8990190977,1.9971802631,-2.7342328062,-1.5739379194,3.1386255939});
		list.add(pos);
		
		//m.movej(cart);
		//m.servoj(p, 1, 1, 4, 0.03f, 100);
		//mx.servoj(cart);
		
		mx.servoj(pos);
		list.forEach(p -> {
			mx.servoj(p);
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		
		c.dispose();
	}

	@Override
	public void notify(RobotPackageData data) {
		if(data instanceof JointData) {
			JointData d = (JointData) data;
			System.out.println("Joint data everi 1 second");
			System.out.println(d.toJointPosition().toString());
		}
		
	}
}
