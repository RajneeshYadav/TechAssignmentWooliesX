@smokeAPI
Feature: This is the place order feature file 

@verifyweatherAPI
Scenario: A happy holidaymaker 
	Given I look up the weather forecast for upcoming Thursday for Sydney
	When I receive the weather forecast
	Then the temperature is warmer than 10 degrees