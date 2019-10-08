package ebay.qa.test;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;


@RunWith(Cucumber.class)

@CucumberOptions(
plugin = {"json:target/cucumber.json"},
features = {"src/test/features"},
glue= {"ebay.qa.testautomation"},
//tags = {"@Scenario002"}
tags = {"@Regression"}
)

public class TestRunner {
	
}
