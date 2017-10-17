package de.tud.robotics.ur.client;

import java.util.HashMap;
import java.util.Map;

public enum ControlMode {

	UNKNOWN(-1),
	CONTROL_MODE_POSITION(0),			
	CONTROL_MODE_TEACH(1),				
	CONTROL_MODE_FORCE(2),			
	CONTROL_MODE_TORQUE(3);			
	
	private final int value;
	
    private ControlMode(int value) {
        this.value = value;
    }
    
    public int toInt() {
    	return value;
    }
    
    private static final Map<Integer, ControlMode> map = new HashMap<Integer, ControlMode>();
    static
    {
        for (ControlMode t : ControlMode.values())
            map.put(t.value, t);
    }
    public static ControlMode from(int value) {
    	ControlMode v = map.get(value);
		return v != null ? v : ControlMode.UNKNOWN;
    }
}
