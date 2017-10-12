package org.codechallenge.api.statistics.model;

import java.util.ArrayList;
import java.util.List;

import org.codechallenge.api.chart.model.Series;

public class Chart {

	private List<String> categories = new ArrayList<>();
	private List<Series> series = new ArrayList<>();

	public void addSeries(Series s) {
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

	public List<Series> getSeries() {
		return series;
	}

	public void setSeries(List<Series> series) {
		this.series = series;
	}

}
