package com.saucelabs.framework.core;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class DriverManager {
    private static final ThreadLocal<AppiumDriver<WebElement>> driver = new ThreadLocal<>();

    private DriverManager() {
        // Private constructor to prevent instantiation
    }

    public static AppiumDriver<WebElement> getDriver() {
        if (driver.get() == null) {
            initializeDriver();
        }
        return driver.get();
    }

    public static void initializeDriver() {
        DesiredCapabilities capabilities = Configuration.getCapabilities();
        String appiumServerUrl = Configuration.getAppiumServerUrl();
        URL serverUrl;
        
        try {
            serverUrl = new URL(appiumServerUrl);
        } catch (MalformedURLException e) {
            throw new RuntimeException("Invalid Appium server URL: " + appiumServerUrl, e);
        }
        
        // 对于Appium 2.x，URL中不需要/wd/hub
        // 添加重试机制以应对连接问题
        int maxRetries = 3;
        int retryCount = 0;
        Exception lastException = null;
        
        while (retryCount < maxRetries) {
            try {
                if (Configuration.isAndroid()) {
                    driver.set(new AndroidDriver<WebElement>(serverUrl, capabilities));
                } else if (Configuration.isIOS()) {
                    driver.set(new IOSDriver<WebElement>(serverUrl, capabilities));
                } else {
                    throw new RuntimeException("Unsupported platform: " + Configuration.getPlatform());
                }
                
                configureDriver();
                break; // 成功创建driver，退出循环
            } catch (Exception e) {
                lastException = e;
                retryCount++;
                System.out.println("Failed to initialize driver, attempt " + retryCount + 
                                   " of " + maxRetries + ": " + e.getMessage());
                // 等待一段时间后重试
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                }
            }
        }
        
        if (driver.get() == null && lastException != null) {
            throw new RuntimeException("Failed to initialize driver after " + 
                                      maxRetries + " attempts", lastException);
        }
    }

    private static void configureDriver() {
        // 使用Java 8兼容的方式设置等待
        getDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    public static void quitDriver() {
        if (driver.get() != null) {
            try {
                driver.get().quit();
            } catch (Exception e) {
                // 忽略关闭会话时的异常
                System.out.println("Warning: Exception while quitting driver: " + e.getMessage());
            } finally {
                driver.remove();
            }
        }
    }
} 