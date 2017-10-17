package de.tud.robotics.ur.examples;

import java.io.IOException;
import java.net.UnknownHostException;

import de.tud.robotics.ur.SecondaryURClient;
import de.tud.robotics.ur.RealtimeURClient;
import de.tud.robotics.ur.URClient;
import de.tud.robotics.ur.URClientListener;
import de.tud.robotics.ur.client.RobotPackageType;
import de.tud.robotics.ur.client.data.ForceModeData;
import de.tud.robotics.ur.client.data.RealtimeCartesianData;
import de.tud.robotics.ur.client.data.RobotPackageData;

public class ForceTest implements URClientListener {

	public static void main(String[] args) throws UnknownHostException, IOException {
		new ForceTest().start();
	}
	public void start() throws UnknownHostException, IOException {
		URClient client = new SecondaryURClient("172.31.1.141");
		client = new RealtimeURClient("172.31.1.141");
		client.addListener(this);
		client.setUpdateFrequence(RobotPackageType.FORCE_MODE_DATA, 10);
		client.connect();
	}
	@Override
	public void notify(RobotPackageData data) {
		ForceModeData fm;
		if(data instanceof ForceModeData) {
			fm = (ForceModeData) data;
			
		}
		if(data instanceof RealtimeCartesianData) {
			RealtimeCartesianData d = (RealtimeCartesianData) data;
			System.out.println(Math.round(d.getForceX())+", "+Math.round(d.getForceY())+", "+Math.round(d.getForceZ()));
		}
		
	}
}
