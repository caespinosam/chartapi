package org.codechallenge;

import org.codechallenge.api.chart.model.ChartResponse;
import org.codechallenge.api.statistics.model.StatisticsResponse;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

/**
 * Integration test to validate the Statistics API
 * @author caespinosam
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = WebEnvironment.DEFINED_PORT)
@DirtiesContext
public class ApiStatisticsIntegrationTest {

	private final static String URL_STATISTICS_API = "http://localhost:8080/chartapi/statistics";
	private final static String URL_CHART_API = "http://localhost:8080/chartapi/chart";
	
	@Test
	public void test_statistics_OK_Response_When_Valid_Request() throws InterruptedException {
		
		RestTemplate restTemplate = new RestTemplate();
		String requestJson = "{\"dimensions\":[\"team\"],\"measures\":[\"champions\",\"leagues\", \"revenue\"]}";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		String plainCreds = "user:user";
		headers.add("Authorization", "Basic " + new
				String(Base64.encode(plainCreds.getBytes())));
		HttpEntity<String> httpEntity = new HttpEntity<String>(requestJson, headers);				
		restTemplate.postForObject(URL_CHART_API, httpEntity, ChartResponse.class);
		
		restTemplate = new RestTemplate();
		requestJson = "{\"last\": 5, \"timeUnit\": \"minutes\" ,\"mavgPoints\": 5}";
		headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		plainCreds = "admin:admin";
		headers.add("Authorization", "Basic " + new
				String(Base64.encode(plainCreds.getBytes())));
		httpEntity = new HttpEntity<String>(requestJson, headers);				
		StatisticsResponse sr = restTemplate.postForObject(URL_STATISTICS_API, httpEntity, StatisticsResponse.class);
		Assert.assertTrue(sr.getTotalQueries() > 0);
		Assert.assertTrue(sr.getTotalRequests() > 0);
		Assert.assertEquals(5, sr.getChart().getCategories().size());
		Assert.assertEquals(3, sr.getChart().getSeries().size());	
		
		int currentRequests = sr.getTotalRequests();
		int currentQueries = sr.getTotalQueries();
		
		restTemplate = new RestTemplate();
		requestJson = "{\"dimensions\":[\"team\"],\"measures\":[\"champions\",\"leagues\", \"revenue\"]}";
		headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		plainCreds = "admin:admin";
		headers.add("Authorization", "Basic " + new
				String(Base64.encode(plainCreds.getBytes())));
		httpEntity = new HttpEntity<String>(requestJson, headers);				
		restTemplate.postForObject(URL_CHART_API, httpEntity, ChartResponse.class);
		
		Thread.sleep(1000); // incrementing counters is asynchronous
		restTemplate = new RestTemplate();
		requestJson = "{\"last\": 5, \"timeUnit\": \"minutes\" ,\"mavgPoints\": 5}";
		headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		plainCreds = "admin:admin";
		headers.add("Authorization", "Basic " + new
				String(Base64.encode(plainCreds.getBytes())));
		httpEntity = new HttpEntity<String>(requestJson, headers);				
		sr = restTemplate.postForObject(URL_STATISTICS_API, httpEntity, StatisticsResponse.class);
		Assert.assertEquals(currentQueries + 3 , sr.getTotalQueries());
		Assert.assertEquals(currentRequests + 1, sr.getTotalRequests());
		
		
	}
	
	@Test(expected = HttpClientErrorException.class)
	public void test_chart_Error_When_Invalid_Credentials() {
		
		RestTemplate restTemplate = new RestTemplate();
		String requestJson = "{\"last\": 5, \"timeUnit\": \"minutes\" ,\"mavgPoints\": 5}";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		String plainCreds = "admin:admin2";
		headers.add("Authorization", "Basic " + new
				String(Base64.encode(plainCreds.getBytes())));
		HttpEntity<String> httpEntity = new HttpEntity<String>(requestJson, headers);				
		restTemplate.postForObject(URL_STATISTICS_API, httpEntity, ChartResponse.class);
			
	}
	
	@Test(expected = HttpClientErrorException.class)
	public void test_chart_Error_When_Invalid_Role() {
		
		RestTemplate restTemplate = new RestTemplate();
		String requestJson = "{\"last\": 5, \"timeUnit\": \"minutes\" ,\"mavgPoints\": 5}";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		String plainCreds = "user:user";
		headers.add("Authorization", "Basic " + new
				String(Base64.encode(plainCreds.getBytes())));
		HttpEntity<String> httpEntity = new HttpEntity<String>(requestJson, headers);				
		restTemplate.postForObject(URL_STATISTICS_API, httpEntity, ChartResponse.class);
			
	}
	
	@Test(expected = HttpClientErrorException.class)
	public void test_chart_Error_Response_When_Empty_Last() {
		
		RestTemplate restTemplate = new RestTemplate();
		String requestJson = "{\"last\": , \"timeUnit\": \"minutes\" ,\"mavgPoints\": 5}";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> httpEntity = new HttpEntity<String>(requestJson, headers);				
		restTemplate.exchange(URL_CHART_API, HttpMethod.POST, httpEntity, ChartResponse.class);		
		
	}
	
	@Test(expected = HttpClientErrorException.class)
	public void test_chart_Error_Response_When_Empty_Minutes() {
		
		RestTemplate restTemplate = new RestTemplate();
		String requestJson = "{\"last\": 5, \"timeUnit\": \"\" ,\"mavgPoints\": 5}";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> httpEntity = new HttpEntity<String>(requestJson, headers);				
		restTemplate.exchange(URL_CHART_API, HttpMethod.POST, httpEntity, ChartResponse.class);		
		
	}
	
	@Test(expected = HttpClientErrorException.class)
	public void test_chart_Error_Response_When_Invalid_Minutes() {
		
		RestTemplate restTemplate = new RestTemplate();
		String requestJson = "{\"last\": 5, \"timeUnit\": \"hour\" ,\"mavgPoints\": 5}";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> httpEntity = new HttpEntity<String>(requestJson, headers);				
		restTemplate.exchange(URL_CHART_API, HttpMethod.POST, httpEntity, ChartResponse.class);		
		
	}
	
	@Test(expected = HttpClientErrorException.class)
	public void test_chart_Error_Response_When_Empty_mavgPoints() {
		
		RestTemplate restTemplate = new RestTemplate();
		String requestJson = "{\"last\": 5, \"timeUnit\": \"minutes\" ,\"mavgPoints\": }";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> httpEntity = new HttpEntity<String>(requestJson, headers);				
		restTemplate.exchange(URL_CHART_API, HttpMethod.POST, httpEntity, ChartResponse.class);		
		
	}


 }
