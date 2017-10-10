package org.codechallenge.api.chart.model;

import java.util.HashMap;
import java.util.Map;

/**
 * A category which consists of many measures and their values (e.g Real Madrid: leagues=20, champions=5) 
 * 
 * @author caespinosam
 *
 */
public class Category {

	private String name;
	private Map<String, Double> measures = new HashMap<>();

	public Category(String name) {
		this.name = name;
	}

	public void addMeasure(String measure, Double value) {
		measures.put(measure, value);
	}

	public String getName() {
		return name;
	}

	public Double getMeasureValue(String measure) {
		return measures.get(measure);
	}
}
