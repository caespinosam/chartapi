package org.codechallenge.api.chart.service;

import org.codechallenge.api.chart.model.Dimension;

/**
 * Defines all the methods to retrieve data from a database.
 * 
 * @author caespinosam
 *
 */
public interface IChartDAO {

	/**
	 * Retrieves all the data related to a dimension.
	 * 
	 * @param dimensionName
	 *            the dimension to filter.
	 */

	Dimension getDimensionData(String dimensionName);

}
