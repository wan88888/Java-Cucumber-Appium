package com.saucelabs.framework.utils;

import com.saucelabs.framework.core.DriverManager;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Utility class for wait operations
 */
public class WaitUtils {
    
    private WaitUtils() {
        // Private constructor to prevent instantiation
    }
    
    /**
     * Wait for element to be visible
     * @param element Element to wait for
     * @param timeoutInSeconds Timeout in seconds
     */
    public static void waitForVisibility(WebElement element, long timeoutInSeconds) {
        AppiumDriver<WebElement> driver = DriverManager.getDriver();
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.until(ExpectedConditions.visibilityOf(element));
    }
    
    /**
     * Wait for element to be clickable
     * @param element Element to wait for
     * @param timeoutInSeconds Timeout in seconds
     */
    public static void waitForClickability(WebElement element, long timeoutInSeconds) {
        AppiumDriver<WebElement> driver = DriverManager.getDriver();
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }
    
    /**
     * Wait for element to be present
     * @param locator Locator of the element to wait for
     * @param timeoutInSeconds Timeout in seconds
     */
    public static void waitForPresence(By locator, long timeoutInSeconds) {
        AppiumDriver<WebElement> driver = DriverManager.getDriver();
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }
    
    /**
     * Wait for element to be invisible
     * @param element Element to wait for
     * @param timeoutInSeconds Timeout in seconds
     */
    public static void waitForInvisibility(WebElement element, long timeoutInSeconds) {
        AppiumDriver<WebElement> driver = DriverManager.getDriver();
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.until(ExpectedConditions.invisibilityOf(element));
    }
    
    /**
     * Static sleep method - use with caution, prefer explicit waits
     * @param milliseconds Time to sleep in milliseconds
     */
    public static void sleep(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
} 