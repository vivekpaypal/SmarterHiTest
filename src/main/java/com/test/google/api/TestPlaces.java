package com.test.google.api;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TestPlaces {
	
	private static final String API_KEY = "AIzaSyC5QrxwxQDyrzSqdpuSq0E1_tb28adT2Ik";
	private static final String SCHEMA_HOST="https://maps.googleapis.com";
	private static final String PATH_NEARBY_SEARCH="/maps/api/place/nearbysearch/json";
	
	
	public static HttpClient getClient(){
		return HttpClientBuilder.create().build();
	}
	
	@Test
	public  void testAPI() throws Exception {
		
		// do the places API call
		
		HttpClient client = getClient();
		// add request header
	    HttpGet request = new HttpGet(buildPlacesUri("-33.8670,151.1957","500","food","cruise"));
		
	    
		HttpResponse response = client.execute(request);

		// verify the call return 200 OK
		
		Assert.assertEquals(response.getStatusLine().getStatusCode(),200);
		
		String jsonString =  EntityUtils.toString(response.getEntity());
		
		//parse the json result and verify  result
		
		Assert.assertEquals(getFirstResult(jsonString),"Australian Cruise Group");
		
	}
	
	@Test (invocationCount=1,threadPoolSize=1)

	public void loadTestAPI()throws Exception {


	// do the places API call


	HttpClient client =  HttpClientBuilder.create().build();

	// add request header

	    HttpGet request = new HttpGet(buildPlacesUri("-33.8670,151.1957","500","food","cruise"));


	   

	HttpResponse response = client.execute(request);



	// verify the call return 200 OK


	Assert.assertEquals(response.getStatusLine().getStatusCode(),200);

	}


	
	public static String buildPlacesUri(String location,String radius,String type,String name){
		
		StringBuilder url = new StringBuilder();
		url.append(SCHEMA_HOST +PATH_NEARBY_SEARCH +"?location=" + location +"&radius=" + radius +"&types=" +type + "&name=" +name +"&key=" +API_KEY);
		
		return url.toString();
	}
	
	
	public static String getFirstResult(String json) throws ParseException{
		

	      JSONParser parser = new JSONParser();
	      Object obj = parser.parse(json);
	      
	      JSONObject jo = (JSONObject)obj;
	      
	      JSONArray array  = (JSONArray) jo.get("results");
	      JSONObject firstResult = (JSONObject) array.get(0);
	      
	     return (String) firstResult.get("name");
	}

}
