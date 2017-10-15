package org.codechallenge.api.statistics.model;

/**
 * Response body sent back to clients. It contains totals and  counters.
 * 
 * @author caespinosam
 *
 */
public class StatisticsResponse {

	private int totalRequests;
	private int totalQueries;
	private StatisticsChart chart = new StatisticsChart();

	public int getTotalRequests() {
		return totalRequests;
	}

	public void setTotalRequests(int totalRequests) {
		this.totalRequests = totalRequests;
	}

	public int getTotalQueries() {
		return totalQueries;
	}

	public void setTotalQueries(int totalQueries) {
		this.totalQueries = totalQueries;
	}

	public StatisticsChart getChart() {
		return chart;
	}

	public void setChart(StatisticsChart chart) {
		this.chart = chart;
	}

}
