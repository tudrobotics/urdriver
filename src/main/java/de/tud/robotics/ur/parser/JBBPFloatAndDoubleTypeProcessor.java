package de.tud.robotics.ur.parser;

import java.io.IOException;

import com.igormaznitsa.jbbp.JBBPCustomFieldTypeProcessor;
import com.igormaznitsa.jbbp.compiler.JBBPNamedFieldInfo;
import com.igormaznitsa.jbbp.compiler.tokenizer.JBBPFieldTypeParameterContainer;
import com.igormaznitsa.jbbp.io.JBBPBitInputStream;
import com.igormaznitsa.jbbp.io.JBBPBitOrder;
import com.igormaznitsa.jbbp.model.JBBPAbstractField;
import com.igormaznitsa.jbbp.model.JBBPFieldInt;
import com.igormaznitsa.jbbp.model.JBBPFieldLong;

public final class JBBPFloatAndDoubleTypeProcessor implements JBBPCustomFieldTypeProcessor {

	private static final String[] TYPES = new String[] { "float", "double" };

	public String[] getCustomFieldTypes() {
		return TYPES;
	}

	public boolean isAllowed(final JBBPFieldTypeParameterContainer fieldType, final String fieldName,
			final int extraData, final boolean isArray) {
		return true;
	}

	public JBBPAbstractField readCustomFieldType(final JBBPBitInputStream in, final JBBPBitOrder bitOrder,
			final int parserFlags, final JBBPFieldTypeParameterContainer customTypeFieldInfo,
			final JBBPNamedFieldInfo fieldName, final int extraData, final boolean readWholeStream,
			final int arrayLength) throws IOException {
		final JBBPAbstractField result;

		final boolean needsBitReversing = in.getBitOrder() != bitOrder;

		if (customTypeFieldInfo.getTypeName().equals("float")) {
			if (readWholeStream) {
				final int[] array = in.readIntArray(-1, customTypeFieldInfo.getByteOrder());
				if (needsBitReversing) {
					for (int i = 0; i < array.length; i++) {
						array[i] = (int) JBBPFieldInt.reverseBits(array[i]);
					}
				}
				result = new JBBPFieldArrayFloat(fieldName, array);
			} else if (arrayLength >= 0) {
				final int[] array = in.readIntArray(arrayLength, customTypeFieldInfo.getByteOrder());
				if (needsBitReversing) {
					for (int i = 0; i < array.length; i++) {
						array[i] = (int) JBBPFieldInt.reverseBits(array[i]);
					}
				}
				result = new JBBPFieldArrayFloat(fieldName, array);
			} else {
				final int value = in.readInt(customTypeFieldInfo.getByteOrder());
				result = new JBBPFieldFloat(fieldName,
						needsBitReversing ? (int) JBBPFieldInt.reverseBits(value) : value);
			}
		} else {
			if (readWholeStream) {
				final long[] array = in.readLongArray(-1, customTypeFieldInfo.getByteOrder());
				if (needsBitReversing) {
					for (int i = 0; i < array.length; i++) {
						array[i] = JBBPFieldLong.reverseBits(array[i]);
					}
				}
				result = new JBBPFieldArrayDouble(fieldName, array);
			} else if (arrayLength >= 0) {
				final long[] array = in.readLongArray(arrayLength, customTypeFieldInfo.getByteOrder());
				if (needsBitReversing) {
					for (int i = 0; i < array.length; i++) {
						array[i] = JBBPFieldLong.reverseBits(array[i]);
					}
				}
				result = new JBBPFieldArrayDouble(fieldName, array);
			} else {
				final long value = in.readLong(customTypeFieldInfo.getByteOrder());
				result = new JBBPFieldDouble(fieldName, needsBitReversing ? JBBPFieldLong.reverseBits(value) : value);
			}
		}
		return result;
	}

}