package de.kobich.commons.format;

import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;

/**
 * Formatter for Roman numbers.
 * @author ckorn
 */
public class RomanFormat extends Format {
	private static final long serialVersionUID = -3247061821127453871L;
	private final static int[] arabic = { 1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1 };
	private final static String[] roman = { "M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I" };

	public RomanFormat() {}

	/**
	 * Converts integer to Roman number
	 * @param n
	 * @return
	 */
	public static String toRoman(int n) {
		if (Math.abs(n) > 4000) {
			return null;
		}
		String sign = n > 0 ? "" : "-";
		return (n == 0) ? "" : reduce(Math.abs(n), 0, new StringBuilder(sign));
	}

	private static String reduce(int n, int index, StringBuilder sb) {
		for (int i = index; i < arabic.length; i++) {
			if (arabic[i] <= n) {
				return reduce(n - arabic[i], i, sb.append(roman[i]));
			}
		}
		return sb.toString();
	}

	private StringBuffer format(int number, StringBuffer toAppendTo, FieldPosition pos) {
		pos.setBeginIndex(0);
		pos.setEndIndex(0);

		String roman = RomanFormat.toRoman(number);

		toAppendTo.append(roman);
		StringBuffer sb = new StringBuffer();
		sb.append(roman);
		return sb;
	}

	/*
	 * (non-Javadoc)
	 * @see java.text.Format#format(java.lang.Object, java.lang.StringBuffer, java.text.FieldPosition)
	 */
	@Override
	public StringBuffer format(Object number, StringBuffer toAppendTo, FieldPosition pos) {
		if (number instanceof Number) {
			return format(((Number) number).intValue(), toAppendTo, pos);
		}
		else {
			throw new IllegalArgumentException("Cannot format given Object as a Roman number");
		}
	}

	/*
	 * (non-Javadoc)
	 * @see java.text.Format#parseObject(java.lang.String, java.text.ParsePosition)
	 */
	@Override
	public Object parseObject(String source, ParsePosition pos) {
		return null;
	}

}
