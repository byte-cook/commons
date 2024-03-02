package de.kobich.commons.converter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ConverterUtils {
	public static <S, T> Collection<T> convert(Collection<S> source, IConverter<S, T> converter) {
		Collection<T> targets = new ArrayList<T>();
		for (S s : source) {
			T t = converter.convert(s);
			targets.add(t);
		}
		return targets;
	}
	
	public static <S, T> List<T> convertList(List<S> source, IConverter<S, T> converter) {
		return new ArrayList<T>(convert(source, converter));
	}
	
	public static <S, T> Set<T> convertSet(Set<S> source, IConverter<S, T> converter) {
		return new HashSet<T>(convert(source, converter));
	}
	
	public static <S, T> Collection<S> reconvert(Collection<T> target, IConverter<S, T> converter) {
		Collection<S> targets = new ArrayList<S>();
		for (T t : target) {
			S s = converter.reconvert(t);
			targets.add(s);
		}
		return targets;
	}
	
	public static <S, T> List<S> reconvertList(List<T> source, IConverter<S, T> converter) {
		return new ArrayList<S>(reconvert(source, converter));
	}
	
	public static <S, T> Set<S> reconvertSet(Set<T> source, IConverter<S, T> converter) {
		return new HashSet<S>(reconvert(source, converter));
	}
	
}
