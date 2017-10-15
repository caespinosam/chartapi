package org.codechallenge.api.statistics.service;

import java.time.LocalDateTime;
import java.util.Map;

import org.codechallenge.api.statistics.model.ETimeUnit;
import org.codechallenge.api.statistics.model.StatisticsTotal;

/**
 * Defines all the methods to retrieve data from a datasource.
 * 
 * @author caespinosam
 *
 */
public interface IStatisticsDataSource {

	/**
	 * Gets the statistics for a period .
	 * @param timeUnit  seconds or minutes
	 * @param last number of time units to query
	 * @param mavgPoints simple moving average points
	 * @return the statistics grouped by time.
	 */
	Map<String, StatisticsTotal> getStatistics(ETimeUnit timeUnit, int last, int mavgPoints);

	/**
	 * Increments the total request counter by 1 for the given date. 
	 * @param key the date when the request was processed.
	 */
	void incrementRequests(LocalDateTime key);

	/**
	 * Increments the total queries counter by 1 for the given date. 
	 * @param key the date when the query was processed.
	 */
	void incrementQueries(LocalDateTime key);

}
