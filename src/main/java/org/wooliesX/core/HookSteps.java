package org.wooliesX.core;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.wooliesX.core.ConfigurationManager;
import org.wooliesX.core.DriverManager;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;

public class HookSteps {
	
	@Before
	public void beforeScenario()
	{
		System.out.println("System.getProperty(\"browser\"):"+System.getProperty("service"));
		System.out.println("System.getProperty(\"environment\"):"+System.getProperty("environment"));
		
		ConfigurationManager.initConfig(System.getProperty("environment"));
		if(System.getProperty("service").equalsIgnoreCase("api"))
		{
			System.out.println("Working with API calls");
		}
		else
		{
			System.out.println("Working with UI interactions");
			DriverManager.initDriver(System.getProperty("service"));
		}
	}

	@After
	public void afterScenario(Scenario scenario)
	{
		if(scenario.isFailed())
		{
			if(System.getProperty("service").equalsIgnoreCase("api"))
			{
				System.out.println("Failed API test scenario");
			}
			else
			{
				scenario.embed(saveFailureScreenShot(DriverManager.getDriver()), "image/png");
			}
		}
		if( ! System.getProperty("service").equalsIgnoreCase("api"))
		{
			DriverManager.quitDriver();
		}
	}
	
	public byte[] saveFailureScreenShot(WebDriver driver) {
		return ((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES);
	}
}
