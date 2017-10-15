package org.codechallenge.api.statistics.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * Part of the response body.
 *  
 * @author caespinosam
 *
 */
public class StatisticsSeries {

	private String name;
	private List<Double> data = new ArrayList<>();

	public void addValue(Double value) {
		data.add(value);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Double> getData() {
		return data;
	}

	public void setData(List<Double> data) {
		this.data = data;
	}
	
}
