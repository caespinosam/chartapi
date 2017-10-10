package org.codechallenge.api.chart.model;

import java.util.ArrayList;
import java.util.List;

/**
 * A dimension to query. It contains multiple categories. 
 * 
 * @author caespinosam
 *
 */
public class Dimension {

	private String name;
	private List<Category> categories = new ArrayList<>();

	public Dimension(String name) {
		this.name = name;
	}

	public void addCategory(Category c) {
		categories.add(c);
	}

	public String getName() {
		return name;
	}

	public List<Category> getCategories() {
		return categories;
	}

}
