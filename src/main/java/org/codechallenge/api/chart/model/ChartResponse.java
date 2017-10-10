package org.codechallenge.api.chart.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Response body sent back to clients in JSON format.
 * 
 * @author caespinosam
 *
 */
public class ChartResponse {

	private List<String> categories = new ArrayList<>();
	private List<Series> series = new ArrayList<>();

	public void addCategory(String c) {
		categories.add(c);
	}

	public void addSeries(Series s) {
		series.add(s);
	}

	public List<String> getCategories() {
		return categories;
	}

	public List<Series> getSeries() {
		return series;
	}

	public void setCategories(List<String> categories) {
		this.categories = categories;
	}

	public void setSeries(List<Series> series) {
		this.series = series;
	}

}

