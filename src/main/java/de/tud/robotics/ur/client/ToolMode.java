package de.tud.robotics.ur.client;

import java.util.HashMap;
import java.util.Map;

public enum ToolMode {

	UNKNOWN(-1),
	TOOL_BOOTLOADER_MODE(249),			
	TOOL_RUNNING_MODE(253),			
	TOOL_IDLE_MODE(255);		
	
	private final int value;
	
    private ToolMode(int value) {
        this.value = value;
    }
    
    public int toInt() {
    	return value;
    }
    
    private static final Map<Integer, ToolMode> map = new HashMap<Integer, ToolMode>();
    static
    {
        for (ToolMode t : ToolMode.values())
            map.put(t.value, t);
    }
    public static ToolMode from(int value) {
    	ToolMode v = map.get(value);
		return v != null ? v : ToolMode.UNKNOWN;
    }
}
