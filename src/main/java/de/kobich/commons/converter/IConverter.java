package de.kobich.commons.converter;

/**
 * Converter
 * @author ckorn
 * @param <S> source type
 * @param <T> target type
 */
public interface IConverter<S, T> {
	/**
	 * Converts from source to target
	 * @param o
	 * @return
	 */
	public T convert(S s);

	/**
	 * Reconverts from target to source
	 * @param t
	 * @return
	 */
	public S reconvert(T t);
}
