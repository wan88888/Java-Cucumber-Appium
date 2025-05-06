package com.saucelabs.framework.pages;

import com.saucelabs.framework.core.Configuration;
import com.saucelabs.framework.pages.android.LoginPageAndroid;
import com.saucelabs.framework.pages.android.ProductsPageAndroid;
import com.saucelabs.framework.pages.ios.LoginPageIOS;
import com.saucelabs.framework.pages.ios.ProductsPageIOS;

/**
 * Factory class to get the appropriate page objects based on the platform.
 */
public class PageFactory {

    /**
     * Get the login page for the current platform
     * @return LoginPageAndroid for Android, LoginPageIOS for iOS
     */
    public static BasePage getLoginPage() {
        if (Configuration.isAndroid()) {
            return new LoginPageAndroid();
        } else if (Configuration.isIOS()) {
            return new LoginPageIOS();
        } else {
            throw new RuntimeException("Unsupported platform: " + Configuration.getPlatform());
        }
    }

    /**
     * Get the products page for the current platform
     * @return ProductsPageAndroid for Android, ProductsPageIOS for iOS
     */
    public static BasePage getProductsPage() {
        if (Configuration.isAndroid()) {
            return new ProductsPageAndroid();
        } else if (Configuration.isIOS()) {
            return new ProductsPageIOS();
        } else {
            throw new RuntimeException("Unsupported platform: " + Configuration.getPlatform());
        }
    }
} 