package org.codechallenge.api.statistics.service;

import org.codechallenge.api.chart.service.IChartService;
import org.codechallenge.api.statistics.model.ETimeUnit;
import org.codechallenge.api.statistics.model.StatisticsResponse;

/**
 * An implementation of {@link IChartService}. It uses a DAO object to query
 * data from a data storage in order to build the final response.
 * 
 * @author caespinosam
 *
 */

public interface IStatisticsService {

	public void registerRequest();

	public void registerQuery();

	public StatisticsResponse getStatistics(ETimeUnit timeUnit, int last, int mavgPoints);

}
