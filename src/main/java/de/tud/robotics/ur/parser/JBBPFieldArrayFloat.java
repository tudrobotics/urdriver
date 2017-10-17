package de.tud.robotics.ur.parser;

import com.igormaznitsa.jbbp.compiler.JBBPNamedFieldInfo;
import com.igormaznitsa.jbbp.model.JBBPAbstractArrayField;
import com.igormaznitsa.jbbp.model.JBBPFieldInt;
import com.igormaznitsa.jbbp.utils.JBBPUtils;

public final class JBBPFieldArrayFloat extends JBBPAbstractArrayField<JBBPFieldFloat> {

	private static final long serialVersionUID = 9088456752092618676L;

	private final int[] array;

	public JBBPFieldArrayFloat(final JBBPNamedFieldInfo name, final int[] array) {
		super(name);
		JBBPUtils.assertNotNull(array, "Array must not be null");
		this.array = array;
	}

	public float[] getArray() {
		final float[] result = new float[this.array.length];
		for (int i = 0; i < result.length; i++) {
			result[i] = Float.intBitsToFloat(this.array[i]);
		}
		return result;
	}

	@Override
	public int size() {
		return this.array.length;
	}

	@Override
	public JBBPFieldFloat getElementAt(final int index) {
		final JBBPFieldFloat result = new JBBPFieldFloat(this.fieldNameInfo, this.array[index]);
		result.setPayload(this.payload);
		return result;
	}

	@Override
	public int getAsInt(final int index) {
		return this.array[index];
	}

	@Override
	public long getAsLong(final int index) {
		return this.getAsInt(index);
	}

	@Override
	public boolean getAsBool(final int index) {
		return this.array[index] != 0;
	}

	public float getAsFloat(final int index) {
		return Float.intBitsToFloat(this.array[index]);
	}

	@Override
	public Object getValueArrayAsObject(final boolean reverseBits) {
		final float[] result = new float[this.array.length];
		if (reverseBits) {
			for (int i = 0; i < result.length; i++) {
				result[i] = Float.intBitsToFloat((int) JBBPFieldInt.reverseBits(this.array[i]));
			}
		} else {
			for (int i = 0; i < result.length; i++) {
				result[i] = Float.intBitsToFloat(this.array[i]);
			}
		}
		return result;
	}

	@Override
	public String getTypeAsString() {
		return "float " + '[' + this.array.length + ']';
	}
}
