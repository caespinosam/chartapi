package org.codechallenge.api.chart.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Part of the response body. 
 * A dimension to query. It contains multiple categories. 
 * 
 * @author caespinosam
 *
 */
public class ChartDimension {

	private String name;
	private List<ChartCategory> categories = new ArrayList<>();

	public ChartDimension(String name) {
		this.name = name;
	}

	public void addCategory(ChartCategory c) {
		categories.add(c);
	}

	public String getName() {
		return name;
	}

	public List<ChartCategory> getCategories() {
		return categories;
	}

}
