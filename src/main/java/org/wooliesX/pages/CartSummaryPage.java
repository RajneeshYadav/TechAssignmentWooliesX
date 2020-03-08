package org.wooliesX.pages;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.wooliesX.core.ContextDataStructure;

public class CartSummaryPage extends Page{
	
	@FindBy( how = How.XPATH, using = "//table[@id='cart_summary']//tr[@id]")
	private List<WebElement> summaryTableElementLst;
	
	@FindBy( how = How.CSS, using = "td#total_product")
	private WebElement displayedTotalProduct;
	
	@FindBy( how = How.CSS, using = "td#total_shipping")
	private WebElement displayedTotalShipping;
	
	@FindBy( how = How.ID, using = "total_price")
	private WebElement displayedTotalPrice;
	
	@FindBy( how = How.CSS, using = "p.cart_navigation.clearfix a[title='Proceed to checkout']")
	private WebElement proceedToCheckoutLnk;
	
	@FindBy( how = How.CSS, using = "button[name='processAddress']")
	private WebElement procesAddressBtn;
	
	@FindBy( how = How.CSS, using = "input#cgv")
	private WebElement agreeTermOfServicesChbx;
	
	@FindBy( how = How.CSS, using = "button[name='processCarrier']")
	private WebElement processCarrierBtn;
	
	@FindBy( how = How.CLASS_NAME, using = "cheque")
	private WebElement payByChequeLnk;
	
	@FindBy( how = How.CSS, using = "p#cart_navigation>button")
	private WebElement confirmMyOrder;
	
	@FindBy( how = How.CSS, using = "p.alert.alert-success")
	private WebElement orderSuccessAlert;
	
	public void verifyCartSummary() {
		
		@SuppressWarnings("unchecked")
		Map<String, List<Double>> selectedItemMap = (Map<String, List<Double>>) ContextDataStructure.getDataStructure().get("SelectedItemsDataMap");
		System.out.println("selectedItemMap: "+selectedItemMap);
		assertThat("Count of Products List displayed on Cart Summary Page is not as expected", summaryTableElementLst.size(), is(selectedItemMap.size()));
		
		summaryTableElementLst.stream().
			forEach(webElement -> 
			{
				String displayedItemName = getTextOf(webElement.findElement(By.xpath("td[2]/p[@class='product-name']")));
				System.out.println("displayedItemName: "+displayedItemName);
				assertThat("Expected product name not found on the cart summary page", selectedItemMap.get(displayedItemName), is(not(nullValue())));
				
				double displayedItemPrice = getCurrencyNumeral( getTextOf(webElement.findElement(By.xpath("td[6]/span"))) );
				assertThat("Expected product price not as expected on the cart summary page", displayedItemPrice, is(selectedItemMap.get(displayedItemName).get(0)));
			});
		
		double expectedTotalProductPrice = 0.0;
		double expectedShippingCostTotal = 0.0;
		for(List<Double> costLst : selectedItemMap.values())
		{
			expectedTotalProductPrice += costLst.get(0);
			expectedShippingCostTotal += costLst.get(1);
		}
		expectedTotalProductPrice = getDecimalFormatOf("#.##", expectedTotalProductPrice);
		System.out.println("Expected Total Product price of items added to card:: "+expectedTotalProductPrice);
		System.out.println("Expected Total Shipping cost of items added to card:: "+expectedShippingCostTotal);
		double expectedTotalPrice = getDecimalFormatOf("#.##", expectedTotalProductPrice + expectedShippingCostTotal);
		System.out.println("Expected Total Price of items added to card:: "+expectedTotalPrice);
		
		assertThat("Total Product price displayed is not as expected.", getCurrencyNumeral(getTextOf(displayedTotalProduct)), equalTo(expectedTotalProductPrice));
		assertThat("Total Shipping price displayed is not as expected.", getCurrencyNumeral(getTextOf(displayedTotalShipping)), equalTo(expectedShippingCostTotal));
		assertThat("Total Order cost displayed is not as expected.", getCurrencyNumeral(getTextOf(displayedTotalPrice)), equalTo(expectedTotalPrice));
	}
	
	public void proceedToCheckout() {
		
		clickThis(proceedToCheckoutLnk);
		waitForPageLoad();
		clickThis(procesAddressBtn);
		waitForPageLoad();
//		clickThis(agreeTermOfServicesChbx);
		clickThisJS("input#cgv");
		clickThis(processCarrierBtn);
		waitForPageLoad();
		clickThis(payByChequeLnk);
		waitForPageLoad();
		clickThis(confirmMyOrder);
		waitForPageLoad();
		waitForElementClickability(orderSuccessAlert);
	}
}
