package de.tud.robotics.ur.client;

import java.util.HashMap;
import java.util.Map;

public enum SafetyMode {

	UNKNOWN(-1),
	SAFETY_MODE_NORMAL(0),				
	SAFETY_MODE_REDUCED(2),			
	SAFETY_MODE_PROTECTIVE_STOP(3),			
	SAFETY_MODE_RECOVERY(4),			
	SAFETY_MODE_SAFEGUARD_STOP(5),				
	SAFETY_MODE_SYSTEM_EMERGENCY_STOP(6),				
	SAFETY_MODE_ROBOT_EMERGENCY_STOP(7),				
	SAFETY_MODE_VIOLATION(8),				
	SAFETY_MODE_FAULT(9);			
	
	private final int value;
	
    private SafetyMode(int value) {
        this.value = value;
    }
    
    public int toInt() {
    	return value;
    }
    
    private static final Map<Integer, SafetyMode> map = new HashMap<Integer, SafetyMode>();
    static
    {
        for (SafetyMode t : SafetyMode.values())
            map.put(t.value, t);
    }
    public static SafetyMode from(int value) {
    	SafetyMode v = map.get(value);
		return v != null ? v : SafetyMode.UNKNOWN;
    }
}
