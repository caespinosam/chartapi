package org.codechallenge.api.statistics.model;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Contains the current totals of a datatime.
 * It uses thread safe integers to ensure consistency. 
 * @author caespinosam
 *
 */
public class StatisticsEntry {

	/** When the request or query was processed.*/
	private LocalDateTime key;
	private AtomicInteger totalRequests = new AtomicInteger(0);
	private AtomicInteger totalQueries = new AtomicInteger(0);

	public StatisticsEntry(LocalDateTime key) {		
		this.key = key;
	}
	
	public void incremenTotalQueries() {
		totalQueries.getAndIncrement();
	}
	
	public void incremenTotalRequests() {
		totalRequests.getAndIncrement();
	}

	public LocalDateTime getKey() {
		return key;
	}

	public AtomicInteger getTotalRequests() {
		return totalRequests;
	}

	public AtomicInteger getTotalQueries() {
		return totalQueries;
	}

}
