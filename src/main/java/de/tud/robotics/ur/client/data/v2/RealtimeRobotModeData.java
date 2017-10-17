package de.tud.robotics.ur.client.data.v2;

import com.igormaznitsa.jbbp.mapper.Bin;

public class RealtimeRobotModeData extends RobotPackageData {
	@Bin
	private int robotMode;
	@Bin(custom=true)
	private double speedScaling;
	
	public RealtimeRobotModeData(String sender) {
		super(sender);
	}

	public int getRobotMode() {
		return robotMode;
	}

	public double getSpeedScaling() {
		return speedScaling;
	}
	

	
}
