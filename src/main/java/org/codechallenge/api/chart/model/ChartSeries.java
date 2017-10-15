package org.codechallenge.api.chart.model;

import java.util.ArrayList;
import java.util.List;


/**
 * Part of the response body. 
* Values of a measure. 
* @author caespinosam
*
*/
public class ChartSeries {

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
