package de.tud.robotics.ur.parser;

import com.igormaznitsa.jbbp.compiler.JBBPNamedFieldInfo;
import com.igormaznitsa.jbbp.model.JBBPAbstractField;
import com.igormaznitsa.jbbp.model.JBBPNumericField;
import com.igormaznitsa.jbbp.utils.JBBPUtils;

public final class JBBPFieldDouble extends JBBPAbstractField implements JBBPNumericField {

	private static final long serialVersionUID = 8571285179176757539L;

	private final long value;

	public JBBPFieldDouble(final JBBPNamedFieldInfo name, final long value) {
		super(name);
		this.value = value;
	}

	public double getAsDouble() {
		return Double.longBitsToDouble(this.value);
	}

	public int getAsInt() {
		return (int) this.value;
	}

	public long getAsLong() {
		return this.value;
	}

	public boolean getAsBool() {
		return this.value != 0;
	}

	public static long reverseBits(final long value) {
		final long b0 = JBBPUtils.reverseBitsInByte((byte) value) & 0xFFL;
		final long b1 = JBBPUtils.reverseBitsInByte((byte) (value >> 8)) & 0xFFL;
		final long b2 = JBBPUtils.reverseBitsInByte((byte) (value >> 16)) & 0xFFL;
		final long b3 = JBBPUtils.reverseBitsInByte((byte) (value >> 24)) & 0xFFL;
		final long b4 = JBBPUtils.reverseBitsInByte((byte) (value >> 32)) & 0xFFL;
		final long b5 = JBBPUtils.reverseBitsInByte((byte) (value >> 40)) & 0xFFL;
		final long b6 = JBBPUtils.reverseBitsInByte((byte) (value >> 48)) & 0xFFL;
		final long b7 = JBBPUtils.reverseBitsInByte((byte) (value >> 56)) & 0xFFL;

		return (b0 << 56) | (b1 << 48) | (b2 << 40) | (b3 << 32) | (b4 << 24) | (b5 << 16) | (b6 << 8) | b7;
	}

	public long getAsInvertedBitOrder() {
		return reverseBits(this.value);
	}

	@Override
	public String getTypeAsString() {
		return "double";
	}

}