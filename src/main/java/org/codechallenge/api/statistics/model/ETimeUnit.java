package org.codechallenge.api.statistics.model;

import java.time.format.DateTimeFormatter;

public enum ETimeUnit {
	minutes(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
	seconds(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
	
	private DateTimeFormatter formatter;

	private ETimeUnit(DateTimeFormatter formatter) {
		this.formatter = formatter;
	}

	public DateTimeFormatter getFormatter() {
		return formatter;
	}

}
