package org.codechallenge.api.chart.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Response body sent back to clients.
 * 
 * @author caespinosam
 *
 */
public class ChartResponse {

	private List<String> categories = new ArrayList<>();
	private List<ChartSeries> series = new ArrayList<>();

	public void addCategory(String c) {
		categories.add(c);
	}

	public void addSeries(ChartSeries s) {
		series.add(s);
	}

	public List<String> getCategories() {
		return categories;
	}

	public List<ChartSeries> getSeries() {
		return series;
	}

	public void setCategories(List<String> categories) {
		this.categories = categories;
	}

	public void setSeries(List<ChartSeries> series) {
		this.series = series;
	}

}

