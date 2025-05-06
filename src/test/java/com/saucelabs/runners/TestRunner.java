package com.saucelabs.runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"com.saucelabs.stepdefinitions"},
        plugin = {"pretty", "html:target/cucumber-reports/report.html", 
                "json:target/cucumber-reports/report.json"},
        monochrome = true
)
public class TestRunner {
    // This class should be empty
    // It's used only as a runner for the Cucumber tests
} 