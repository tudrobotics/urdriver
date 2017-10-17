package de.tud.robotics.ur.api;

public enum Joint {

	BASE(0), SHOULDER(1),ELBOW(2),WRIST1(3),WRIST2(4),WRIST3(5);
	
	private final int value;
	
    private Joint(int value) {
        this.value = value;
    }
    
    public int toInt() {
    	return value;
    }
    
    public String toString() {
    	return value+"";
    }
}
