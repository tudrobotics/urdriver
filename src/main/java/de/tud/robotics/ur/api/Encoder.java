package de.tud.robotics.ur.api;

/*
 * An integer determining how to treat the inputs on A
and B
0 is no encoder, pulse decoding is disabled.
1 is quadrature encoder, input A and B must be
square waves with 90 degree offset. Direction of the
conveyor can be determined.
2 is rising and falling edge on single input (A).
3 is rising edge on single input (A).
4 is falling edge on single input (A).
 */
public enum Encoder {

	NO_ENCODER(0), QUADRATURE_ENCODER(1), RISING_FALLING_EDGE(2), RISING_SINGLE_INPUT(3), FALLING_SINGLE_INPUT(4);
	
	private final int value;
	
    private Encoder(int value) {
        this.value = value;
    }
    
    
    public String toString() {
    	return value+"";
    }
}
