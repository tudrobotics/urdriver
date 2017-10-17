package de.tud.robotics.ur.client;

import java.util.HashMap;
import java.util.Map;

public enum JointMode {
	
	UNKNOWN(-1),
	JOINT_SHUTTING_DOWN_MODE(236),			
	JOINT_PART_D_CALIBRATION_MODE(237),					
	JOINT_BACKDRIVE_MODE(238),				
	JOINT_POWER_OFF_MODE(239),			
	JOINT_NOT_RESPONDING_MODE(245),
	JOINT_MOTOR_INITIALISATION_MODE(246),			
	JOINT_BOOTING_MODE(247)		,			
	JOINT_PART_D_CALIBRATION_ERROR_MODE(248),					
	JOINT_BOOTLOADER_MODE(249),
	JOINT_CALIBRATION_MODE(250),
	JOINT_FAULT_MODE(252),
	JOINT_RUNNING_MODE(253),
	JOINT_IDLE_MODE(255);			
	
	private final int value;
	
    private JointMode(int value) {
        this.value = value;
    }
    
    public int toInt() {
    	return value;
    }
    
    private static final Map<Integer, JointMode> map = new HashMap<Integer, JointMode>();
    static
    {
        for (JointMode t : JointMode.values())
            map.put(t.value, t);
    }
    public static JointMode from(int value) {
    	JointMode v = map.get(value);
		return v != null ? v : JointMode.UNKNOWN;
    }
}
