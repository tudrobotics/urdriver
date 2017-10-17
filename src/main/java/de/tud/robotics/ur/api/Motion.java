package de.tud.robotics.ur.api;

import java.util.Vector;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.vecmath.Vector3d;

public interface Motion extends URScriptInterface {
	/**
	 * Tells the robot controller to treat digital inputs number A and B as
	 * pulses for a conveyor encoder. Only digital input 0, 1, 2 or 3 can be
	 * used. >>> conveyor pulse decode(1,0,1) This example shows how to set up
	 * quadrature pulse decoding with input A = digital in[0] and input B =
	 * digital in[1] >>> conveyor pulse decode(2,3) This example shows how to
	 * set up rising and falling edge pulse decoding with input A = digital
	 * in[3]. Note that you do not have to set parameter B (as it is not used
	 * anyway).
	 * 
	 * @param type
	 *            An integer determining how to treat the inputs on A and B 0 is
	 *            no encoder, pulse decoding is disabled. 1 is quadrature
	 *            encoder, input A and B must be square waves with 90 degree
	 *            offset. Direction of the conveyor can be determined. 2 is
	 *            rising and falling edge on single input (A). 3 is rising edge
	 *            on single input (A). 4 is falling edge on single input (A).
	 *            The controller can decode inputs at up to 40kHz
	 * @param a
	 *            Encoder input A, values of 0-3 are the digital inputs 0-3.
	 * @param b
	 *            Encoder input B, values of 0-3 are the digital inputs 0-3.
	 */
	void conveyor_pulse_decode(Encoder type, DigitalInput a, DigitalInput b);

	/**
	 * Resets the robot mode from force mode to normal operation. This is also
	 * done when a program stops.
	 */
	void end_force_mode();

	/**
	 * Set robot back in normal position control mode after freedrive mode.
	 */
	void end_freedrive_mode();

	/**
	 * Set robot back in normal position control mode after freedrive mode.
	 */
	void end_teach_mode();

	/**
	 * Set robot to be controlled in force mode
	 * 
	 * @param task_frame
	 *            A pose vector that defines the force frame relative to the
	 *            base frame.
	 * @param selection_vector
	 *            A 6d vector that may only contain 0 or 1. 1 means that the
	 *            robot will be compliant in the corresponding axis of the task
	 *            frame, 0 means the robot is not compliant along/about that
	 *            axis.
	 * @param wrench
	 *            The forces/torques the robot is to apply to its environment.
	 *            These values have different meanings whether they correspond
	 *            to a compliant axis or not. Compliant axis: The robot will
	 *            adjust its position along/about the axis in order to achieve
	 *            the specified force/torque. Non-compliant axis: The robot
	 *            follows the trajectory of the program but will account for an
	 *            external force/torque of the specified value.
	 * @param type
	 *            An integer specifying how the robot interprets the force
	 *            frame. 1: The force frame is transformed in a way such that
	 *            its y-axis is aligned with a vector pointing from the robot
	 *            tcp towards the origin of the force frame. 2: The force frame
	 *            is not transformed. 3: The force frame is transformed in a way
	 *            such that its x-axis is the projection of the robot tcp
	 *            velocity vector onto the x-y plane of the force frame. All
	 *            other values of type are invalid.
	 * @param limits
	 *            A 6d vector with float values that are interpreted differently
	 *            for compliant/non-compliant axes: Compliant axes: The limit
	 *            values for compliant axes are the maximum allowed tcp speed
	 *            along/about the axis. Non-compliant axes: The limit values for
	 *            non-compliant axes are the maximum allowed deviation
	 *            along/about an axis between the actual tcp position and the
	 *            one set by the program.
	 */
	void force_mode(CartesianPosition task_frame, Vector<Boolean> selection_vector, int wrench, ForceInterpretation type,
			float[] limits);

