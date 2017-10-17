package de.tud.robotics.ur.api;

import javax.vecmath.Vector3d;

public interface Internals extends URScriptInterface {

	/**
	 * Returns the force exceted at the TCP Return the current externally
	 * excerted force at the TCP. The force is the norm of Fx, Fy, and Fz
	 * calculated using get tcp force().
	 * 
	 * @return The force in Newtons (float)
	 */
	float force();
	/**
	 * Returns the actual angular positions of all joints The angular actual
	 * positions are expressed in radians and returned as a vector of length 6.
	 * Note that the output might differ from the output of get target joint
	 * positions(), especially durring acceleration and heavy loads.
	 * 
	 * @return The current actual joint angular position vector in rad : [Base,
	 *         Shoulder, Elbow, Wrist1, Wrist2, Wrist3]
	 */
	JointPosition get_actual_joint_positions();
	
	/**
	 * Returns the actual angular velocities of all joints The angular actual
	 * velocities are expressed in radians pr. second and returned as a vector
	 * of length 6. Note that the output might differ from the output of get
	 * target joint speeds(), especially durring acceleration and heavy loads.
	 * 
	 * @return The current actual joint angular velocity vector in rad/s: [Base,
	 *         Shoulder, Elbow, Wrist1, Wrist2, Wrist3]
	 */
	double[] get_actual_joint_speeds();

	/**
	 * Returns the current measured tool pose Returns the 6d pose representing
	 * the tool position and orientation specified in the base frame. The
	 * calculation of this pose is based on the actual robot encoder readings.
	 * 
	 * @return The current actual TCP vector : ([X, Y, Z, Rx, Ry, Rz])
	 */
	CartesianPosition get_actual_tcp_pose();

	/**
	 * Returns the current measured TCP speed The speed of the TCP retuned in a
	 * pose structure. The first three values are the cartesian speeds along
	 * x,y,z, and the last three define the current rotation axis, rx,ry,rz, and
	 * the length jrz,ry,rzj defines the angular velocity in radians/s.
	 * 
	 * @return The current actual TCP velocity vector; ([X, Y, Z, Rx, Ry, Rz])
	 */
	CartesianPosition get_actual_tcp_speed();

	/**
	 * Returns the temperature of the control box The temperature of the robot
	 * control box in degrees Celcius.
	 * 
	 * @return A temperature in degrees Celcius (float)
	 */
	float get_controller_temp();

	/**
	 * Inverse kinematics Inverse kinematic transformation (tool space -> joint
	 * space). Solution closest to current joint positions is returned, unless
	 * qnear defines one.
	 * 
	 * @param x
	 *            tool pose (spatial vector)
	 * @param qnear
	 *            joint positions to select solution. Optional.
	 * @param maxPositionError
	 *            Define the max allowed position error. Optional.
	 * @param maxOrientationError
	 *            Define the max allowed orientation error. Optional.
	 * @return joint positions
	 */
	JointPosition get_inverse_kin(CartesianPosition x, @Named("qnear=") JointPosition qnear,@Named("maxPositionError=") float maxPositionError, @Named("maxOrientationError=") float maxOrientationError);

	JointPosition get_inverse_kin(CartesianPosition x);

	
	/**
	 * Returns the temperature of joint j The temperature of the joint house of
	 * joint j, counting from zero. j=0 is the base joint, and j=5 is the last
	 * joint before the tool flange.
	 * 
	 * @param j
	 *            The joint number (int)
	 * @return A temperature in degrees Celcius (float)
	 */
	float get_joint_temp(Joint j);

	/**
	 * Returns the torques of all joints The torque on the joints, corrected by
	 * the torque needed to move the robot itself (gravity, friction, etc.),
	 * returned as a vector of length 6.
	 * 
	 * @return The joint torque vector in ; ([float])
	 */
	float[] get_joint_torques();

	/**
	 * Returns the desired angular position of all joints The angular target
	 * positions are expressed in radians and returned as a vector of length 6.
	 * Note that the output might differ from the output of get actual joint
	 * positions(), especially durring acceleration and heavy loads.
	 * 
	 * @return The current target joint angular position vector in rad: [Base,
	 *         Shoulder, Elbow, Wrist1, Wrist2, Wrist3]
	 */
	JointPosition get_target_joint_positions();

	/**
	 * Returns the desired angular velocities of all joints The angular target
	 * velocities are expressed in radians pr. second and returned as a vector
	 * of length 6. Note that the output might differ from the output of get
	 * actual joint speeds(), especially durring acceleration and heavy loads.
	 * 
	 * @return The current target joint angular velocity vector in rad/s: [Base,
	 *         Shoulder, Elbow, Wrist1, Wrist2, Wrist3]
	 */
	double[] get_target_joint_speeds();

