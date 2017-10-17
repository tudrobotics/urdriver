package de.tud.robotics.ur.client.data.v2;

import java.util.Date;

public abstract class RobotPackageData {

	private long timestamp = System.currentTimeMillis();
	private String sender;
	
	public RobotPackageData(String sender) {
		this.sender = sender;
	}
	public long getTimestamp() {
		return timestamp;
	}
	public String getSender() {
		return sender;
	}
}
