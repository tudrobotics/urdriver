package de.tud.robotics.ur;

import java.io.StringWriter;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.tud.robotics.ur.api.ExpectedReturn;

public class URDashboardInvocationHandler implements InvocationHandler {

	private static final Logger LOG = Logger.getLogger(URDashboardInvocationHandler.class.getSimpleName());
	private static final String newline = "\n";
	protected Map<Method, String> commandCache;
	
	private DashboardURClient client;

	public URDashboardInvocationHandler(DashboardURClient urDashboardClient) {
		this.client = urDashboardClient;
		commandCache = new HashMap<Method, String>();
	}

	@Override
	public synchronized Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		if (method.isDefault()) {
			return handleDefaultMessage(proxy, method, args);
		}

		String command = buildURCommand(method);
		// replace the generic placeholder
		command = fillURCommand(command, args);

		String expectedReturn = null;

		// check if @ExpectedReturn is present
		if (method.isAnnotationPresent(ExpectedReturn.class)) {
			ExpectedReturn annotInstance = method.getAnnotation(ExpectedReturn.class);
			expectedReturn = annotInstance.value();
			for (int i = 0; i < annotInstance.parameters().length; i++) {
				expectedReturn += " " + args[i];
			}
		}

		LOG.log(Level.FINE, "call " + proxy.getClass().getInterfaces()[0].getSimpleName() + "." + command);
		//System.out.println(command);
		String string = client.call(command + newline);
		if ((method.getReturnType().equals(Boolean.class) || method.getReturnType().equals(Boolean.TYPE))) {
			if (expectedReturn != null) {
				if (string != null) {
					//System.out.println(string);
					return string.equalsIgnoreCase(expectedReturn);
				} else {
					return false;
				}
			} else {
				return true;
			}
		}
		return string;
	}

	private String fillURCommand(String command, Object[] args) {
		if (args != null)
			for (int i = 0; i < args.length; i++) {
				command = command.replace("?" + i, args[i].toString());
			}
		return command;
	}

	private String buildURCommand(Method method) {
		if (commandCache.get(method) != null) {
			return commandCache.get(method);
		}
		StringWriter call = new StringWriter();
		call.append(method.getName().replace("_", " "));
		// build command for caching
		if (method.getParameterTypes().length > 1) {
			throw new RuntimeException("URDashboard cant handle more than one argument");
		}
		for (int i = 0; i < method.getParameterTypes().length; i++) {
			call.append(" ?" + i);
		}
		return call.toString();
	}

	private Object handleDefaultMessage(Object proxy, Method method, Object[] args) throws Throwable {
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
