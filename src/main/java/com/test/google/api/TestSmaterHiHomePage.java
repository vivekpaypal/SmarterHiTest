package com.test.google.api;

import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.test.selenium.fwk.BaseTest;
import com.test.selenium.fwk.DriverSession;

public class TestSmaterHiHomePage extends BaseTest {
	
	
	
	
	@Test
	public void test() throws Exception{
		List<WebElement> links = DriverSession.getDriver().findElements(By.xpath("//a"));
		
		for(WebElement e : links){
			
			String linkSrc = e.getAttribute("href");
			checkLink(linkSrc);
		}
	}
	
	
	public void clickGoBack(WebElement link){
		
		link.click();
		DriverSession.getDriver().navigate().back();
		
	}
	public void checkLink(String url) throws Exception{
		
		HttpGet request = new HttpGet(url);
		
	    
		HttpResponse response = TestPlaces.getClient().execute(request);

		// verify the call not 404
		
		Assert.assertNotEquals(response.getStatusLine().getStatusCode(),404);
	}

}
