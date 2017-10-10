package org.codechallenge.api.chart.model;

import java.util.ArrayList;
import java.util.List;


/**
* Values of a measure. 
* @author caespinosam
*
*/
public class Series {

	private String measureName;
	private List<Double> data = new ArrayList<>();

	public void addValue(Double value) {
		data.add(value);
	}

	public String getMeasureName() {
		return measureName;
	}

	public void setMeasureName(String name) {
		this.measureName = name;
	}

	public List<Double> getData() {
		return data;
	}

	public void setData(List<Double> data) {
		this.data = data;
	}
	
	

}
