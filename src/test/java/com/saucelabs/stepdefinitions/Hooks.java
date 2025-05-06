package com.saucelabs.stepdefinitions;

import com.saucelabs.framework.core.DriverManager;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Hooks {
    private static final Logger logger = LoggerFactory.getLogger(Hooks.class);

    @Before
    public void setUp(Scenario scenario) {
        logger.info("Starting scenario: {}", scenario.getName());
        // Initialize the AppiumDriver
        DriverManager.initializeDriver();
    }

    @After
    public void tearDown(Scenario scenario) {
        logger.info("Ending scenario: {}", scenario.getName());
        
        // Take screenshot if scenario fails
        if (scenario.isFailed()) {
            logger.error("Scenario failed, taking screenshot");
            try {
                final byte[] screenshot = ((TakesScreenshot) DriverManager.getDriver()).getScreenshotAs(OutputType.BYTES);
                scenario.attach(screenshot, "image/png", "Screenshot");
            } catch (Exception e) {
                logger.error("Failed to take screenshot", e);
            }
        }
        
        // Quit the driver
        DriverManager.quitDriver();
    }
} 