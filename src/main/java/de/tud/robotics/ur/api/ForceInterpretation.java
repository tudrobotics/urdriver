package de.tud.robotics.ur.api;

/*
 * An integer specifying how the robot
interprets the force frame. 1: The force
frame is transformed in a way such
that its y-axis is aligned with a vector
pointing from the robot tcp towards
the origin of the force frame. 2: The
force frame is not transformed. 3: The
force frame is transformed in a way
such that its x-axis is the projection of
the robot tcp velocity vector onto the
x-y plane of the force frame. All other
values of type are invalid.
 */

public enum ForceInterpretation {

	FI_1(1),FI_2(2),FI_3(3);
	
	private final int value;
	
    private ForceInterpretation(int value) {
        this.value = value;
    }
    
    public String toString() {
    	return value+"";
    }
}
