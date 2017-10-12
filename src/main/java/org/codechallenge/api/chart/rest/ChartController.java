package org.codechallenge.api.chart.rest;

import javax.validation.Valid;

import org.codechallenge.api.chart.model.ChartRequest;
import org.codechallenge.api.chart.model.ChartResponse;
import org.codechallenge.api.chart.service.IChartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller to receive query dimension requests.
 * 
 * @author caespinosam
 *
 */

@RestController
public class ChartController {

	@Autowired
	private IChartService chartService;

	@RequestMapping(value = "/chart", method = RequestMethod.POST)
	public ChartResponse chart(@Valid @RequestBody ChartRequest request) {
		ChartResponse response = chartService.queryMeasures(request.getDimensions().get(0), request.getMeasures());
		return response;
	}
}
