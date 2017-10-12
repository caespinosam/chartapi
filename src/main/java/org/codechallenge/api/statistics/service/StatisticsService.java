package org.codechallenge.api.statistics.service;

import java.time.LocalDateTime;
import java.util.Map;

import org.codechallenge.api.chart.model.Series;
import org.codechallenge.api.chart.service.IChartService;
import org.codechallenge.api.statistics.model.ETimeUnit;
import org.codechallenge.api.statistics.model.StatisticsResponse;
import org.codechallenge.api.statistics.model.Total;
import org.springframework.stereotype.Service;

/**
 * An implementation of {@link IChartService}. It uses a DAO object to query
 * data from a data storage in order to build the final response.
 * 
 * @author caespinosam
 *
 */
@Service
public class StatisticsService implements IStatisticsService {

	private IStatisticsStorage statistics = new StatisticsHashMapStorage();

	public void registerRequest() {
		statistics.incrementRequests(getNowDate());
	}

	public void registerQuery() {
		statistics.incrementQueries(getNowDate());
	}

	public StatisticsResponse getStatistics(ETimeUnit timeUnit, int last, int mavgPoints) {
		StatisticsResponse response = new StatisticsResponse();
		int totalRequests = 0;
		int totalQueries = 0;
		Map<String, Total> filtereData = statistics.getStatistics(timeUnit, last, mavgPoints);
		
		Series seriesRequests = new Series();
		seriesRequests.setMeasureName("requests");
		Series seriesQueries = new Series();
		seriesQueries.setMeasureName("queries");
		Series seriesAverage = new Series();
		seriesAverage.setMeasureName("mavg");		
		response.getChart().addSeries(seriesRequests);
		response.getChart().addSeries(seriesQueries);
		response.getChart().addSeries(seriesAverage);
		
		for (String entry : filtereData.keySet()) {
			Total entryTotal = filtereData.get(entry);
			totalRequests = totalRequests + entryTotal.getTotalRequests();
			totalQueries = totalQueries + entryTotal.getTotalQueries();
			response.getChart().addCategory(entry);
			seriesRequests.addValue((double)entryTotal.getTotalRequests());
			seriesQueries.addValue((double)entryTotal.getTotalQueries());
		}
		response.setTotalQueries(totalQueries);
		response.setTotalRequests(totalRequests);
		
		return response;
	}

	private LocalDateTime getNowDate() {
		return LocalDateTime.now().withNano(0);
	}

}
