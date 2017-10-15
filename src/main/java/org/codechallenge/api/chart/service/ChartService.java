package org.codechallenge.api.chart.service;

import java.util.List;

import org.codechallenge.api.chart.model.ChartCategory;
import org.codechallenge.api.chart.model.ChartResponse;
import org.codechallenge.api.chart.model.ChartDimension;
import org.codechallenge.api.chart.model.ChartSeries;
import org.codechallenge.api.statistics.service.IStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.cache.ehcache.EhCacheCache;
import org.springframework.stereotype.Service;

/**
 * An implementation of {@link IChartService}. It uses a manager to query
 * data from a datasource to build the final response.
 *
 * 
 * @author caespinosam
 *
 */
@Service
public class ChartService implements IChartService {

	@Autowired
	private IChartDataSource chartDS;
	@Autowired
	private IStatisticsService statisticsService;
	// To store and reuse previous results
	@Value("#{cacheManager.getCache('charts')}")
	private EhCacheCache cache;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.codechallenge.api.chart.service.IChartService#generateChart(java.lang
	 * .String, java.util.List)
	 */
	public ChartResponse queryMeasures(String dimension, List<String> measures) {

		incrementStatisticsCounters(measures);

		// verify if the query was already calculated so that the previous
		// result can be reused
		String cacheKey = dimension + "-" + measures.hashCode();
		ValueWrapper cacheValueWrapper = cache.get(cacheKey);
		if (cacheValueWrapper != null) {
			return (ChartResponse) cacheValueWrapper.get();
		} else {
			ChartResponse response = new ChartResponse();
			ChartDimension dim = chartDS.getDimensionData(dimension);
			if (dim != null) {
				for (String measure : measures) {
					ChartSeries sr = new ChartSeries();
					sr.setName(measure);
					response.addSeries(sr);					
				}
				// populate categories and series
				for (ChartCategory category : dim.getCategories()) {
					response.addCategory(category.getName());
					for (ChartSeries series : response.getSeries()) {
						series.addValue(category.getMeasureValue(series.getName()));
					}
				}
			}
			cache.put(cacheKey, response);			
			return response;
		}

	}

	/**
	 * Increments the counters asynchronously not to affect the latency. 
	 * @param measures
	 */
	private void incrementStatisticsCounters(List<String> measures) {

		statisticsService.registerRequest();
		for (String measure : measures) {
			statisticsService.registerQuery();
		}

	}

}
