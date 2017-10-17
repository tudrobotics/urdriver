package de.tud.robotics.ur.examples;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.lang.annotation.Target;
import java.math.RoundingMode;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.tud.robotics.ur.AbstractURServoControl;
import de.tud.robotics.ur.CommonURClient;
import de.tud.robotics.ur.RealtimeURClient;
import de.tud.robotics.ur.SecondaryURClient;
import de.tud.robotics.ur.TargetManipulatingURServoControl;
import de.tud.robotics.ur.URClient;
import de.tud.robotics.ur.URClientListener;
import de.tud.robotics.ur.URServoControl;
import de.tud.robotics.ur.api.Internals;
import de.tud.robotics.ur.api.JointPosition;
import de.tud.robotics.ur.api.Motion;
import de.tud.robotics.ur.api.MotionExtended;
import de.tud.robotics.ur.client.RobotPackageType;
import de.tud.robotics.ur.client.data.JointData;
import de.tud.robotics.ur.client.data.RealtimeJointData;
import de.tud.robotics.ur.client.data.RobotPackageData;

public class RecordingExample implements URClientListener {

	private static boolean record = false;
	private Map<String,List<Double>> cache = new HashMap<String, List<Double>>();
	
	public static void main(String[] args) throws UnknownHostException, IOException, InterruptedException {
		new RecordingExample().run();
	}
	
	private volatile RealtimeJointData jointData = null;
	
	public void run() throws InterruptedException {
		for(int i =0; i < 6;i++) {
			cache.put("pos"+i, new LinkedList<Double>());
			cache.put("target"+i, new LinkedList<Double>());
			cache.put("speed"+i, new LinkedList<Double>());
			cache.put("targetSpeed"+i, new LinkedList<Double>());
			cache.put("acc"+i, new LinkedList<Double>());
			cache.put("moments"+i, new LinkedList<Double>());
		}
		CommonURClient c = new RealtimeURClient("192.168.229.130");
		//CommonURClient c = new RealtimeURClient("172.31.1.141");
		c.setUpdateFrequence(RobotPackageType.JOINT_DATA, 125);
		c.addListener(this);
		AbstractURServoControl servo = new TargetManipulatingURServoControl(c);
		//servo = new URServoControl(c);
		servo.start();
		servo.setSpeedPercentage(4.0f);
		try {
			c.connect();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Motion m = c.getProxy(Motion.class);
		MotionExtended mx = c.getProxy(MotionExtended.class);
		Internals ig = c.getProxy(Internals.class); 
		//JointPosition p = new JointPosition(new double[] {1.0,2.3,1.0,1.0,1.2,1.0});
		//CartesianPosition cart = new CartesianPosition(new double[] {1.0,2.3,1.0,1.0,1.2,1.0});
		LinkedList<JointPosition> list = new LinkedList<JointPosition>();
		
		
		JointPosition pos = new JointPosition(new double[] {Math.toRadians(-21.8), Math.toRadians(-151.16) , Math.toRadians(-73.37), Math.toRadians(-139.78), Math.toRadians(-109.46),  -41.28 / 180.0 * Math.PI
				});
		list.add(pos);
		pos = new JointPosition(new double[] {Math.toRadians(41.66), Math.toRadians(-151.45), Math.toRadians(-72.54), Math.toRadians(-140.82), Math.toRadians(-46.17) , Math.toRadians(-36.37)
				});
		list.add(pos);		
		System.out.println("move to start");
		m.movej(pos, 0.2f,0.25f,0,0);
		
		Thread.sleep(10000);
		record = true;
		System.out.println("servo command");
		servo.setTarget(list.get(0));
		Thread.sleep(500);
		System.out.println("send current pos");
		servo.setTarget(jointData.toJointPosition());
		//m.servoj(p, 1, 1, 4, 0.03f, 100);
		//mx.servoj(cart);
		
		//mx.servoj(pos);
		Thread.sleep(1000);
		servo.dispose();
		c.dispose();
		record = false;
		write();
	}
		
	@Override
	public void notify(RobotPackageData data) {
		if(data instanceof RealtimeJointData) {
			RealtimeJointData d = (RealtimeJointData) data;
			if(this.jointData != null) {
				//System.out.println(d.getLastUpdated()- this.jointData.getLastUpdated() +" ms");#
			}
			this.jointData = d;
			if(!record) return;
			double[] pos = d.jointPositionArray();
			double[] target = d.jointTargetPositionArray();
			double[] speed = d.jointVelocityArray();
			double[] targetSpeed = d.jointTargetVelocityArray();
			double[] acc = d.jointTargetAccelerationArray();
			double[] moments = d.jointTargetMomentumArray();
			
			for(int i =0; i < 6;i++) {
				cache.get("pos"+i).add(pos[i]);
				cache.get("target"+i).add(target[i]);
				cache.get("speed"+i).add(speed[i]);
				cache.get("targetSpeed"+i).add(targetSpeed[i]);
				cache.get("acc"+i).add(acc[i]);
				cache.get("moments"+i).add(moments[i]);
			}
			
		}
		
	}
	private void write() {
		String eol = System.getProperty("line.separator");
		SimpleDateFormat inputDf = new SimpleDateFormat("yyyy-MM-dd");
		try (Writer writer = new FileWriter("record"+inputDf.format(new Date())+".csv")) {
		  for (String entry : cache.keySet()) {
		    writer.append(entry)
		          .append(';');
		          
		  }
          writer.append(eol);
          for(int i= 0; i< cache.values().toArray().length;i++) {
          for (String entry : cache.keySet()) {
				try {
					writer.append(cache.get(entry).get(i).toString().replace(".", ",")).append(";");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
        	  }
  		  writer.append(eol);    
  		  }
		} catch (IOException ex) {
		  ex.printStackTrace(System.err);
		}
	}
}
