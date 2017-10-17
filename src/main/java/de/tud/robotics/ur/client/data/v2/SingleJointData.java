package de.tud.robotics.ur.client.data.v2;
import com.igormaznitsa.jbbp.mapper.Bin;
import com.igormaznitsa.jbbp.mapper.BinType;

import de.tud.robotics.ur.client.JointMode;


public final class SingleJointData extends CommonSingleJointData {
		
	@Bin(custom=true)
	private float Tmicro;
		
	public float getTmicro() {
		return Tmicro;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Float.floatToIntBits(Iactual);
		result = prime * result + Float.floatToIntBits(Tmotor);
		result = prime * result + Float.floatToIntBits(Vactual);
		result = prime * result + jointMode;
		long temp;
		temp = Double.doubleToLongBits(qactual);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(qdactual);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(qtarget);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SingleJointData other = (SingleJointData) obj;
		if (Float.floatToIntBits(Iactual) != Float.floatToIntBits(other.Iactual))
			return false;
		if (Float.floatToIntBits(Tmotor) != Float.floatToIntBits(other.Tmotor))
			return false;
		if (Float.floatToIntBits(Vactual) != Float.floatToIntBits(other.Vactual))
			return false;
		if (jointMode != other.jointMode)
			return false;
		if (Double.doubleToLongBits(qactual) != Double.doubleToLongBits(other.qactual))
			return false;
		if (Double.doubleToLongBits(qdactual) != Double.doubleToLongBits(other.qdactual))
			return false;
		if (Double.doubleToLongBits(qtarget) != Double.doubleToLongBits(other.qtarget))
			return false;
		return true;
	}
			
}