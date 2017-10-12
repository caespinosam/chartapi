package org.codechallenge.api.chart.service;

import java.util.List;

import org.codechallenge.api.chart.model.Category;
import org.codechallenge.api.chart.model.ChartResponse;
import org.codechallenge.api.chart.model.Dimension;
import org.codechallenge.api.chart.model.Series;
import org.codechallenge.api.statistics.service.IStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * An implementation of {@link IChartService}. It uses a DAO object to query
 * data from a data storage in order to build the final response.
 * 
 * @author caespinosam
 *
 */
@Service
public class ChartService implements IChartService {

	@Autowired
	private IChartDAO chartDAO;
	@Autowired
	private IStatisticsService statisticsService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.codechallenge.api.chart.service.IChartService#generateChart(java.lang
	 * .String, java.util.List)
	 */
	//@Cacheable(value = "dimensions")
	public ChartResponse queryMeasures(String dimension, List<String> measures) {
		
		statisticsService.registerRequest();
		
		Dimension dim = chartDAO.getDimensionData(dimension);
		ChartResponse response = new ChartResponse();
		if (dim != null) {

			// instantiate the series to send back
			for (String measure : measures) {
				Series sr = new Series();
				sr.setMeasureName(measure);
				response.addSeries(sr);			
				statisticsService.registerQuery();
			}

			// populate categories and series
			for (Category category : dim.getCategories()) {				
				response.addCategory(category.getName());
				for (Series series : response.getSeries()) {
					series.addValue(category.getMeasureValue(series.getMeasureName()));
				}
			}
		}
		return response;

	}

}
