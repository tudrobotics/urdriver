package de.tud.robotics.ur.client;

import java.util.HashMap;
import java.util.Map;

public enum RobotMessageType {
	
	UNKNOWN(-1),
	ROBOT_STATE(16);
	
	private final int value;
	
    private RobotMessageType(int value) {
        this.value = value;
    }
    
    public int toInt() {
    	return value;
    }
    
    private static final Map<Integer, RobotMessageType> map = new HashMap<Integer, RobotMessageType>();
    static
    {
        for (RobotMessageType t : RobotMessageType.values())
            map.put(t.value, t);
    }
    public static RobotMessageType from(int value) {
    	RobotMessageType v = map.get(value);
		return v != null ? v : RobotMessageType.UNKNOWN;
    }
}
