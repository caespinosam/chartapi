package org.codechallenge.api.statistics.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.codechallenge.api.statistics.model.ETimeUnit;
import org.codechallenge.api.statistics.model.StatisticsEntry;
import org.codechallenge.api.statistics.model.Total;

public class StatisticsHashMapStorage  implements IStatisticsStorage
{

	private Map<LocalDateTime, StatisticsEntry> data = new ConcurrentHashMap<>();

	public void incrementRequests(LocalDateTime key) {
		putIfAbsent(key);
		data.get(key).incremenTotalRequests();
	}

	public void incrementQueries(LocalDateTime key) {
		putIfAbsent(key);
		data.get(key).incremenTotalQueries();;
	}

	public Map<String, Total> getStatistics(ETimeUnit timeUnit, int last, int mavgPoints) {

		final LocalDateTime fromDate;
		if (timeUnit == ETimeUnit.minutes) {
			fromDate = LocalDateTime.now().minusMinutes(last);
		} else {
			fromDate = LocalDateTime.now().minusSeconds(last);
		}

		Map<String, List<Map.Entry<LocalDateTime, StatisticsEntry>>> filteredData = data.entrySet().stream()
				.filter(x -> x.getKey().isEqual(fromDate) || x.getKey().isAfter(fromDate))
				.collect(Collectors.groupingBy(
						x -> ((Map.Entry<LocalDateTime, StatisticsEntry>) x).getKey().format(timeUnit.getFormatter())));

		Map<String, Total> result = new HashMap<>();
		for (String key : filteredData.keySet()) {
			List<Map.Entry<LocalDateTime, StatisticsEntry>> value = filteredData.get(key);
			int totalRequests = 0;
			int totalQueries = 0;
			for (Map.Entry<LocalDateTime, StatisticsEntry> e : value) {
				totalRequests = totalRequests + e.getValue().getTotalRequests().get();
				totalQueries = totalQueries + e.getValue().getTotalQueries().get();
			}
			result.put(key, new Total(totalRequests, totalQueries));
		}

		return result;
	}

	private void putIfAbsent(LocalDateTime key) {
		StatisticsEntry current = data.get(key);
		if (current == null) {
			data.putIfAbsent(key, new StatisticsEntry(key));
		}
	}

}