	/**
	 * Set robot in freedrive mode. In this mode the robot can be moved around
	 * by hand in the same way as by pressing the freedrive button. The
	 * robot wuill not be able to follow a trajectory (eg. a movej) in this
	 * mode.
	 */
	void voidfreedrive_mode();

	/**
	 * Tells the tick count of the encoder, note that the controller
	 * interpolates tick counts to get more accurate movements with low
	 * resolution encoders
	 * 
	 * @return The conveyor encoder tick count
	 */
	int get_conveyor_tick_count();

	/**
	 * Move Circular: Move to position (circular in tool-space) TCP moves on the
	 * circular arc segment from current pose, through pose via to pose to.
	 * Accelerates to and moves with constant tool speed v.
	 * 
	 * @param pose_via
	 *            path point (note: only position is used). (pose via can also
	 *            be specified as joint positions, then forward kinematics is
	 *            used to calculate the corresponding pose)
	 * @param pose_to
	 *            target pose (pose to can also be specified as joint positions,
	 *            then forward kinematics is used to calculate the corresponding
	 *            pose)
	 * @param acceleration
	 *            tool acceleration [m/sË†2]
	 * @param speed
	 *            tool speed [m/s]
	 * @param blend_radius
	 *            blend radius (of target pose) [m]
	 */
	void movec(CartesianPosition pose_via,CartesianPosition pose_to, @Named("a=") float acceleration, @Named("v=") float speed, @Named("r=") float blend_radius);

	default void movec(CartesianPosition pose_via, CartesianPosition pose_to) {
		movec(pose_via, pose_to, 1.2f, 0.25f, 0.0f);
	}

	/**
	 * Move to position (linear in joint-space) When using this command, the
	 * robot must be at standstill or come from a movej og movel with a blend.
	 * The speed and acceleration parameters controls the trapezoid speed
	 * profile of the move. The $t$ parameters can be used in stead to set the
	 * time for this move. Time setting has priority over speed and acceleration
	 * settings. The blend radius can be set with the $r$ parameters, to avoid
	 * the robot stopping at the point. However, if he blend region of this
	 * mover overlaps with previous or following regions, this move will be
	 * skipped, and an â€™Overlapping Blendsâ€™ warning message will be
	 * generated.
	 * 
	 * @param q
	 *            joint positions (q can also be specified as a pose, then
	 *            inverse kinematics is used to calculate the corresponding
	 *            joint positions)
	 * @param joint_acceleration
	 *            joint acceleration of leading axis [rad/sË†2]
	 * @param joint_speed
	 *            joint speed of leading axis [rad/s]
	 * @param time
	 *            time [S]
	 * @param blend_radius
	 *            blend radius [m]
	 */
	void movej(JointPosition q, @Named("a=") float joint_acceleration, @Named("v=") float joint_speed, @Named("t=") float time, @Named("r=") float blend_radius);

	default void movej(JointPosition q) {
		movej(q, 1.4f, 1.05f, 0.0f, 0f);
	}

	void movej(CartesianPosition q, @Named("a=") float joint_acceleration, @Named("v=") float joint_speed, @Named("t=") float time, @Named("r=") float blend_radius);

	default void movej(CartesianPosition q) {
		movej(q, 1.4f, 1.05f, 0.0f, 0f);
	}
	/**
	 * Move to position (linear in tool-space) See movej.
	 * 
	 * @param pose
	 *            target pose (pose can also be specified as joint positions,
	 *            then forward kinematics is used to calculate the corresponding
	 *            pose)
	 * @param tool_acceleration
	 *            tool acceleration [m/sË†2]
	 * @param tool_speed
	 *            tool speed [m/s]
	 * @param time
	 *            time [S]
	 * @param blend_radius
	 *            blend radius [m]
	 */
	void movel(CartesianPosition pose, @Named("a=") float tool_acceleration, @Named("v=") float tool_speed, @Named("t=") float time, @Named("r=") float blend_radius);

