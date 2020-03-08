package org.wooliesX.pages;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;
import java.util.Map;
import java.util.function.Function;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.wooliesX.core.ConfigurationManager;
import org.wooliesX.core.DriverManager;

public class Page {

	protected WebDriver driver = DriverManager.getDriver();
	protected Map<String, String> appConfig = ConfigurationManager.getConfig();
	protected final int PAGE_LOAD_WAIT_TIME = 60;
	protected final int ELEMENT_WAIT_TIME = 30;
	protected final int POLL_TIME = 500;
	
	public Page() {
		
		PageFactory.initElements(driver, this);
	}
	
	public WebElement waitForElementClickability(Object locator) {
		
		if (locator instanceof By) {
			return getWebDriverWait(ELEMENT_WAIT_TIME).until(ExpectedConditions.elementToBeClickable((By)locator));
		}
		else if (locator instanceof WebElement) {
			return getWebDriverWait(ELEMENT_WAIT_TIME).until(ExpectedConditions.elementToBeClickable((WebElement)locator));
		}
		else if (locator instanceof String) {
			return getWebDriverWait(ELEMENT_WAIT_TIME).until(ExpectedConditions.elementToBeClickable(By.xpath(String.valueOf(locator))));
		}
		return null;
	}
	
	public void waitForPageLoad()
	{
		//way 1
//		getWebDriverWait(PAGE_LOAD_WAIT_TIME).until((webdriver) -> ((JavascriptExecutor)driver).executeScript("return document.readyState").equals("complete") );
		//way 2
//		getWebDriverWait(PAGE_LOAD_WAIT_TIME).until(new ExpectedCondition<Boolean> () {
//			@Override
//			public Boolean apply(WebDriver input) {
//				((JavascriptExecutor)driver).executeScript("return document.readyState").equals("complete");
//				return null;
//			}
//		});
		//way 3
		getWebDriverWait(PAGE_LOAD_WAIT_TIME).until(new MyPageLoadExpectedCondtion());
	}
	
	private WebDriverWait getWebDriverWait(int timeToWait) {
		
		return new WebDriverWait(driver, ELEMENT_WAIT_TIME, POLL_TIME);
	}
	
	protected void openUrl(String url)
	{
		driver.get(url);
	}
	
	protected void setTextTo(Object webElementIdentifier, String textToFill)
	{
		System.out.println("Setting text '"+textToFill+"' to -> "+webElementIdentifier);
		waitForElementClickability(webElementIdentifier).sendKeys(textToFill);
	}
	
	protected String getTextOf(Object webElementIdentifier)
	{
		System.out.println("Getting text of -> "+webElementIdentifier);
		return waitForElementClickability(webElementIdentifier).getText();
	}
	
	protected void clickThis(Object webElementIdentifier)
	{
		System.out.println("Clicking this -> "+webElementIdentifier);
		waitForElementClickability(webElementIdentifier).click();
	}
	
	protected void hoverOver(Object webElementIdentifier) {
		
		System.out.println("Hovering over -> "+webElementIdentifier);
		Actions actionsBuilder = new Actions(driver);
		actionsBuilder.moveToElement(waitForElementClickability(webElementIdentifier)).build().perform();
	}
	
	@SuppressWarnings("static-access")
	protected void waitForSomeTime(int secondsToWait) {
		
		try {
			Thread.currentThread().sleep(secondsToWait * 1000);
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		}
	}
	
	protected double getCurrencyNumeral(String currencyString) {
		
		Number number = null;
		try {
			number = NumberFormat.getCurrencyInstance(Locale.getDefault()).parse(currencyString);
		} catch (ParseException e) {
			
			e.printStackTrace();
		}
		
		return number.doubleValue();
	}
	
	protected double getDecimalFormatOf(String decimalFormatStr, double doubleNum) {
		
		DecimalFormat decimalFormat = new DecimalFormat(decimalFormatStr);
		try {
			doubleNum = decimalFormat.parse(decimalFormat.format(doubleNum)).doubleValue();
		} catch (ParseException e) {
			
			e.printStackTrace();
		}
		return doubleNum;
	}
	
	protected void clickThisJS(String cssSelector) {
		
		System.out.println("Clicking this by Java Script -> "+cssSelector);
		((JavascriptExecutor)driver).executeScript("document.querySelector(\""+cssSelector+"\").click()");
	}
}

class MyPageLoadExpectedCondtion implements Function<WebDriver, Boolean>
{
	@Override
	public Boolean apply(WebDriver driver) {
		
		return ((JavascriptExecutor)driver).executeScript("return document.readyState").equals("complete");
	}
}
