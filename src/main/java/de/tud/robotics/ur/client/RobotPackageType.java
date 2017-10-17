package de.tud.robotics.ur.client;

import java.util.HashMap;
import java.util.Map;

public enum RobotPackageType {
	
	UNKNOWN(-1),
	ROBOT_MODE_DATA(0),
	JOINT_DATA(1),
	TOOL_DATA(2),
	MASTERBOARD_DATA(3),
	CARTESIAN_DATA(4),
	KINEMATICS_DATA(5),
	CONFIGURATION_DATA(6),
	FORCE_MODE_DATA(7),
	ADDITIONAL_INFO(8),
	CALIBRATION_DATA(9);
	
	public static int ADDITIONAL_INFO_LENGTH = 8;
	public static int CARTESIAN_DATA_LENGTH = 101;
	public static int CONFIGURATION_DATA_LENGTH = 445;
	public static int FORCE_MODE_DATA_LENGTH = 61;
	public static int JOINT_DATA_LENGTH = 251;
	public static int KINEMATICS_DATA_LENGTH = 225;
	public static int MASTERBOARD_DATA_LENGTH = 74;
	public static int ROBOT_MODE_DATA_LENGTH = 46;
	public static int TOOL_DATA_LENGTH = 37;
	
	private final int value;
	
    private RobotPackageType(int value) {
        this.value = value;
    }
    
    public int toInt() {
    	return value;
    }
    
    private static final Map<Integer, RobotPackageType> map = new HashMap<Integer, RobotPackageType>();
    static
    {
        for (RobotPackageType t : RobotPackageType.values())
            map.put(t.value, t);
    }
    public static RobotPackageType from(int value) {
    	RobotPackageType v = map.get(value);
		return v != null ? v : RobotPackageType.UNKNOWN;
    }
}
