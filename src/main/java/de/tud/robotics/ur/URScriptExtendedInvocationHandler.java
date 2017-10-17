package de.tud.robotics.ur;

import java.io.StringWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import de.tud.robotics.ur.api.InnerFunction;
import de.tud.robotics.ur.api.Named;

public class URScriptExtendedInvocationHandler extends URScriptInvocationHandler {

	public URScriptExtendedInvocationHandler(URClient client) {
		super(client);
	}
	
	@Override
	protected String buildURCommand(Method method) {
			if (commandCache.get(method) != null) {
				return commandCache.get(method);
			}
			StringWriter call = new StringWriter();
			call.append(method.getName());
			call.append("(");
			
			Annotation[][] parameterAnnotations = method.getParameterAnnotations();
			Annotation[] annotationOfParameter;
			boolean innerFunctionExists = false;
			for (int i = 0; i < method.getParameterTypes().length; i++) {
				innerFunctionExists = false;
				// resolve annotation for call string
				annotationOfParameter = parameterAnnotations[i];
				for (Annotation a : annotationOfParameter) {
					// resolve the @Named annotation
					if (a instanceof Named) {
						call.append(((Named) a).value());
					}
					// resolve @InnerFunction
					if(a instanceof InnerFunction) {
						Method m = null;
						try {
							m = ((InnerFunction) a).clazz().getDeclaredMethod(((InnerFunction) a).method(), ((InnerFunction) a).arguments());
						} catch (NoSuchMethodException | SecurityException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						String subCommand = buildURCommand(m);
						call.append(subCommand);
						innerFunctionExists = true;
					}
				}
				if(!innerFunctionExists)
					call.append("?" + i);
				if (i + 1 < method.getParameterTypes().length) {
					call.append(",");
				}
			}
			call.append(")");

			// add to cache
			commandCache.put(method, call.toString());
			return call.toString();

		}
	
}
