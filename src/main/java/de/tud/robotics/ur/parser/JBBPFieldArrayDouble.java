package de.tud.robotics.ur.parser;

import com.igormaznitsa.jbbp.compiler.JBBPNamedFieldInfo;
import com.igormaznitsa.jbbp.model.JBBPAbstractArrayField;
import com.igormaznitsa.jbbp.model.JBBPFieldLong;
import com.igormaznitsa.jbbp.utils.JBBPUtils;

public final class JBBPFieldArrayDouble extends JBBPAbstractArrayField<JBBPFieldDouble> {

	private static final long serialVersionUID = 5143347696236941029L;

	private final long[] array;

	public JBBPFieldArrayDouble(final JBBPNamedFieldInfo name, final long[] array) {
		super(name);
		JBBPUtils.assertNotNull(array, "Array must not be null");
		this.array = array;
	}

	public double[] getArray() {
		final double[] result = new double[this.array.length];
		for (int i = 0; i < result.length; i++) {
			result[i] = Double.longBitsToDouble(this.array[i]);
		}
		return result;
	}

	@Override
	public int size() {
		return this.array.length;
	}

	@Override
	public JBBPFieldDouble getElementAt(final int index) {
		final JBBPFieldDouble result = new JBBPFieldDouble(this.fieldNameInfo, this.array[index]);
		result.setPayload(this.payload);
		return result;
	}

	public double getAsDouble(final int index) {
		return Double.longBitsToDouble(this.array[index]);
	}

	@Override
	public int getAsInt(final int index) {
		return (int) this.array[index];
	}

	@Override
	public long getAsLong(final int index) {
		return this.array[index];
	}

	@Override
	public boolean getAsBool(final int index) {
		return this.array[index] != 0L;
	}

	@Override
	public Object getValueArrayAsObject(final boolean reverseBits) {
		final double[] result = new double[this.array.length];
		if (reverseBits) {
			for (int i = 0; i < result.length; i++) {
				result[i] = Double.longBitsToDouble(JBBPFieldLong.reverseBits(this.array[i]));
			}
		} else {
			for (int i = 0; i < result.length; i++) {
				result[i] = Double.longBitsToDouble(this.array[i]);
			}
		}
		return result;
	}

	@Override
	public String getTypeAsString() {
		return "double " + '[' + this.array.length + ']';
	}
}