package org.codechallenge.api.statistics.service;

import java.time.LocalDateTime;
import java.util.Map;

import org.codechallenge.api.statistics.model.ETimeUnit;
import org.codechallenge.api.statistics.model.Total;

/**
 * Defines all the methods to create a statistics storage.
 * 
 * @author caespinosam
 *
 */
public interface IStatisticsStorage {

	/**
	 * Increments
	 * 
	 * @param key
	 */
	Map<String, Total> getStatistics(ETimeUnit timeUnit, int last, int mavgPoints);

	void incrementRequests(LocalDateTime key);

	void incrementQueries(LocalDateTime key);

}
