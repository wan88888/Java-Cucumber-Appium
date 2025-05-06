package com.saucelabs.framework.pages.ios;

import com.saucelabs.framework.pages.BasePage;
import org.openqa.selenium.WebElement;

public class ProductsPageIOS extends BasePage {

    private WebElement getProductTitle() {
        return findElementByAccessibilityId("test-PRODUCTS");
    }

    private WebElement getMenuButton() {
        return findElementByAccessibilityId("test-Menu");
    }

    private WebElement getCartButton() {
        return findElementByAccessibilityId("test-Cart");
    }

    /**
     * Check if the products page is displayed
     * @return True if products page is displayed, false otherwise
     */
    public boolean isDisplayed() {
        try {
            return isElementDisplayed(getProductTitle());
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Get the title text
     * @return The title text
     */
    public String getTitle() {
        try {
            // 确保等待元素可见，并获取其文本
            WebElement title = getProductTitle();
            waitForVisibility(title);
            return title.getText();
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * Click on the menu button
     * @return This page instance for method chaining
     */
    public ProductsPageIOS clickMenuButton() {
        click(getMenuButton());
        return this;
    }

    /**
     * Click on the cart button
     * @return This page instance for method chaining (could return a CartPage in a real implementation)
     */
    public ProductsPageIOS clickCartButton() {
        click(getCartButton());
        return this;
    }
} 