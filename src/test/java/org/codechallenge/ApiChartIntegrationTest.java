package org.codechallenge;

import org.codechallenge.api.chart.model.ChartResponse;
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
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

/**
 * Integration test to validate the Chart API
 * @author caespinosam
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = WebEnvironment.DEFINED_PORT)
public class ApiChartIntegrationTest {

	private final static String URL_CHART_API = "http://localhost:8080/chartapi/chart";
	
	@Test
	public void test_chart_OK_Response_When_Valid_Request() {
		
		RestTemplate restTemplate = new RestTemplate();
		String requestJson = "{\"dimensions\":[\"team\"],\"measures\":[\"champions\",\"leagues\", \"revenue\"]}";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		String plainCreds = "user:user";
		headers.add("Authorization", "Basic " + new
				String(Base64.encode(plainCreds.getBytes())));
		HttpEntity<String> httpEntity = new HttpEntity<String>(requestJson, headers);				
		ChartResponse cr = restTemplate.postForObject(URL_CHART_API, httpEntity, ChartResponse.class);
		Assert.assertFalse(cr.getCategories().isEmpty());
		Assert.assertEquals(3, cr.getSeries().size());
		Assert.assertTrue(cr.getCategories().contains("Real Madrid"));		
	}
	
	@Test(expected = HttpClientErrorException.class)
	public void test_chart_Error_When_Invalid_Credentials() {
		
		RestTemplate restTemplate = new RestTemplate();
		String requestJson = "{\"dimensions\":[\"team\"],\"measures\":[\"champions\",\"leagues\", \"revenue\"]}";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		String plainCreds = "user:user2";
		headers.add("Authorization", "Basic " + new
				String(Base64.encode(plainCreds.getBytes())));
		HttpEntity<String> httpEntity = new HttpEntity<String>(requestJson, headers);				
		restTemplate.postForObject(URL_CHART_API, httpEntity, ChartResponse.class);
			
	}
	
	@Test(expected = HttpClientErrorException.class)
	public void test_chart_Error_Response_When_Empty_Dimension() {
		
		RestTemplate restTemplate = new RestTemplate();
		String requestJson = "{\"dimensions\":[],\"measures\":[\"champions\",\"leagues\"]}";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> httpEntity = new HttpEntity<String>(requestJson, headers);				
		restTemplate.exchange(URL_CHART_API, HttpMethod.POST, httpEntity, ChartResponse.class);		
		
	}
	
	
	@Test(expected = HttpClientErrorException.class)
	public void test_chart_Error_Response_When_More_Than_One_Dimension() {
		
		RestTemplate restTemplate = new RestTemplate();
		String requestJson = "{\"dimensions\":[\"team\", \"other\"],\"measures\":[\"champions\",\"leagues\"]}";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> httpEntity = new HttpEntity<String>(requestJson, headers);				
		restTemplate.exchange(URL_CHART_API, HttpMethod.POST, httpEntity, ChartResponse.class);		
		
	}
	
	@Test(expected = HttpClientErrorException.class)
	public void test_chart_Error_Response_When_Empty_Measures() {
		
		RestTemplate restTemplate = new RestTemplate();
		String requestJson = "{\"dimensions\":[\"team\"],\"measures\":[]}";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> httpEntity = new HttpEntity<String>(requestJson, headers);				
		restTemplate.exchange(URL_CHART_API, HttpMethod.POST, httpEntity, ChartResponse.class);		
		
	}
	
	@Test(expected = HttpClientErrorException.class)
	public void test_chart_Error_Response_When_More_Than_Three_Measures() {
		
		RestTemplate restTemplate = new RestTemplate();
		String requestJson = "{\"dimensions\":[\"team\"],\"measures\":[\"champions\",\"leagues\", \"revenue\", \"other\"]}";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> httpEntity = new HttpEntity<String>(requestJson, headers);				
		restTemplate.exchange(URL_CHART_API, HttpMethod.POST, httpEntity, ChartResponse.class);		
		
	}

}
