package de.tud.robotics.ur.client.data.v2;

import com.igormaznitsa.jbbp.mapper.Bin;

public final class RealtimeCartesianData extends CommonCartesianData {

	@Bin(custom=true)
	private double speedX;
	@Bin(custom=true)
	private double speedY;
	@Bin(custom=true)
	private double speedZ;
	@Bin(custom=true)
	private double speedRx;
	@Bin(custom=true)
	private double speedRy;
	@Bin(custom=true)
	private double speedRz;
	@Bin(custom=true)
	private double forceX;
	@Bin(custom=true)
	private double forceY;
	@Bin(custom=true)
	private double forceZ;
	@Bin(custom=true)
	private double forceRx;
	@Bin(custom=true)
	private double forceRy;
	@Bin(custom=true)
	private double forceRz;
	@Bin(custom=true)
	private double targetX;
	@Bin(custom=true)
	private double targetY;
	@Bin(custom=true)
	private double targetZ;
	@Bin(custom=true)
	private double targetRx;
	@Bin(custom=true)
	private double targetRy;
	@Bin(custom=true)
	private double targetRz;
	@Bin(custom=true)
	private double speedTargetX;
	@Bin(custom=true)
	private double speedTargetY;
	@Bin(custom=true)
	private double speedTargetZ;
	@Bin(custom=true)
	private double speedTargetRx;
	@Bin(custom=true)
	private double speedTargetRy;
	@Bin(custom=true)
	private double speedTargetRz;
	
	public RealtimeCartesianData(String sender) {
		super(sender);
	}

	public double getSpeedX() {
		return speedX;
	}

	public double getSpeedY() {
		return speedY;
	}

	public double getSpeedZ() {
		return speedZ;
	}

	public double getSpeedRx() {
		return speedRx;
	}

	public double getSpeedRy() {
		return speedRy;
	}

	public double getSpeedRz() {
		return speedRz;
	}

	public double getForceX() {
		return forceX;
	}

	public double getForceY() {
		return forceY;
	}

	public double getForceZ() {
		return forceZ;
	}

	public double getForceRx() {
		return forceRx;
	}

	public double getForceRy() {
		return forceRy;
	}

	public double getForceRz() {
		return forceRz;
	}

	public double getTargetX() {
		return targetX;
	}

	public double getTargetY() {
		return targetY;
	}

	public double getTargetZ() {
		return targetZ;
	}

	public double getTargetRx() {
		return targetRx;
	}

	public double getTargetRy() {
		return targetRy;
	}

	public double getTargetRz() {
		return targetRz;
	}

	public double getSpeedTargetX() {
		return speedTargetX;
	}

	public double getSpeedTargetY() {
		return speedTargetY;
	}

	public double getSpeedTargetZ() {
		return speedTargetZ;
	}

	public double getSpeedTargetRx() {
		return speedTargetRx;
	}

	public double getSpeedTargetRy() {
		return speedTargetRy;
	}

	public double getSpeedTargetRz() {
		return speedTargetRz;
	}

	
	
}
