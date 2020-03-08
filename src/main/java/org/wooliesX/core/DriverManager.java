package org.wooliesX.core;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class DriverManager {

	private static WebDriver driver;

	private DriverManager() {

	}

	public static void initDriver(String browser)
	{
		if(driver == null)
		{
			if(browser.equalsIgnoreCase("Chrome"))
			{
				System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
				ChromeOptions options = new ChromeOptions();
				options.addArguments("start-maximized");
				driver = new ChromeDriver(options);
			}
			else if(browser.equalsIgnoreCase("firefox"))
			{
				
			}
			else if(browser.equalsIgnoreCase("ie"))
			{
				
			}
		}
	}
	
	public static WebDriver getDriver()
	{
		return driver;
	}
	
	public static void quitDriver()
	{
		driver.quit();
		driver = null;
	}
}
