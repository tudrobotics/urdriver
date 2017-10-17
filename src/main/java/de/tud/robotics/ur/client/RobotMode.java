package de.tud.robotics.ur.client;

import java.util.HashMap;
import java.util.Map;

public enum RobotMode {
	UNKNOWN(-1),
	ROBOT_MODE_DISCONNECTED(0),
	ROBOT_MODE_CONFIRM_SAFETY(1),
	ROBOT_MODE_BOOTING(2),
	ROBOT_MODE_POWER_OFF(3),
	ROBOT_MODE_POWER_ON(4),
	ROBOT_MODE_IDLE(5),
	ROBOT_MODE_BACKDRIVE(6),
	ROBOT_MODE_RUNNING(7),				
	ROBOT_MODE_UPDATING_FIRMWARE(8);			
	
	private final int value;
	
    private RobotMode(int value) {
        this.value = value;
    }
    
    public int toInt() {
    	return value;
    }
    
    private static final Map<Integer, RobotMode> map = new HashMap<Integer, RobotMode>();
    static
    {
        for (RobotMode t : RobotMode.values())
            map.put(t.value, t);
    }
    public static RobotMode from(int value) {
    	RobotMode v = map.get(value);
		return v != null ? v : RobotMode.UNKNOWN;
    }
}
