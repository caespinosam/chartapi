package org.codechallenge.api.statistics.model;

public class Total {

	private int totalRequests = 0;
	private int totalQueries = 0;

	public Total(int totalRequests, int totalQueries) {		
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
