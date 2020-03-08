package org.wooliesX.steps;

import org.wooliesX.pages.HomePage;
import org.wooliesX.pages.LoginPage;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class LoginSteps extends Steps {

	LoginPage loginPage = (LoginPage)factory.getServiceUtil("loginpage");
	HomePage homePage = (HomePage)factory.getServiceUtil("homepage");
	
	@Given("^user has opened the application login page$")
	public void openLoginPage(){

		loginPage.openLoginPage();
	}

	@And("^all the Login page elements are displayed properly$")
	public void verifyLoginPageElements() {

		loginPage.checkPageLoadedProperly();
	}

	@Then("^user logs in the application$")
	public void login() {

		loginPage.login();
	}
	
	@Given("^user has logged into the application$")
	public void makeSureUserIsLoggedIn() {
		
		homePage.openHomePage();
		homePage.clickSignInLink();
		loginPage.login();
	}
	
}
