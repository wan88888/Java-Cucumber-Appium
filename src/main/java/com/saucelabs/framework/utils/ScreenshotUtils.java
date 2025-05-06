package com.saucelabs.framework.utils;

import com.saucelabs.framework.core.DriverManager;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Utility class for taking screenshots
 */
public class ScreenshotUtils {
    private static final Logger logger = LoggerFactory.getLogger(ScreenshotUtils.class);
    private static final String SCREENSHOT_DIRECTORY = "target/screenshots/";

    private ScreenshotUtils() {
        // Private constructor to prevent instantiation
    }

    /**
     * Take a screenshot and save it to the screenshots directory
     * @param screenshotName Name of the screenshot
     * @return Path to the saved screenshot file, or null if screenshot could not be taken
     */
    public static String takeScreenshot(String screenshotName) {
        try {
            // Create screenshot directory if it doesn't exist
            File directory = new File(SCREENSHOT_DIRECTORY);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            // Take screenshot
            File screenshot = ((TakesScreenshot) DriverManager.getDriver()).getScreenshotAs(OutputType.FILE);
            
            // Generate filename with timestamp
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
            String timestamp = now.format(formatter);
            
            // Create the file path
            String fileName = screenshotName + "_" + timestamp + ".png";
            Path destinationPath = Paths.get(SCREENSHOT_DIRECTORY, fileName);
            
            // Copy the screenshot to the destination
            Files.copy(screenshot.toPath(), destinationPath);
            
            logger.info("Screenshot saved: {}", destinationPath);
            return destinationPath.toString();
        } catch (IOException e) {
            logger.error("Failed to take screenshot", e);
            return null;
        }
    }

    /**
     * Take a screenshot and return it as byte array
     * @return Screenshot as byte array, or null if screenshot could not be taken
     */
    public static byte[] takeScreenshotAsBytes() {
        try {
            return ((TakesScreenshot) DriverManager.getDriver()).getScreenshotAs(OutputType.BYTES);
        } catch (Exception e) {
            logger.error("Failed to take screenshot as bytes", e);
            return null;
        }
    }
} 