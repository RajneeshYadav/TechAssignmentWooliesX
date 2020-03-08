package org.wooliesX.core;

import org.wooliesX.api.BaseAPI;
import org.wooliesX.api.WeatherAPI;

public class APIServiceFactory implements IAbstractFactory<BaseAPI> {

	private BaseAPI baseAPI;
	private WeatherAPI weatherAPI; 
	
	private static APIServiceFactory apiServiceFactory = null;
	
	private APIServiceFactory() {
		
	}
	
	@Override
	public BaseAPI getServiceUtil(String serviceName) {

		if(serviceName.equalsIgnoreCase("weatherapi")) {
			
			return weatherAPI == null ? new WeatherAPI() : weatherAPI;
		}
		else {
			
			return baseAPI;
		}
		
	}

	public static APIServiceFactory getFactory() {
		
		return apiServiceFactory == null ? new APIServiceFactory() : apiServiceFactory;
	}
}
