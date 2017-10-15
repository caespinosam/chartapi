package org.codechallenge.api.chart.model;

import java.util.List;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * Request body sent by clients. 
 * 
 * @author caespinosam
 *
 */
public class ChartRequest {
	
	@NotEmpty
	@Size(min = 1, max=1)
	private List<String> dimensions;
	@NotEmpty
	@Size(min = 1, max=3)
	private List<String> measures;

	public List<String> getDimensions() {
		return dimensions;
	}

	public void setDimensions(List<String> dimensions) {
		this.dimensions = dimensions;
	}

	public List<String> getMeasures() {
		return measures;
	}

	public void setMeasures(List<String> measures) {
		this.measures = measures;
	}

}
