package org.wooliesX.api.steps;

import org.wooliesX.api.WeatherAPI;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class WeatherAPISteps extends ApiSteps {
	
	WeatherAPI weatherApi = (WeatherAPI)factory.getServiceUtil("weatherapi");
	
	@Given("^I look up the weather forecast for upcoming (.*) for (.*)$")
	public void prepareServiceRequest(String day, String city) {
		
		weatherApi.prepareServiceRequest(day, city);
	}
	
	@When("^I receive the weather forecast$")
	public void postAndReceiveResponse() {
		
		weatherApi.postAndReceiveResponse();
	}
	
	@Then("^the temperature is warmer than (\\d+) degrees$")
	public void verifyResponseReceived(int temp) {
		
		weatherApi.verifyResponseReceived(temp);
	}

}
