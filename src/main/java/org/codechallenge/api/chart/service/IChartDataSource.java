package org.codechallenge.api.chart.service;

import org.codechallenge.api.chart.model.ChartDimension;

/**
 * Defines all the methods to retrieve data from a datasource.
 * 
 * @author caespinosam
 *
 */
public interface IChartDataSource {

	/**
	 * Retrieves all the data related to a dimension.
	 * 
	 * @param dimensionName
	 *            the dimension to filter.
	 */

	ChartDimension getDimensionData(String dimensionName);

}