	default void movel(CartesianPosition pose) {
		movel(pose, 1.2f, 0.25f, 0f, 0f);
	}

	/**
	 * Move Process Blend circular (in tool-space) and move linear (in
	 * tool-space) to position. Accelerates to and moves with constant tool
	 * speed v.
	 * 
	 * @param pose
	 *            target pose (pose can also be specified as joint positions,
	 *            then forward kinematics is used to calculate the corresponding
	 *            pose)
	 * @param tool_acceleration
	 *            tool acceleration [m/sË†2]
	 * @param tool_speed
	 *            tool speed [m/s]
	 * @param blend_radius
	 *            blend radius [m]
	 */
	void movep(CartesianPosition pose, @Named("a=") float tool_acceleration, @Named("v=") float tool_speed, @Named("r=") float blend_radius);

	default void movep(CartesianPosition pose) {
		movep(pose, 1.2f, 0.25f, 0f);
	}

	/**
	 * Servo Circular Servo to position (circular in tool-space). Accelerates to
	 * and moves with constant tool speed v.
	 * 
	 * @param pose
	 *            target pose (pose can also be specified as joint positions,
	 *            then forward kinematics is used to calculate the corresponding
	 *            pose)
	 * @param tool_acceleration
	 *            tool acceleration [m/sË†2]
	 * @param tool_speed
	 *            tool speed [m/s]
	 * @param blend_radius
	 *            blend radius (of target pose) [m]
	 */
	void servoc( CartesianPosition pose, @Named("a=") float tool_acceleration, @Named("v=") float tool_speed, @Named("r=") float blend_radius);

	default void servoc(CartesianPosition pose) {
		servoc(pose, 1.2f, 0.25f, 0f);
	}

	void servoc(JointPosition pose, @Named("a=") float tool_acceleration, @Named("v=") float tool_speed, @Named("r=") float blend_radius);

	default void servoc(JointPosition pose) {
		servoc(pose, 1.2f, 0.25f, 0f);
	}
	/**
	 * Servo to position (linear in joint-space) Servo function used for online
	 * control of the robot. The lookahead time and the gain can be used to
	 * smoothen or sharpen the trajectory. Note: A high gain or a short
	 * lookahead time may cause instability. Prefered use is to call this
	 * function with a new setpoint (q) in each time step (thus the default
	 * t=0.008)
	 * 
	 * @param q
	 *            joint positions [rad]
	 * @param a
	 *            NOT used in current version
	 * @param v
	 *            NOT used in current version
	 * @param time
	 *            time [S]
	 * @param lookahead_time
	 *            time [S], range [0.03,0.2] smoothens the trajectory with this
	 *            lookahead time
	 * @param gain
	 *            proportional gain for following target position, range
	 *            [100,2000]
	 */
	void servoj(JointPosition q,int a, int v, @Named("t=") float time, @Named("lookahead_time=") @MinFloat(0.03f) @MaxFloat(0.2f) float lookahead_time, @Named("gain=") @Min(100) @Max(2000) int gain);

	default void servoj(JointPosition q) {
		servoj(q, 1, 1, 4, 0.03f, 100);
	}

	/**
	 * Tells the robot controller the tick count of the encoder. This function
	 * is useful for absolute encoders, use conveyor pulse decode() for setting
	 * up an incremental encoder. For circular conveyors, the value must be
	 * between 0 and the number of ticks per revolution.
	 * 
	 * @param tick_count
	 *            Tick count of the conveyor (Integer)
	 * @param absolute_encoder_resolution
	 *            Resolution of the encoder, needed to handle wrapping nicely.
	 *            (Integer) 0 is a 32 bit signed encoder, range [-2147483648 ;
	 *            2147483647] (default) 1 is a 8 bit unsigned encoder, range [0
	 *            ; 255] 2 is a 16 bit unsigned encoder, range [0 ; 65535] 3 is
	 *            a 24 bit unsigned encoder, range [0 ; 16777215] 4 is a 32 bit
	 *            unsigned encoder, range [0 ; 4294967295]
	 */
	void set_conveyor_tick_count(int tick_count, @Named("absolute_encoder_resolution=") AbsoluteEncoderResolution absolute_encoder_resolution);

