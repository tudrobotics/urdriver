package de.tud.robotics.ur.client.data.v2;

public final class RealtimeSingleJointData extends CommonSingleJointData {

	private double qdtarget;
	private double qddtarget;
	private double Itarget;
	private double Mtarget;
	
	/**
	 * 
	 * @return joint velocity
	 */
	public double getQdtarget() {
		return qdtarget;
	}
	/**
	 * 
	 * @return joint acceleration
	 */
	public double getQddtarget() {
		return qddtarget;
	}
	/**
	 * 
	 * @return target joint currents
	 */
	public double getItarget() {
		return Itarget;
	}
	/**
	 * 
	 * @return joint torques
	 */
	public double getMtarget() {
		return Mtarget;
	}
	
	
	
	
}
