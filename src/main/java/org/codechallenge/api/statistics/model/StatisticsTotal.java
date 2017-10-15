package org.codechallenge.api.statistics.model;

/**
 * Encapsulates the totals of an entry.
 * 
 * @author caespinosam
 *
 */
public class StatisticsTotal {

	private int totalRequests = 0;
	private int totalQueries = 0;

	public StatisticsTotal(int totalRequests, int totalQueries) {		
		this.totalRequests = totalRequests;
		this.totalQueries = totalQueries;
	}

	public int getTotalRequests() {
		return totalRequests;
	}

	public int getTotalQueries() {
		return totalQueries;
	}

}
