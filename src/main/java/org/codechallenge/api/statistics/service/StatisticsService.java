package org.codechallenge.api.statistics.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.codechallenge.api.chart.model.ChartSeries;
import org.codechallenge.api.statistics.model.ETimeUnit;
import org.codechallenge.api.statistics.model.StatisticsResponse;
import org.codechallenge.api.statistics.model.StatisticsTotal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * An implementation of {@link IStatisticsService}. It uses a manager to query
 * data from a datasource to build the final response.
 * 
 * Some method are asynchronous not to affect the performance of the calling methods.
 * 
 * @author caespinosam
 *
 */
@Service
public class StatisticsService implements IStatisticsService {

	@Autowired
	private IStatisticsDataSource statistics;

	/*
	 * (non-Javadoc)
	 * @see org.codechallenge.api.statistics.service.IStatisticsService#registerRequest()
	 */
	@Async
	public void registerRequest() {				
		statistics.incrementRequests(getNowDate());			
	}

	/*
	 * (non-Javadoc)
	 * @see org.codechallenge.api.statistics.service.IStatisticsService#registerQuery()
	 */
	@Async
	public void registerQuery() {		
		statistics.incrementQueries(getNowDate());			
	}

	/*
	 * (non-Javadoc)
	 * @see org.codechallenge.api.statistics.service.IStatisticsService#getStatistics(org.codechallenge.api.statistics.model.ETimeUnit, int, int)
	 */
	public StatisticsResponse getStatistics(ETimeUnit timeUnit, int last, int mavgPoints) {
		StatisticsResponse response = new StatisticsResponse();
		int totalRequests = 0;
		int totalQueries = 0;
		Map<String, StatisticsTotal> filtereData = statistics.getStatistics(timeUnit, last, mavgPoints);
		
		// populates the body response expected by clients.
		ChartSeries seriesRequests = new ChartSeries();
		seriesRequests.setName("requests");
		ChartSeries seriesQueries = new ChartSeries();
		seriesQueries.setName("queries");
		ChartSeries seriesAverage = new ChartSeries();
		seriesAverage.setName("mavg");		
		response.getChart().addSeries(seriesRequests);
		response.getChart().addSeries(seriesQueries);
		response.getChart().addSeries(seriesAverage);
		
		List<String> sortedKeys = filtereData.keySet().stream().sorted().collect(Collectors.toList());		
		for (String key : sortedKeys) {
			StatisticsTotal entryTotal = filtereData.get(key);
			totalRequests = totalRequests + entryTotal.getTotalRequests();
			totalQueries = totalQueries + entryTotal.getTotalQueries();
			response.getChart().addCategory(key);
			seriesRequests.addValue((double)entryTotal.getTotalRequests());
			seriesQueries.addValue((double)entryTotal.getTotalQueries());
		}
		seriesAverage.setData(calculateSimpleMovingAverage(seriesQueries.getData(), mavgPoints));
		response.setTotalQueries(totalQueries);
		response.setTotalRequests(totalRequests);
		
		return response;
	}

	private LocalDateTime getNowDate() {
		return LocalDateTime.now().withNano(0);
	}
	
	private List<Double> calculateSimpleMovingAverage(List<Double> data, int mavgPoints) {
		List<Double> result = new ArrayList<Double>();	
		List<Double> tmp = new ArrayList<>(data);
		if (!data.isEmpty() && data.size() > mavgPoints) {
			while (tmp.size() >= mavgPoints) {				
				List<Double> subset = tmp.subList(0, mavgPoints);
				double mean = 0;
				for (int i = 0; i < subset.size(); i++) {
					mean = mean + subset.get(i);
				}
				mean = mean / mavgPoints;
				result.add(mean);
				tmp.remove(0);
			}
		}
		
		return result;
		
	}

}
