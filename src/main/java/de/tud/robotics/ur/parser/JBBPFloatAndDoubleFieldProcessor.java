package de.tud.robotics.ur.parser;

import java.lang.reflect.Field;

import com.igormaznitsa.jbbp.mapper.Bin;
import com.igormaznitsa.jbbp.mapper.JBBPMapperCustomFieldProcessor;
import com.igormaznitsa.jbbp.model.JBBPFieldStruct;

public final class JBBPFloatAndDoubleFieldProcessor implements JBBPMapperCustomFieldProcessor{

	@Override
	public Object prepareObjectForMapping(JBBPFieldStruct parsedBlock, Bin annotation, Field field) {
		if(field.getType().getName().equalsIgnoreCase("double")) {
			return parsedBlock.findFieldForNameAndType(field.getName(), JBBPFieldDouble.class).getAsDouble();
		}
		if(field.getType().getName().equalsIgnoreCase("float")) {
			return parsedBlock.findFieldForNameAndType(field.getName(), JBBPFieldFloat.class).getAsFloat();
		}
		return -1.0;
	}

}
