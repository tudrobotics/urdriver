package de.tud.robotics.ur.client.data;

public class RealtimeRobotModeData extends RobotModeData {

	public void setTimestamp(long time) {
		this.timestamp = time;
	}
	public void setRobotMode(int robotMode) {
		this.robotMode = robotMode;
	}
	public void setSpeedScaling(double speedScaling) {
		this.speedScaling = speedScaling;
	}

	
}
