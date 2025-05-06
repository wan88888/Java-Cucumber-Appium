package com.saucelabs.framework.pages;

import com.saucelabs.framework.core.DriverManager;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class BasePage {
    protected AppiumDriver<WebElement> driver;
    protected WebDriverWait wait;
    protected static final int DEFAULT_TIMEOUT = 15;
    protected static final int SHORT_TIMEOUT = 5;

    public BasePage() {
        this.driver = DriverManager.getDriver();
        this.wait = new WebDriverWait(driver, DEFAULT_TIMEOUT);
        
        // 我们不再使用PageFactory和AppiumFieldDecorator
        // 改为在子类中直接使用driver.findElement()
    }

    /**
     * 根据accessibility ID查找元素，具有重试机制
     * @param accessibilityId accessibility ID
     * @return 找到的WebElement
     */
    protected WebElement findElementByAccessibilityId(String accessibilityId) {
        return findElementByAccessibilityId(accessibilityId, DEFAULT_TIMEOUT);
    }

    /**
     * 根据accessibility ID查找元素，使用自定义超时时间
     * @param accessibilityId accessibility ID
     * @param timeoutInSeconds 超时时间（秒）
     * @return 找到的WebElement
     */
    protected WebElement findElementByAccessibilityId(String accessibilityId, int timeoutInSeconds) {
        WebDriverWait customWait = new WebDriverWait(driver, timeoutInSeconds);
        try {
            return customWait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//*[@content-desc='" + accessibilityId + "' or @name='" + accessibilityId + "']")));
        } catch (TimeoutException e) {
            // 可能是元素定位问题，尝试直接使用Appium的查找方法
            return driver.findElementByAccessibilityId(accessibilityId);
        }
    }
    
    /**
     * 根据XPath查找元素
     * @param xpath XPath表达式
     * @return 找到的WebElement
     */
    protected WebElement findElementByXPath(String xpath) {
        return findElementByXPath(xpath, DEFAULT_TIMEOUT);
    }

    /**
     * 根据XPath查找元素，使用自定义超时时间
     * @param xpath XPath表达式
     * @param timeoutInSeconds 超时时间（秒）
     * @return 找到的WebElement
     */
    protected WebElement findElementByXPath(String xpath, int timeoutInSeconds) {
        WebDriverWait customWait = new WebDriverWait(driver, timeoutInSeconds);
        return customWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
    }

    /**
     * Wait for element to be clickable and click on it
     * @param element The element to click
     */
    protected void click(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element)).click();
    }

    /**
     * Wait for element to be visible and send keys to it
     * @param element The element to send keys to
     * @param text The text to send
     */
    protected void sendKeys(WebElement element, String text) {
        wait.until(ExpectedConditions.visibilityOf(element)).sendKeys(text);
    }

    /**
     * Wait for element to be visible
     * @param element The element to wait for
     */
    protected void waitForVisibility(WebElement element) {
        waitForVisibility(element, DEFAULT_TIMEOUT);
    }

    /**
     * 等待元素可见，使用自定义超时时间
     * @param element 要等待的元素
     * @param timeoutInSeconds 超时时间（秒）
     */
    protected void waitForVisibility(WebElement element, int timeoutInSeconds) {
        try {
            WebDriverWait customWait = new WebDriverWait(driver, timeoutInSeconds);
            customWait.until(ExpectedConditions.visibilityOf(element));
        } catch (StaleElementReferenceException e) {
            // 元素已过时，尝试重新查找（通常发生在页面刷新或重新加载后）
            System.out.println("StaleElementReferenceException occurred. Element is no longer attached to DOM.");
        }
    }

    /**
     * 等待元素可点击
     * @param element 要等待的元素
     */
    protected void waitForClickability(WebElement element) {
        waitForClickability(element, DEFAULT_TIMEOUT);
    }

    /**
     * 等待元素可点击，使用自定义超时时间
     * @param element 要等待的元素
     * @param timeoutInSeconds 超时时间（秒）
     */
    protected void waitForClickability(WebElement element, int timeoutInSeconds) {
        WebDriverWait customWait = new WebDriverWait(driver, timeoutInSeconds);
        customWait.until(ExpectedConditions.elementToBeClickable(element));
    }

    /**
     * 安全点击元素，先等待元素可点击
     * @param element 要点击的元素
     */
    protected void safeClick(WebElement element) {
        try {
            waitForClickability(element);
            element.click();
        } catch (Exception e) {
            // 如果常规点击失败，尝试使用JavaScript执行点击
            System.out.println("Regular click failed, trying alternative method: " + e.getMessage());
            try {
                driver.executeScript("arguments[0].click();", element);
            } catch (Exception jsException) {
                throw new RuntimeException("Failed to click element even with JavaScript: " + jsException.getMessage());
            }
        }
    }

    /**
     * 安全输入文本，先清除文本框
     * @param element 文本输入元素
     * @param text 要输入的文本
     */
    protected void safeInput(WebElement element, String text) {
        waitForVisibility(element);
        element.clear();
        element.sendKeys(text);
    }

    /**
     * Get text from element
     * @param element The element to get text from
     * @return The text of the element
     */
    protected String getText(WebElement element) {
        return wait.until(ExpectedConditions.visibilityOf(element)).getText();
    }

    /**
     * Check if element is displayed
     * @param element The element to check
     * @return True if element is displayed, false otherwise
     */
    protected boolean isElementDisplayed(WebElement element) {
        try {
            return element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Wait for element to disappear
     * @param by The locator of the element to wait for
     */
    protected void waitForElementToDisappear(By by) {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
    }
} 