package org.wooliesX.steps;

import org.wooliesX.pages.CartSummaryPage;
import org.wooliesX.pages.HomePage;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class CartSummarySteps extends Steps {
	
	public CartSummarySteps() {
		
		super();
	}
	
	CartSummaryPage cartSummaryPage = (CartSummaryPage)factory.getServiceUtil("cartsummarypage");
	
	@And("^can verify the products and the total displayed$")
	public void verifyCartSummary() {
		
		cartSummaryPage.verifyCartSummary();
	}
	
	@And("^can finish the order$")
	public void proceedToCheckout() {
		
		cartSummaryPage.proceedToCheckout();
	}
}
