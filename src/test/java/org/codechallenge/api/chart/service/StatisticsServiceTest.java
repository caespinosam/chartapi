package org.codechallenge.api.chart.service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.codechallenge.api.statistics.model.ETimeUnit;
import org.codechallenge.api.statistics.model.StatisticsResponse;
import org.codechallenge.api.statistics.service.IStatisticsService;
import org.codechallenge.api.statistics.service.StatisticsService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Unit test to validate {@link StatisticsService}.
 * 
 * @author caespinosam
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext
public class StatisticsServiceTest {

	@Autowired
	private IStatisticsService service;

	@Test
	public void test_Increment_Requests_Counter_When_Register_Request() throws InterruptedException {
		StatisticsResponse r = service.getStatistics(ETimeUnit.minutes, 5, 2);
		int currentRequests = r.getTotalRequests();
		service.registerRequest();
		Thread.sleep(1000);
		Assert.assertEquals(currentRequests + 1, service.getStatistics(ETimeUnit.minutes, 5, 2).getTotalRequests());
		service.registerRequest();
		service.registerRequest();
		Thread.sleep(1000);
		Assert.assertEquals(currentRequests + 3, service.getStatistics(ETimeUnit.minutes, 5, 2).getTotalRequests());
	}

	@Test
	public void test_Increment_Requests_Counter_When_Register_Request_Concurrently() throws InterruptedException {
		StatisticsResponse r = service.getStatistics(ETimeUnit.minutes, 5, 2);
		int currentRequests = r.getTotalRequests();
		
		int threads = 4;
		int invocations = 50;
		ExecutorService es = Executors.newFixedThreadPool(threads);
		for (int i = 0; i< threads; i++) {
			es.submit(new Runnable() {

				@Override
				public void run() {
					for (int i = 0; i < invocations; i++) {
						service.registerRequest();
					}

				}
			});
		}
		
		
		es.shutdown();
		es.awaitTermination(1, TimeUnit.MINUTES);		
		Thread.sleep(5000);
		r = service.getStatistics(ETimeUnit.minutes, 5, 2);
		Assert.assertEquals(currentRequests + (threads * invocations), r.getTotalRequests());
	}
	
	@Test
	public void test_Increment_Queries_Counter_When_Register_Query() throws InterruptedException {
		StatisticsResponse r = service.getStatistics(ETimeUnit.minutes, 5, 2);
		int currentQueries = r.getTotalQueries();
		service.registerQuery();;
		Thread.sleep(1000);
		Assert.assertEquals(currentQueries + 1, service.getStatistics(ETimeUnit.minutes, 5, 2).getTotalQueries());
		service.registerQuery();
		service.registerQuery();
		Thread.sleep(1000);
		Assert.assertEquals(currentQueries + 3, service.getStatistics(ETimeUnit.minutes, 5, 2).getTotalQueries());
	}
	
	
	@Test
	public void test_Increment_Queries_Counter_When_Register_Query_Concurrently() throws InterruptedException {
		StatisticsResponse r = service.getStatistics(ETimeUnit.minutes, 5, 2);
		int currentQueries = r.getTotalQueries();
		
		int threads = 4;
		int invocations = 50;
		ExecutorService es = Executors.newFixedThreadPool(threads);
		for (int i = 0; i< threads; i++) {
			es.submit(new Runnable() {

				@Override
				public void run() {
					for (int i = 0; i < invocations; i++) {
						service.registerQuery();;
					}

				}
			});
		}
		
		
		es.shutdown();
		es.awaitTermination(1, TimeUnit.MINUTES);		
		Thread.sleep(5000);
		r = service.getStatistics(ETimeUnit.minutes, 5, 2);
		Assert.assertEquals(currentQueries + (threads * invocations), r.getTotalQueries());
	}

}
