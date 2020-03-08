package org.wooliesX.pages;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class LoginPage extends Page {

	@FindBy(how = How.ID, using = "email")
	private WebElement emailTxtBox;
	
	@FindBy(how = How.ID, using = "passwd")
	private WebElement passwordTxtBox;
	
	@FindBy( how = How.ID, using = "SubmitLogin" )
	private WebElement loginBtn;
	
	@FindBy( id = "SubmitCreate" )
	private WebElement createAccountBtn;
	
	@FindBy( xpath = "//form[@id='login_form']//a[.='Forgot your password?']" )
	private WebElement forgotPasswordLink;
	
	public void openLoginPage()
	{
		openUrl(appConfig.get("url"));
	}
	
	public void checkPageLoadedProperly() {
		waitForPageLoad();
		verifyLoginPageElements();
	}
	
	private void verifyLoginPageElements() {
		
		assertThat(emailTxtBox.isDisplayed() && emailTxtBox.isEnabled(), is(true));
		assertThat(passwordTxtBox.isDisplayed() && passwordTxtBox.isEnabled(), is(true));
		assertThat(loginBtn.isDisplayed() && loginBtn.isEnabled(), is(true));
		assertThat(createAccountBtn.isDisplayed() && createAccountBtn.isEnabled(), is(true));
		assertThat(forgotPasswordLink.isDisplayed() && forgotPasswordLink.isEnabled(), is(true));
	}
	
	public void login()
	{
		setTextTo(emailTxtBox, appConfig.get("emailAddress"));
		setTextTo(passwordTxtBox, appConfig.get("password"));
		clickThis(loginBtn);
		waitForPageLoad();
	}
}
