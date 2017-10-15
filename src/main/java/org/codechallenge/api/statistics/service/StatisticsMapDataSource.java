package org.codechallenge.api.statistics.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.codechallenge.api.statistics.model.ETimeUnit;
import org.codechallenge.api.statistics.model.StatisticsEntry;
import org.codechallenge.api.statistics.model.StatisticsTotal;
import org.springframework.stereotype.Repository;

/**
 * An implementation of {@link IStatisticsDataSource}. It uses an in-memory data structure (map)
 * to store data.
 * 
 * @author caespinosam
 *
 */
@Repository
public class StatisticsMapDataSource implements IStatisticsDataSource {
	
	/** Intervals*/
	private static final int UNITS_GAP = 1;

	private Map<LocalDateTime, StatisticsEntry> data = new ConcurrentHashMap<>();

	/*
	 * (non-Javadoc)
	 * @see org.codechallenge.api.statistics.service.IStatisticsDataSource#incrementRequests(java.time.LocalDateTime)
	 */
	public void incrementRequests(LocalDateTime key) {
		putIfAbsent(key);
		data.get(key).incremenTotalRequests();
	}

	/*
	 * (non-Javadoc)
	 * @see org.codechallenge.api.statistics.service.IStatisticsDataSource#incrementQueries(java.time.LocalDateTime)
	 */
	public void incrementQueries(LocalDateTime key) {
		putIfAbsent(key);
		data.get(key).incremenTotalQueries();
		;
	}

	/*
	 * (non-Javadoc)
	 * @see org.codechallenge.api.statistics.service.IStatisticsDataSource#getStatistics(org.codechallenge.api.statistics.model.ETimeUnit, int, int)
	 */
	public Map<String, StatisticsTotal> getStatistics(ETimeUnit timeUnit, int last, int mavgPoints) {

		last = last - 1;// so that now date is included
		final LocalDateTime fromDate = LocalDateTime.now().minus(last, timeUnit.getTemporalUnit());			
		List<String> datesToProcess = calculateDatesToProcess(timeUnit, last, fromDate);

		// select only the data in the range, then group the data by using a
		// string representation of the key
		Map<String, List<Map.Entry<LocalDateTime, StatisticsEntry>>> filteredData = data.entrySet().stream()
				.filter(x -> x.getKey().isEqual(fromDate) || x.getKey().isAfter(fromDate))
				.collect(Collectors.groupingBy(
						x -> ((Map.Entry<LocalDateTime, StatisticsEntry>) x).getKey().format(timeUnit.getFormatter())));

		// calculate all the totals of each entry
		Map<String, StatisticsTotal> result = new HashMap<>();
		for (String key : filteredData.keySet()) {
			List<Map.Entry<LocalDateTime, StatisticsEntry>> value = filteredData.get(key);
			int totalRequests = 0;
			int totalQueries = 0;
			for (Map.Entry<LocalDateTime, StatisticsEntry> e : value) {
				totalRequests = totalRequests + e.getValue().getTotalRequests().get();
				totalQueries = totalQueries + e.getValue().getTotalQueries().get();
			}
			result.put(key, new StatisticsTotal(totalRequests, totalQueries));
		}

		// set defaults values to those datetimes without statistics
		for (String dateToSHow : datesToProcess) {
			if (!result.containsKey(dateToSHow)) {
				result.put(dateToSHow, new StatisticsTotal(0, 0));
			}
		}

		return result;
	}

	/**
	 * A thread safe method to store a new entry .
	 * @param key
	 */
	private void putIfAbsent(LocalDateTime key) {
		StatisticsEntry current = data.get(key);
		if (current == null) {
			data.putIfAbsent(key, new StatisticsEntry(key));
		}
	}
	
	/**
	 * Generates the string representation of all the dates to query.
	 * 
	 * @param timeUnit
	 * @param last
	 * @param fromDate
	 * @return
	 */
	private List<String> calculateDatesToProcess(ETimeUnit timeUnit, int last, LocalDateTime fromDate){
		LocalDateTime now = LocalDateTime.now();		
		List<String> datesToShow = new ArrayList<String>();	
		LocalDateTime tmp = fromDate;
		while (!tmp.isAfter(now)) {
			datesToShow.add(tmp.format(timeUnit.getFormatter()));
			tmp = tmp.plus(UNITS_GAP, timeUnit.getTemporalUnit());
		}	
		return datesToShow;
	}

}
