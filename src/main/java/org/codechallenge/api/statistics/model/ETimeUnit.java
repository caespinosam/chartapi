package org.codechallenge.api.statistics.model;

import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;

/**
 * Time units to use.
 * 
 * @author caespinosam
 *
 */
public enum ETimeUnit {
	minutes(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"), ChronoUnit.MINUTES),
	seconds(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"), ChronoUnit.SECONDS);

	/** To turn dates into strings.*/
	private DateTimeFormatter formatter;
	/** The Java8 TemporalUnit.*/
	private TemporalUnit temporalUnit;

	private ETimeUnit(DateTimeFormatter formatter, TemporalUnit temporalUnit) {
		this.formatter = formatter;
		this.temporalUnit = temporalUnit;
	}

	public DateTimeFormatter getFormatter() {
		return formatter;
	}

	public TemporalUnit getTemporalUnit() {
		return temporalUnit;
	}

}
