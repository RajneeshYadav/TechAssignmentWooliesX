package org.wooliesX.steps;

import org.wooliesX.pages.HomePage;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class HomeSteps extends Steps {
	
	public HomeSteps() {
		
		super();
	}
	
	HomePage homePage = (HomePage)factory.getServiceUtil("homepage");
	

	@Given("^user has opened the application home page$")
	public void openHomePage() {

		homePage.openHomePage();
	}
	
	@And("^all the Home page elements are displayed properly$")
	public void verifyHomePage() {

		homePage.verifyHomePage(false);
	}
	
	@When("^user opens the login page$")
	public void clickSignInLink() {
		
		homePage.clickSignInLink();
	}
	
	@And("^all the user Home page elements are displayed properly$")
	public void verifyUserHomePage() {

		homePage.verifyUserHomePage();
	}
	
	@And("^user goes to the default home page$")
	public void userGoesBackToDefaultHomePage() {
		
		homePage.userGoesBackToDefaultHomePage();
	}
	
	@When("^user chooses (?:a|another) dress from the home page$")
	public void selectARandomDress() {
		
		homePage.chooseRandomDress();
	}
	
	@And("^adds it( | too )to card$")
	public void addToCard(String flag) {
		
		homePage.addToCart(flag);
	}
	
	@Then("^user can proceed to checkout$")
	public void proceedToCheckout() {
		
		homePage.proceedToCheckout();
	}
}
