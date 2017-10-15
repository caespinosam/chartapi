package org.codechallenge.api.statistics.model;

import java.util.ArrayList;
import java.util.List;

import org.codechallenge.api.chart.model.ChartSeries;

/**
 * Part of the response body. 
 * Contains the calculated statistics for each datetime.
 * @author caespinosam
 *
 */
public class StatisticsChart {

	private List<String> categories = new ArrayList<>();
	private List<ChartSeries> series = new ArrayList<>();

	public void addSeries(ChartSeries s) {
		series.add(s);
	}

	public void addCategory(String c) {
		categories.add(c);
	}

	public List<String> getCategories() {
		return categories;
	}

	public void setCategories(List<String> categories) {
		this.categories = categories;
	}

	public List<ChartSeries> getSeries() {
		return series;
	}

	public void setSeries(List<ChartSeries> series) {
		this.series = series;
	}

}
