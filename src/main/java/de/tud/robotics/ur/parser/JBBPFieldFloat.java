package de.tud.robotics.ur.parser;

import com.igormaznitsa.jbbp.compiler.JBBPNamedFieldInfo;
import com.igormaznitsa.jbbp.model.JBBPAbstractField;
import com.igormaznitsa.jbbp.model.JBBPNumericField;
import com.igormaznitsa.jbbp.utils.JBBPUtils;

public final class JBBPFieldFloat extends JBBPAbstractField implements JBBPNumericField {

	private static final long serialVersionUID = 9022682441939193636L;

	private final int value;

	public JBBPFieldFloat(final JBBPNamedFieldInfo name, final int value) {
		super(name);
		this.value = value;
	}

	public float getAsFloat() {
		return Float.intBitsToFloat(this.value);
	}

	public int getAsInt() {
		return this.value;
	}

	public long getAsLong() {
		return this.getAsInt();
	}

	public boolean getAsBool() {
		return this.value != 0;
	}

	public static long reverseBits(final int value) {
		final int b0 = JBBPUtils.reverseBitsInByte((byte) value) & 0xFF;
		final int b1 = JBBPUtils.reverseBitsInByte((byte) (value >> 8)) & 0xFF;
		final int b2 = JBBPUtils.reverseBitsInByte((byte) (value >> 16)) & 0xFF;
		final int b3 = JBBPUtils.reverseBitsInByte((byte) (value >> 24)) & 0xFF;

		return (long) ((b0 << 24) | (b1 << 16) | (b2 << 8) | b3);
	}

	public long getAsInvertedBitOrder() {
		return reverseBits(this.value);
	}

	@Override
	public String getTypeAsString() {
		return "float";
	}

}