	default void set_conveyor_tick_count(int tick_count) {
		set_conveyor_tick_count(tick_count, AbsoluteEncoderResolution.signed_encoder_32_bit);
	}

	/**
	 * Set joint positions of simulated robot
	 * 
	 * @param q
	 *            joint positions
	 */
	void set_pos(JointPosition q);

	/**
	 * Joint speed Accelerate to and move with constant joint speed
	 * 
	 * @param joint_speeds
	 * @param joint_acceleration
	 * @param t_min
	 */
	void speedj(float joint_speeds,float a, float t_min);

	/**
	 * Makes robot movement (movej etc.) follow the original trajectory instead
	 * of the conveyor specified by track conveyor linear() or track conveyor
	 * circular().
	 * 
	 * @param tool
	 *            speed [m/s] (spatial vector)
	 * @param a
	 *            joint tool acceleration [/s2]
	 * @param t_min
	 *            minimal time before function returns
	 */
	void speedl(Vector3d tool_speed, float tool_acceleration, float t_min);

	/**
	 * Makes robot movement (movej etc.) follow the original trajectory instead
	 * of the conveyor specified by track conveyor linear() or track conveyor
	 * circular().
	 */
	void stop_conveyor_tracking();

	/**
	 * Stop (linear in joint space) Decellerate joint speeds to zero
	 * 
	 * @param joint_acceleration
	 *            joint acceleration [rad/s2] (of leading axis)
	 */
	void stopj(float joint_acceleration);

	/**
	 * Stop (linear in tool space) Decellerate tool speed to zero
	 * 
	 * @param tool_accleration
	 *            tool accleration [m/s2]
	 */
	void stopl(float tool_accleration);

	/**
	 * Set robot in freedrive mode. In this mode the robot can be moved around
	 * by hand in the same way as by pressing the freedrive button. The
	 * robot wuill not be able to follow a trajectory (eg. a movej) in this
	 * mode.
	 */
	void teach_mode();

	/**
	 * Makes robot movement (movej() etc.) track a circular conveyor. >>> track
	 * conveyor circular(p[0.5,0.5,0,0,0,0],500.0, false) The example code makes
	 * the robot track a circular conveyor with center in p[0.5,0.5,0,0,0,0] of
	 * the robot base coordinate system, where 500 ticks on the encoder
	 * corresponds to one revolution of the circular conveyor around the center.
	 * 
	 * @param center
	 *            Pose vector that determines the center the conveyor in the
	 *            base coordinate system of the robot.
	 * @param ticks_per_revolution
	 *            How many tichs the encoder sees when the conveyor moves one
	 *            revolution.
	 * @param rotate_tool
	 *            Should the tool rotate with the coneyor or stay in the
	 *            orientation specified by the trajectory (movel() etc.).
	 */
	void track_conveyor_circular(CartesianPosition center, float ticks_per_revolution, boolean rotate_tool);

	/**
	 * Makes robot movement (movej() etc.) track a linear conveyor. >>> track
	 * conveyor linear(p[1,0,0,0,0,0],1000.0) The example code makes the robot
	 * track a conveyor in the x-axis of the robot base coordinate system, where
	 * 1000 ticks on the encoder corresponds to 1m along the x-axis.
	 * 
	 * @param direction
	 *            Pose vector that determines the direction of the conveyor in
	 *            the base coordinate system of the robot
	 * @param ticks_per_meter
	 *            How many tichs the encoder sees when the conveyor moves one
	 *            meter
	 */
	void track_conveyor_linear(CartesianPosition direction, float ticks_per_meter);

}
