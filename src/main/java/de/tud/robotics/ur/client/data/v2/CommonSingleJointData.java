package de.tud.robotics.ur.client.data.v2;
import com.igormaznitsa.jbbp.mapper.Bin;
import com.igormaznitsa.jbbp.mapper.BinType;

import de.tud.robotics.ur.client.JointMode;


public class CommonSingleJointData {
		@Bin(custom=true)
		protected double qactual;
		@Bin(custom=true)
		protected double qtarget;
		@Bin(custom=true)
		protected double qdactual;
		@Bin(custom=true)
		protected float Iactual;
		@Bin(custom=true)
		protected float Vactual;
		@Bin(custom=true)
		protected float Tmotor;
		@Bin(type=BinType.UBYTE)
		protected int jointMode;
		
		/**
		 * 
		 * @return current joint position
		 */
		public double getQactual() {
			return qactual;
		}
		/**
		 * 
		 * @return target joint position
		 */
		public double getQtarget() {
			return qtarget;
		}
		/**
		 * 
		 * @return current joint velocity
		 */
		public double getQdactual() {
			return qdactual;
		}
		/**
		 * 
		 * @return  currents on joint
		 */
		public float getIactual() {
			return Iactual;
		}
		/**
		 * 
		 * @return voltage on joint
		 */
		public float getVactual() {
			return Vactual;
		}
		/**
		 * 
		 * @return temperature of motor on joint in degress celsius
		 */
		public float getTmotor() {
			return Tmotor;
		}
		
		public JointMode getJointMode() {
			return JointMode.from(jointMode);
		}
			
	}