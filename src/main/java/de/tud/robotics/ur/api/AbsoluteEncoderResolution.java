package de.tud.robotics.ur.api;

/*
 * 0 is a 32 bit signed
encoder, range
[-2147483648 ;
2147483647] (default)
1 is a 8 bit unsigned
encoder, range [0 ; 255]
2 is a 16 bit unsigned
encoder, range [0 ;
65535]
3 is a 24 bit unsigned
encoder, range [0 ;
16777215]
4 is a 32 bit unsigned
encoder, range [0 ;
4294967295]
 */
public enum AbsoluteEncoderResolution {
	
	signed_encoder_32_bit(0), unsigned_encoder_8_bit(1), unsigned_encoder_16_bit(2), 
	unsigned_encoder_24_bit(3), unsigned_encoder_32_bit(4);
	
private final int value;
	
    private AbsoluteEncoderResolution(int value) {
        this.value = value;
    }
    
    public int toInt() {
    	return value;
    }
    
    public String toString() {
    	return value+"";
    }
}
