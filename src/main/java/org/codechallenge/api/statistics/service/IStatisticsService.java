package org.codechallenge.api.statistics.service;

import org.codechallenge.api.statistics.model.ETimeUnit;
import org.codechallenge.api.statistics.model.StatisticsResponse;

/**
 * Defines all the methods to process statistics queries .
 * 
 * @author caespinosam
 *
 */

public interface IStatisticsService {

	/**
	 * Increments the total request counter by 1 .	 
	 */
	public void registerRequest();

	/**
	 * Increments the total queries counter by 1 . 	
	 */
	public void registerQuery();

	/**
	 * Gets the statistics for a period.
	 * @param timeUnit  seconds or minutes
	 * @param last number of time units to query
	 * @param mavgPoints simple moving average points
	 * @return 
	 */
	public StatisticsResponse getStatistics(ETimeUnit timeUnit, int last, int mavgPoints);

}
