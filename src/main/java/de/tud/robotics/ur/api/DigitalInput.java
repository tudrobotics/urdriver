package de.tud.robotics.ur.api;

public enum DigitalInput {

	INPUT_0(0), INPUT_1(1), INPUT_2(2), INPUT_3(3), ;
	
	private final int value;
	
    private DigitalInput(int value) {
        this.value = value;
    }
    
    public int toInt() {
    	return value;
    }
    
    public String toString() {
    	return value+"";
    }
}
