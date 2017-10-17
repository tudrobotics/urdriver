package de.tud.robotics.ur;

import java.io.IOException;
import java.io.StringWriter;
import java.lang.annotation.Annotation;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.apache.commons.lang3.Validate;

import de.tud.robotics.ur.api.MaxFloat;
import de.tud.robotics.ur.api.MinFloat;
import de.tud.robotics.ur.api.Named;
import de.tud.robotics.ur.api.Optional;


public class URScriptInvocationHandler implements InvocationHandler {

	private static final Logger LOG = Logger.getLogger(URScriptInvocationHandler.class.getSimpleName());
	protected static final String newline = "\n";

	protected Map<Method, String> commandCache;

	private URClient client; 
	
	public URScriptInvocationHandler(URClient client) {
		this.client = client;
		commandCache = new HashMap<Method, String>();
	}

	/**
	 * this method produce the call to UR
	 */
	/*
	 * @Override public synchronized Object invoke(Object proxy, Method method,
	 * Object[] args) throws Throwable { // support default messages in
	 * interfaces if (method.isDefault()) { return handleDefault(proxy, method,
	 * args); }
	 * 
	 * StringWriter call = new StringWriter(); call.append(method.getName());
	 * call.append("("); if (args != null) { Annotation[][] parameterAnnotations
	 * = method.getParameterAnnotations(); Annotation[] annotationOfParameter;
	 * for (int i = 0; i < args.length; i++) { Validate.notNull(args[i],
	 * args[i].getClass().getName()+" must be not NULL"); annotationOfParameter
	 * = parameterAnnotations[i]; for (Annotation a : annotationOfParameter) {
	 * //resolve the @Name annotation if (a instanceof Named) {
	 * call.append(((Named) a).value()); } // resolve the @Min annotation if(a
	 * instanceof Min) { try { Validate.isTrue(Long.valueOf(args[i].toString())
	 * >= ((Min)a).value(), ((Min)a).message()); } catch (NumberFormatException
	 * e){
	 * 
	 * } } // resolve the @Max annotation if(a instanceof Max) { try {
	 * Validate.isTrue(Long.valueOf(args[i].toString()) <= ((Max)a).value(),
	 * ((Max)a).message()); } catch (NumberFormatException e){
	 * 
	 * } } // resolve the @MinFloat annotation if(a instanceof MinFloat) { try {
	 * Validate.isTrue(Double.valueOf(args[i].toString()) >=
	 * ((MinFloat)a).value(), ((MinFloat)a).message()); } catch
	 * (NumberFormatException e){
	 * 
	 * } } // resolve the @MaxFloat annotation if(a instanceof MaxFloat) { try {
	 * Validate.isTrue(Double.valueOf(args[i].toString()) <=
	 * ((MaxFloat)a).value(),((MaxFloat)a).message()); } catch
	 * (NumberFormatException e){
	 * 
	 * } } } call.append(args[i].toString()); if (i + 1 < args.length) {
	 * call.append(","); } } } call.append(")" + newline); LOG.log(Level.FINE,
	 * "call " + proxy.getClass().getInterfaces()[0].getSimpleName() + "." +
	 * call.toString()); System.out.println(call.toString()); // if the
	 * returntype is null denn wie call the method if
	 * (method.getReturnType().equals(Void.class) ||
	 * method.getReturnType().equals(Void.TYPE)) {
	 * client.write(call.toString()); } else { // TODO return requested value }
	 * return null; }
	 */

	@Override
	public synchronized Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		// support default messages in interfaces
		if (method.isDefault()) {
			return handleDefault(proxy, method, args);
		}
		// this throws an exception if Arguments are wrong
		validateArguments(method, args);

		String command = buildURCommand(method);
		// replace the generic placeholder
		command = fillURCommand(command, args);

