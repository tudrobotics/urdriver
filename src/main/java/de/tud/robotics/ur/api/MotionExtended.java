package de.tud.robotics.ur.api;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

public interface MotionExtended extends Motion {

	void servoj(@InnerFunction(clazz = Internals.class, method = "get_inverse_kin", arguments = { CartesianPosition.class }) CartesianPosition c,int a, int v, @Named("t=") float time, @Named("lookahead_time=") @MinFloat(0.03f) @MaxFloat(0.2f) float lookahead_time, @Named("gain=") @Min(100) @Max(2000) int gain);

	default void servoj(CartesianPosition c) {
		servoj(c, 1, 1, 4, 0.03f, 100);
	}
}
