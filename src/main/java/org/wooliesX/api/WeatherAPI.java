package org.wooliesX.api;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import java.util.Calendar;
import java.util.Locale;
import java.util.Map;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.path.json.config.JsonPathConfig.NumberReturnType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.config.JsonConfig.*;

public class WeatherAPI extends BaseAPI {
	
	private RequestSpecification reqSpecs;
	private int daysCountToGetDataFor;
	private ResponseSpecification respSpecs;
	private Response response;
	
	public void prepareServiceRequest(String dayOfWeek, String city) {
		
		RequestSpecBuilder reqSpecBuilder = new RequestSpecBuilder();
		reqSpecBuilder.setBaseUri(appConfig.get("apibaseurl"));
		reqSpecBuilder.setBasePath(appConfig.get("apibasepath"));
		/*
		 * Following blocked code is not working as my api key generated on https://openweathermap.org/ is not working, so using sample request
		 */
//		reqSpecBuilder.addQueryParam("q", city);
//		reqSpecBuilder.addQueryParam("units", "metric");
		/*
		 * Following is the sample request available on https://openweathermap.org/
		 */
		reqSpecBuilder.addQueryParam("lat", "35");
		reqSpecBuilder.addQueryParam("lon", "139");
		
		daysCountToGetDataFor = getDifferenceInDaysFromToday(dayOfWeek);
		
		reqSpecBuilder.addQueryParam("cnt", daysCountToGetDataFor);
		reqSpecBuilder.addQueryParam("appid", appConfig.get("apikey"));
		reqSpecBuilder.setConfig(RestAssured.config().jsonConfig(jsonConfig().numberReturnType(NumberReturnType.FLOAT_AND_DOUBLE)));
		reqSpecBuilder.log(LogDetail.ALL);
		reqSpecs = reqSpecBuilder.build();
	}
	
	
	public void postAndReceiveResponse() {
		
		response =
				given()
					.spec(reqSpecs)
				.when()
					.get();
	}
	
	public void verifyResponseReceived(int temp) {
		
		
		ResponseSpecBuilder resSpecBuilder = new ResponseSpecBuilder();
		resSpecBuilder.expectStatusCode(200);
		resSpecBuilder.log(LogDetail.ALL);
		respSpecs = resSpecBuilder.build();
		
		//Here getting the minimum temperature of the day number calculated(daysCountToGetDataFor) from the returned list which would be for the required day of week like Thursday
		response.then()
					.spec(respSpecs)
					.assertThat()
					.body("list.'temp'["+daysCountToGetDataFor+"].min", is(greaterThan((float)temp)));
	}
	
	private int getDifferenceInDaysFromToday(String dayOfWeek) {
		
		
		Calendar cal = Calendar.getInstance();
		Map<String, Integer> weekDayNameNumberMap = cal.getDisplayNames(Calendar.DAY_OF_WEEK, Calendar.LONG_FORMAT, Locale.getDefault());
		System.out.println(weekDayNameNumberMap.get(dayOfWeek));
		int differenceInDays = cal.get(Calendar.DAY_OF_WEEK) - weekDayNameNumberMap.get(dayOfWeek);
		System.out.println("differenceInDays_1: "+differenceInDays);
		if(differenceInDays < 0)
		{
			differenceInDays = Math.abs(differenceInDays);
		}
		else if(differenceInDays > 0)
		{
			differenceInDays = 7 - differenceInDays;
		}
		System.out.println("differenceInDays_2: "+differenceInDays);
		return differenceInDays;
	}
	public static void main(String args[]) {
		
		new WeatherAPI().getDifferenceInDaysFromToday("Monday");
		
	}

}