		LOG.log(Level.FINE, "call " + proxy.getClass().getInterfaces()[0].getSimpleName() + "." + command);
		return callURCommand(method, command);
	}

	private String fillURCommand(String command, Object[] args) {
		for (int i = 0; i < args.length; i++) {
			command = command.replace("?" + i, args[i].toString());
		}
		return command;
	}

	private Object callURCommand(Method method, String command) throws IOException {
		// if the returntype is null denn wie call the method
		if (method.getReturnType().equals(Void.class) || method.getReturnType().equals(Void.TYPE)) {
			//System.out.println(command);
			client.write(command + newline);
		} else {
			LOG.log(Level.WARNING,"invoking methods with returnvalue not supported");
			// TODO return requested value
		}
		return null;
	}
	protected String buildURCommand(Method method) {
		if (commandCache.get(method) != null)
			return commandCache.get(method);
		StringWriter call = new StringWriter();
		call.append(method.getName());
		call.append("(");
		Annotation[][] parameterAnnotations = method.getParameterAnnotations();
		Annotation[] annotationOfParameter;

		for (int i = 0; i < method.getParameterTypes().length; i++) {
			// resolve annotation for call string
			annotationOfParameter = parameterAnnotations[i];
			for (Annotation a : annotationOfParameter) {
				// resolve the @Named annotation
				if (a instanceof Named) {
					call.append(((Named) a).value());
				}
			}
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

	protected void validateArguments(Method method, Object[] args) {
		if (args == null)
			return;
		Annotation[][] parameterAnnotations = method.getParameterAnnotations();
		Annotation[] annotationOfParameter;
		for (int i = 0; i < args.length; i++) {
			Validate.notNull(args[i], args[i].getClass().getName() + " must be not NULL");
			annotationOfParameter = parameterAnnotations[i];
			for (Annotation a : annotationOfParameter) {
				// resolve @Optional annotation 
				if(!(a instanceof Optional)) {
					
				}
				// resolve the @Min annotation
				if (a instanceof Min) {
					try {
						Validate.isTrue(Long.valueOf(args[i].toString()) >= ((Min) a).value(), ((Min) a).message());
					} catch (NumberFormatException e) {

					}
				}
				// resolve the @Max annotation
				if (a instanceof Max) {
					try {
						Validate.isTrue(Long.valueOf(args[i].toString()) <= ((Max) a).value(), ((Max) a).message());
					} catch (NumberFormatException e) {

					}
				}
				// resolve the @MinFloat annotation
				if (a instanceof MinFloat) {
					try {
						Validate.isTrue(Double.valueOf(args[i].toString()) >= ((MinFloat) a).value(),
								((MinFloat) a).message());
					} catch (NumberFormatException e) {

					}
				}
				// resolve the @MaxFloat annotation
				if (a instanceof MaxFloat) {
					try {
						Validate.isTrue(Double.valueOf(args[i].toString()) <= ((MaxFloat) a).value(),
								((MaxFloat) a).message());
					} catch (NumberFormatException e) {

					}
				}
			}
		}
	}

	private Object handleDefault(Object proxy, Method method, Object[] args) throws Throwable {
		// support default messages in interfaces
		if (method.isDefault()) {
			final Class<?> declaringClass = method.getDeclaringClass();
			final MethodHandles.Lookup lookup = MethodHandles.publicLookup().in(declaringClass);
			// ensure allowed mode will not check visibility
			final Field f = MethodHandles.Lookup.class.getDeclaredField("allowedModes");
			final int modifiers = f.getModifiers();
			if (Modifier.isFinal(modifiers)) { // should be done a single time
				final Field modifiersField = Field.class.getDeclaredField("modifiers");
				modifiersField.setAccessible(true);
				modifiersField.setInt(f, modifiers & ~Modifier.FINAL);
				f.setAccessible(true);
				f.set(lookup, MethodHandles.Lookup.PRIVATE);
			}
			return lookup.unreflectSpecial(method, declaringClass).bindTo(proxy).invokeWithArguments(args);
		}
		return null;
	}
}
