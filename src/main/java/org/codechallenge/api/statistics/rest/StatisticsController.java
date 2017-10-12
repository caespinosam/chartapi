package org.codechallenge.api.statistics.rest;

import javax.validation.Valid;

import org.codechallenge.api.statistics.model.ETimeUnit;
import org.codechallenge.api.statistics.model.StatisticsRequest;
import org.codechallenge.api.statistics.model.StatisticsResponse;
import org.codechallenge.api.statistics.service.IStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller to receive statistics requests.
 * 
 * @author caespinosam
 *
 */

@RestController
public class StatisticsController {

	@Autowired
	private IStatisticsService statisticsService;

	@RequestMapping(value = "/statistics", method = RequestMethod.POST)
	public StatisticsResponse chart(@Valid @RequestBody StatisticsRequest request) {
		
		StatisticsResponse response = statisticsService.getStatistics(
				ETimeUnit.valueOf(request.getTimeUnit()), 
				request.getLast(),
				request.getMavgPoints());
		return response;
	}
}
