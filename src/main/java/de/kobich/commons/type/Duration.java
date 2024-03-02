package de.kobich.commons.type;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * This type represents durations that are between 00:00:00:000 and 23:59:59:999 (format is HH:mm:ss:SSS)
 * @author ckorn
 */
public class Duration implements Serializable {
	private static final long serialVersionUID = -8207158497536055661L;
	public static final int SECOND_MILLS = 1000;
	public static final int MINUTE_MILLIS = 60 * SECOND_MILLS;
	public static final int HOUR_MILLIS = 60 * MINUTE_MILLIS;
	public static final int DAY_MILLIS = 24 * HOUR_MILLIS;
	private static final Logger logger = Logger.getLogger(Duration.class);
	private long time;

	/**
	 * Constructor
	 */
	public Duration() {
		reset();
	}
	/**
	 * Constructor
	 */
	public Duration(long time) {
		this.time = time;
	}

	/**
	 * CTOR
	 * @param duration HH:mm:ss:SSS
	 * @throws IllegalArgumentException
	 */
	public Duration(String duration) throws IllegalArgumentException {
		parse(duration);
	}

	/**
	 * Constructor
	 * @param hours
	 * @param mins
	 * @param secs
	 * @param millis
	 * @throws IllegalArgumentException
	 */
	public Duration(int hours, int mins, int secs, int millis) throws IllegalArgumentException {
		this(0, hours, mins, secs, millis);
	}
	
	/**
	 * Constructor
	 * @param days
	 * @param hours
	 * @param mins
	 * @param secs
	 * @param millis
	 * @throws IllegalArgumentException
	 */
	public Duration(int days, int hours, int mins, int secs, int millis) throws IllegalArgumentException {
		this.time += millis;
		this.time += secs * SECOND_MILLS;
		this.time += mins * MINUTE_MILLIS;
		this.time += hours * HOUR_MILLIS;
		this.time += days * DAY_MILLIS;
	}
	
	/**
	 * Resets this duration to zero
	 */
	public void reset() {
		this.time = 0;
	}

	/**
	 * Returns the days part of this duration
	 * @return days part
	 */
	public int getDays() {
		return (int) (time / DAY_MILLIS);
	}

	/**
	 * Returns the hour part of this duration
	 * @return hour part
	 */
	public int getHours() {
		return (int) (time / HOUR_MILLIS) % 24;
	}

	/**
	 * Returns the minute part of this duration
	 * @return minute part
	 */
	public int getMinutes() {
		return (int) (time / MINUTE_MILLIS) % 60;
	}

	/**
	 * Returns the second part of this duration
	 * @return second part
	 */
	public int getSeconds() {
		return (int) (time / SECOND_MILLS) % 60;
	}

	/**
	 * Returns the millisecond part of this duration
	 * @return millisecond part
	 */
	public int getMillis() {
		return (int) (time % 1000);
	}
	
	/**
	 * Parses the duration
	 * @param duration
	 */
	private void parse(String duration) {
		reset();
		String[] tokens = duration.split(":");
		List<String> tokenList = Arrays.asList(tokens);
		Collections.reverse(tokenList);
		for (int i = 0; i < tokenList.size(); i++) {
			String token = tokenList.get(i);
			try {
				int time = Integer.parseInt(token);
				switch (i) {
					case 0:
						this.time += time;
						break;
					case 1:
						this.time += time * SECOND_MILLS;
						break;
					case 2: 
						this.time += time * MINUTE_MILLIS;
						break;
					case 3: 
						this.time += time * HOUR_MILLIS;
						break;
					case 4: 
						this.time += time * DAY_MILLIS;
						break;
				}
			}
			catch (NumberFormatException exc) {
				logger.warn("Parsing error for token: " + token, exc);
			}
		}
	}

	/**
	 * Returns the string representation of this duration
	 * @return duration as string
	 */
	public String getAsString() {
		return String.format("%d:%02d:%02d:%02d:%03d", getDays(), getHours(), getMinutes(), getSeconds(), getMillis());
	}

	/**
	 * Returns the numeric representation of this duration in milliseconds
	 * @return duration as milliseconds
	 */
	public long getTime() {
		return time;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return getAsString();
	}

}
