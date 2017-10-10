package org.codechallenge.api.chart.service;

import java.util.List;

import org.codechallenge.api.chart.model.ChartResponse;

/**
 * Defines all the methods to process dimension queries .
 * 
 * @author caespinosam
 *
 */
public interface IChartService {
	
	/**
	 * Calculates all the categories and series related to a dimension.   
	 * 
	 * @param dimension the dimension to query
	 * @param measures the measures to filter
	 * @return categories and series of the given dimension
	 */
	ChartResponse generateChart(String dimension, List<String> measures);
}
