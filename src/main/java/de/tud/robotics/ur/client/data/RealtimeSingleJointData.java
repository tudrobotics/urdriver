package de.tud.robotics.ur.client.data;

public class RealtimeSingleJointData extends SingleJointData {

	protected double qdtarget;
	protected double qddtarget;
	protected double Itarget;
	protected double Mtarget;
	protected double Icontrol;
	
	
	public double getQdtarget() {
		return qdtarget;
	}
	public void setQdtarget(double qdtarget) {
		this.qdtarget = qdtarget;
	}
	public double getQddtarget() {
		return qddtarget;
	}
	public void setQddtarget(double qddtarget) {
		this.qddtarget = qddtarget;
	}
	public double getItarget() {
		return Itarget;
	}
	public void setItarget(double itarget) {
		Itarget = itarget;
	}
	public double getMtarget() {
		return Mtarget;
	}
	public void setMtarget(double mtarget) {
		Mtarget = mtarget;
	}
	public double getIcontrol() {
		return Icontrol;
	}
	public void setIcontrol(double icontrol) {
		Icontrol = icontrol;
	}
	
	
	
	
}