	/**
	 * Returns the current target tool pose Returns the 6d pose representing the
	 * tool position and orientation specified in the base frame. The
	 * calculation of this pose is based on the current target joint positions.
	 * 
	 * @return The current target TCP vector; ([X, Y, Z, Rx, Ry, Rz])
	 */
	CartesianPosition get_target_tcp_pose();

	/**
	 * Returns the current target TCP speed The desired speed of the TCP retuned
	 * in a pose structure. The first three values are the cartesian speeds
	 * along x,y,z, and the last three define the current rotation axis,
	 * rx,ry,rz, and the length jrz,ry,rzj defines the angular velocity in
	 * radians/s.
	 * 
	 * @return The TCP speed; (pose)
	 */
	double[] get_target_tcp_speed();

	/**
	 * Returns the wrench (Force/Torque vector) at the TCP The external wrench
	 * is computed baed on the error between the joint torques required to stay
	 * on the trajectory and the expected joint torques. The function returns
	 * â€�p[Fx (N), Fy(N), Fz(N), TRx (Nm), TRy (Nm), TRz (Nm)]â€�. where Fx,
	 * Fy, and Fx are the forces in the axes of the robot base coordinate system
	 * measured in Newtons, and TRx, TRy, and TRz are the torques around these
	 * axes measyred in Newton times Meters.
	 * 
	 * @return the wrench (pose)
	 */
	double[] get_tcp_force();

	/**
	 * Display popup on GUI Display message in popup window on GUI.
	 * 
	 * @param s
	 *            message string
	 * @param title
	 *            title string
	 * @param warning
	 *            warning message?
	 * @param error
	 *            error message?
	 */
	void popup(String s, @Named("title=") String title, @Named("warning=") boolean warning, @Named("error=")  boolean error);

	default void popup(String s) {
		popup(s, "Popup", false, false);
	}

	/**
	 * Shutdown the robot, and power off the robot and controller.
	 */
	void powerdown();

	/**
	 * Set the direction of the acceleration experienced by the robot. When the
	 * robot mounting is fixed, this corresponds to an accleration of g away
	 * from the earthâ€™s centre. >>> set gravity([0, 9.82*sin(theta),
	 * 9.82*cos(theta)]) will set the acceleration for a robot that is rotated
	 * â€�thetaâ€� radians around the x-axis of the robot base coordinate system
	 * 
	 * @param d
	 *            3D vector, describing the direction of the gravity, relative
	 *            to the base of the robot.
	 */
	void set_gravity(Vector3d d);
	/**
	 * Set payload mass and center of gravity Sets the mass and center of
	 * gravity (abbr. CoG) of the payload. This function must be called, when
	 * the payload weight or weigh distribution changes significantly - I.e when
	 * the robot picks up or puts down a heavy workpiece. The CoG argument is
	 * optional - If not provided, the Tool Center Point (TCP) will be used as
	 * the Center of Gravity (CoG). If the CoG argument is omitted, later calls
	 * to set tcp(pose) will change CoG to the new TCP. The CoG is specified as
	 * a Vector, [CoGx, CoGy, CoGz], displacement, from the toolmount.
	 * 
	 * @param m
	 *            mass in kilograms
	 * @param CoG
	 *            Center of Gravity: [CoGx, CoGy, CoGz] in meters. Optional.
	 */
	void set_payload(float m, Vector3d CoG);
	default void set_payload(float m) {
		set_payload(m,new Vector3d(new double[]{0,0,0}));
	}
	/**
	 * Set the Tool Center Point Sets the transformation from the output flange
	 * coordinate system to the TCP as a pose.
	 * 
	 * @param pose
	 *            A pose describing the transformation.
	 */
	void set_tcp(JointPosition pose);

	/**
	 * Sleep for an amount of time
	 * 
	 * @param t
	 *            time [s]
	 */
	void sleep(float t);

	/**
	 * Uses up the remaining physical time a thread has in the current
	 * frame.
	 */
	void sync();

	/**
	 * Send text message to log Send message with s1 and s2 concatenated to be
	 * shown on the GUI log-tab
	 * 
	 * @param s1
	 *            message string, variables of other types (int, bool poses
	 *            etc.) can also be sent
	 * @param s2
	 *            message string, variables of other types (int, bool poses
	 *            etc.) can also be sent
	 */
	void textmsg(String s1, String s2);

}
