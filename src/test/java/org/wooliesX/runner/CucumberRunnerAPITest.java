package org.wooliesX.runner;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@CucumberOptions(
			features= {"src/test/resources/features"},
			glue= {"org.wooliesX.steps","org.wooliesX.api.steps","org.wooliesX.core"},
			plugin= {"html:target/cucumber-html-report", "json:target/jsonReports/cucumber.json"},
			tags= {"@smokeAPI"}
		)

@RunWith(Cucumber.class)
public class CucumberRunnerAPITest {

}
