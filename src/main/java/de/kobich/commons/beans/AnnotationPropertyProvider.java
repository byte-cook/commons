package de.kobich.commons.beans;

import java.lang.reflect.Method;

public class AnnotationPropertyProvider<T> extends ReflectionPropertyProvider<T> implements IPropertyProvider<T> {
	public AnnotationPropertyProvider(Class<?> clazz) {
		super(clazz);
		Method[] methods = clazz.getMethods();
		for (Method method : methods) {
			if (method.isAnnotationPresent(GetProperty.class)) {
				GetProperty property = method.getAnnotation(GetProperty.class);
				for (String name : property.name()) {
					setReadMethod(name, method);
				}
			}
			else if (method.isAnnotationPresent(SetProperty.class)) { 
				SetProperty property = method.getAnnotation(SetProperty.class);
				for (String name : property.name()) {
					setWriteMethod(name, method);
				}
			}
		}
	}

}
