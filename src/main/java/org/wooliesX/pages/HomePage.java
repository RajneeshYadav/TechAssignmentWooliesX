package org.wooliesX.pages;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.wooliesX.core.ContextDataStructure;

public class HomePage extends Page{
	
	@FindBy( how = How.CSS, using = "div.nav>div.container>div.row>nav>*")
	private List<WebElement> bannerNavElements;
	
	@FindBy( how = How.CSS, using = "header#header>div:nth-child(3) div.row>*")
	private List<WebElement> headerElements;
	
	@FindBy( how = How.CSS, using = "div#header_logo")
	private WebElement headerLogo;
	
	@FindBy( how = How.CSS, using = "div#search_block_top")
	private WebElement searchblockTop;
	
	@FindBy(how = How.CSS, using = "div.col-sm-4.clearfix:not(#search_block_top)")
	private WebElement cartBlockTop;
	
	@FindBy( how = How.CSS, using = "div#block_top_menu")
	private WebElement menuBlockTop;

	@FindBy( how = How.CSS, using = "div.header_user_info>a")
	private WebElement signInLink;
	
	List<String> bannerElementTxtLst = Arrays.asList(
											"Sign in", 
											"Contact us", 
											"Call us now: 0123-456-789"
										);
	
	List<String> userBannerElementTxtLst = Arrays.asList(
											appConfig.get("username"),
											"Sign out", 
											"Contact us", 
											"Call us now: 0123-456-789"
										);
	
	@FindBy( how = How.CSS, using = "div#center_column>*")
	private List<WebElement> specificUserHomeElements;
	
	List<String> userHomeTxtLst = Arrays.asList(
									"MY ACCOUNT", 
									"Welcome to your account. Here you can manage all of your personal information and orders.", 
									"ORDER HISTORY AND DETAILS\n" 
									+"MY CREDIT SLIPS\n" 
									+"MY ADDRESSES\n" 
									+"MY PERSONAL INFORMATION\n" 
									+"MY WISHLISTS",
									"Home"
								);
	
	@FindBy( css = "a[title='Home']")
	private WebElement homeBtn;
	
	@FindBy( how = How.CSS, using = "ul.homefeatured li a>img")
	private List<WebElement> homefeaturedElementLst;
	
	@FindBy( how = How.XPATH, using = "(//ul[@id='homefeatured']/li[contains(@class, 'hovered')]//span[@itemprop='price'])[1]")
	private WebElement priceOfSelectedElement;
	
	@FindBy( how = How.XPATH, using = "//span[@class='ajax_cart_shipping_cost']")
	private WebElement shippingCostOfItemElement;
	
	@FindBy( how = How.XPATH, using = "//ul[@id='homefeatured']/li[contains(@class, 'hovered')]//a[@class='product-name']")
	private WebElement nameOfSelectedItem;
	
	@FindBy( how = How.XPATH, using = "//ul[@id='homefeatured']/li[contains(@class, 'hovered')]//a[@title='Add to cart']")
	private WebElement addToCartLnk;
	
	@FindBy( how = How.CSS, using = "a[title='Proceed to checkout']")
	private WebElement proceedToCheckoutLnk;
	
	@FindBy( how = How.CSS, using = "span[title='Continue shopping']")
	private WebElement continueShoppingSpan;
	
	public void verifyHomePage(boolean userLoggedIn) {
		
		List<String> expectedBannerElementTxtLst = bannerElementTxtLst;
		if(userLoggedIn) {
			expectedBannerElementTxtLst = userBannerElementTxtLst;
		}
		assertThat("Count of Home page banner elements is not as expected. It is missing or adding few elements.", bannerNavElements.size(), equalTo(expectedBannerElementTxtLst.size()) );
		for(int i = 0; i < bannerNavElements.size(); i++) {
			
			assertThat("Text for the element '"+bannerNavElements.get(i).getAttribute("innerHTML")+"' is not as expected", 
								bannerNavElements.get(i).getText(), equalTo(expectedBannerElementTxtLst.get(i)) );
			
		}
		
		assertThat("List of header elements does not contains the header logo element", headerElements.get(0), is(headerLogo));
		assertThat("List of header elements does not contains the search block element", headerElements.get(1), is(searchblockTop));
		assertThat("List of header elements does not contains the cart block element", headerElements.get(2), is(cartBlockTop));
		assertThat("List of header elements does not contains menu block element", headerElements.get(5), is(menuBlockTop));
		
	}

	public void openHomePage() {
		
		openUrl(appConfig.get("url"));
	}
	
	public void clickSignInLink() {
			
//		clickThis(bannerNavElements.get(0).findElement(By.cssSelector("a.login")));
		clickThis(signInLink);
	}
	
	public void verifyUserHomePage() {

		verifyHomePage(true);
		
		for(int i = 0; i < specificUserHomeElements.size(); i++) {
			
			assertThat("Text for the element '"+specificUserHomeElements.get(i).getAttribute("innerHTML")+"' is not as expected", 
					specificUserHomeElements.get(i).getText(), equalTo(userHomeTxtLst.get(i)) );
			
		}
	}
	
	public void userGoesBackToDefaultHomePage() {
		
		clickThis(homeBtn);
		waitForPageLoad();
	}
	
	
	WebElement alreadySelectedElement = null;
	Map<String, List<Double>> selectedItemMap = new HashMap<String, List<Double>>();
	String nameOfSelectedItemStr = null;
	
	public void chooseRandomDress() {
		
		Collections.shuffle(homefeaturedElementLst);
		WebElement dressElement = homefeaturedElementLst.stream()
											.filter(element -> alreadySelectedElement == null || !element.equals(alreadySelectedElement))
											.findAny()
											.get();
		hoverOver(dressElement);
		String priceOfDress = getTextOf(priceOfSelectedElement);
		System.out.println("priceOfDress: "+priceOfDress);
		nameOfSelectedItemStr = getTextOf(nameOfSelectedItem);
		selectedItemMap.put(nameOfSelectedItemStr, new ArrayList<Double>(Arrays.asList(getCurrencyNumeral(priceOfDress))));
		alreadySelectedElement = dressElement;
	}
	
	public void addToCart(String flag) {
		
		clickThis(addToCartLnk);
		waitForPageLoad();
		waitForElementClickability(proceedToCheckoutLnk);
		waitForSomeTime(2);

		double shippingCostOfItem = getCurrencyNumeral(getTextOf(shippingCostOfItemElement));
		selectedItemMap.get(nameOfSelectedItemStr).add(new Double( shippingCostOfItem));
		
		double priceTillNow = 0.0;
		double shippingCostTillNow = 0.0;
		for(List<Double> costLst : selectedItemMap.values())
		{
			priceTillNow += costLst.get(0);
			shippingCostTillNow += costLst.get(1);
		}
		System.out.println("Total price of items added to card till now ======> "+getDecimalFormatOf("#.##", priceTillNow));
		System.out.println("Total shipping cost of items added to card till now ======> "+getDecimalFormatOf("#.##", shippingCostTillNow));

		if(!flag.contains("too")) {
			
			clickThis(continueShoppingSpan);
			ContextDataStructure.getDataStructure().put("SelectedItemsDataMap", selectedItemMap);
		}
	}
	
	public void proceedToCheckout() {
		
		clickThis(proceedToCheckoutLnk);
	}
}
