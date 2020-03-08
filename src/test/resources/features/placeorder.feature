@smokeUI
Feature: This is the place order feature file

@verifyHomeALoginPages
Scenario: This is the smoke test for verifying that default home, login and user home pages are displayed as expected
	Given user has opened the application home page
	And all the Home page elements are displayed properly
	When user opens the login page
	And all the Login page elements are displayed properly
	Then user logs in the application
	And all the user Home page elements are displayed properly
	
@placeOrder
Scenario: This test is to check the place order functionality
	Given user has logged into the application
	And user goes to the default home page
	When user chooses a dress from the home page
	And adds it to card 
	And user chooses another dress from the home page
	And adds it too to card
	Then user can proceed to checkout
	And can verify the products and the total displayed
	And can finish the order