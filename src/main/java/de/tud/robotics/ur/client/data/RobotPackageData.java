package de.tud.robotics.ur.client.data;

import java.util.Date;

public abstract class RobotPackageData {

	private long lastUpdated = new Date().getTime();
	private String sender;
	
	public long getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(long lastUpdated) {
		this.lastUpdated = lastUpdated;
	}
	
	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public abstract String getRobotPackageType();
}
