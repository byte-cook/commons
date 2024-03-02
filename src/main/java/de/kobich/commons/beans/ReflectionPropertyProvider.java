package de.kobich.commons.beans;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

public class ReflectionPropertyProvider<T> implements IPropertyProvider<T> {
	private static final Logger logger = Logger.getLogger(ReflectionPropertyProvider.class);
	private final Class<?> clazz;
	private final Map<String, Method> readMethods;
	private final Map<String, Method> writeMethods;

	public ReflectionPropertyProvider(Class<?> clazz) {
		this.clazz = clazz;
		this.readMethods = new HashMap<String, Method>();
		this.writeMethods = new HashMap<String, Method>();
	}

	@Override
	public <R> R getProperty(T obj, String name, Class<R> returnType, Object... args) {
		Object result = getProperty(obj, name, args);
		if (result != null && result.getClass().isAssignableFrom(returnType)) {
			return returnType.cast(result);
		}
		return null;
	}

	@Override
	public Object getProperty(T obj, String name, Object... args) {
		Method method = readMethods.get(name);
		if (method != null) {
			try {
				Object result = method.invoke(obj, args);
				return result;
			}
			catch (Exception exc) {
				logger.warn("Error while invoking method: " + method.getName(), exc);
				return null;
			}
		}
		logger.warn("No read method defined for property: " + name);
		return null;
	}

	@Override
	public void setProperty(T o, String name, Object... args) {
		Method method = writeMethods.get(name);
		if (method != null) {
			try {
				method.invoke(o, args);
				return;
			}
			catch (Exception exc) {
				logger.warn("Error while invoking method: " + method.getName(), exc);
			}
		}
		logger.warn("No write method defined for property: " + name);
	}
	
	public void setReadMethod(String name, String methodName, Class<?>... parameterTypes) {
		try {
			Method method = clazz.getMethod(methodName, parameterTypes);
			this.readMethods.put(name, method);
		}
		catch (Exception exc) {
		}
	}
	public void setWriteMethod(String name, String methodName, Class<?>... parameterTypes) {
		try {
			Method method = clazz.getMethod(methodName, parameterTypes);
			this.writeMethods.put(name, method);
		}
		catch (Exception exc) {
			exc.printStackTrace();
		}
	}
	
	protected void setWriteMethod(String name, Method method) {
		writeMethods.put(name, method);
	}
	protected void setReadMethod(String name, Method method) {
		readMethods.put(name, method);
	}
	
}
