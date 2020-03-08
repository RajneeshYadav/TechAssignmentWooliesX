package org.wooliesX.core;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

public class ConfigurationManager {
	
	private static Map<String, String> appConfig;
	
	private ConfigurationManager() {
		
	}

	public static void initConfig(String env)
	{
		if(appConfig == null) {
			ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
	        try {
	            final Map<String, Map<String, String>> appConfigurationsMap = objectMapper.readValue(new File("src/test/resources/configurations/AppConfigurations.yaml"), new TypeReference<Map<String, Map<String, String>>>() {
	            });

	            System.out.println(appConfigurationsMap);
	            Map<String, String> appConfig = appConfigurationsMap.get(env);
	            System.out.println(appConfig);
	            ConfigurationManager.appConfig = appConfig;
	            
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
		}
	}
	
	public static Map<String, String> getConfig()
	{
		return appConfig;
	}
	
	public static void main(String[] args) {

		initConfig("sit");
	}

}
