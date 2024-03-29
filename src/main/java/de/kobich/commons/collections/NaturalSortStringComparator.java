package de.kobich.commons.collections;

/*
 * The Alphanum Algorithm is an improved sorting algorithm for strings
 * containing numbers.  Instead of sorting numbers in ASCII order like
 * a standard sort, this algorithm sorts numbers in numeric order.
 *
 * The Alphanum Algorithm is discussed at http://www.DaveKoelle.com
 *
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 *
 */

import java.util.Comparator;

/**
 * This is an updated version with enhancements made by Daniel Migowski, Andre Bogus, and David Koelle
 */
public class NaturalSortStringComparator implements Comparator<String> {
	public static NaturalSortStringComparator INSTANCE = new NaturalSortStringComparator(); 
	
	@Override
	public int compare(String s1, String s2) {
		int thisMarker = 0;
		int thatMarker = 0;
		int s1Length = s1.length();
		int s2Length = s2.length();

		while (thisMarker < s1Length && thatMarker < s2Length) {
			String thisChunk = getChunk(s1, s1Length, thisMarker);
			thisMarker += thisChunk.length();

			String thatChunk = getChunk(s2, s2Length, thatMarker);
			thatMarker += thatChunk.length();

			// If both chunks contain numeric characters, sort them numerically
			int result = 0;
			if (Character.isDigit(thisChunk.charAt(0)) && Character.isDigit(thatChunk.charAt(0))) {
				Integer thisInt = Integer.parseInt(thisChunk);
				Integer thatInt = Integer.parseInt(thatChunk);
				result = thisInt.compareTo(thatInt);
			}
			else {
				result = thisChunk.compareTo(thatChunk);
			}

			if (result != 0) {
				return result;
			}
		}

		return s1Length - s2Length;
	}

	/**
	 * Length of string is passed in for improved efficiency (only need to calculate it once)
	 * @param s
	 * @param slength
	 * @param marker
	 * @return
	 */
	private final String getChunk(String s, int slength, int marker) {
		StringBuilder chunk = new StringBuilder();
		char c = s.charAt(marker);
		chunk.append(c);
		marker++;
		if (Character.isDigit(c)) {
			while (marker < slength) {
				c = s.charAt(marker);
				if (!Character.isDigit(c)) {
					break;
				}
				chunk.append(c);
				marker++;
			}
		}
		else {
			while (marker < slength) {
				c = s.charAt(marker);
				if (Character.isDigit(c)) {
					break;
				}
				chunk.append(c);
				marker++;
			}
		}
		return chunk.toString();
	}
}
