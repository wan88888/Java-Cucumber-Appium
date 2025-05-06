package com.saucelabs.framework.pages.android;

import com.saucelabs.framework.pages.BasePage;
import org.openqa.selenium.WebElement;

public class LoginPageAndroid extends BasePage {

    // 移除@AndroidFindBy注解，改用findElementByAccessibilityId
    private WebElement getUsernameField() {
        return findElementByAccessibilityId("test-Username");
    }

    private WebElement getPasswordField() {
        return findElementByAccessibilityId("test-Password");
    }

    private WebElement getLoginButton() {
        return findElementByAccessibilityId("test-LOGIN");
    }

    private WebElement getErrorMessageElement() {
        return findElementByXPath("//android.view.ViewGroup[@content-desc='test-Error message']/android.widget.TextView");
    }

    /**
     * 在用户名字段中输入用户名
     * @param username 要输入的用户名
     * @return 当前页面的实例，用于链式调用
     */
    public LoginPageAndroid enterUsername(String username) {
        WebElement usernameField = getUsernameField();
        safeInput(usernameField, username);
        return this;
    }

    /**
     * 在密码字段中输入密码
     * @param password 要输入的密码
     * @return 当前页面的实例，用于链式调用
     */
    public LoginPageAndroid enterPassword(String password) {
        WebElement passwordField = getPasswordField();
        safeInput(passwordField, password);
        return this;
    }

    /**
     * 点击登录按钮
     * @return 当前页面的实例，用于链式调用
     */
    public LoginPageAndroid clickLogin() {
        WebElement loginButton = getLoginButton();
        safeClick(loginButton);
        return this;
    }

    /**
     * 获取错误消息文本
     * @return 错误消息文本
     */
    public String getErrorMessage() {
        try {
            WebElement errorElement = getErrorMessageElement();
            waitForVisibility(errorElement);
            return errorElement.getText();
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 检查错误消息是否显示
     * @return 如果错误消息显示则返回true，否则返回false
     */
    public boolean isErrorMessageDisplayed() {
        try {
            WebElement errorElement = getErrorMessageElement();
            waitForVisibility(errorElement, SHORT_TIMEOUT);
            return errorElement.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 使用提供的凭据登录
     * @param username 用户名
     * @param password 密码
     * @return 当前页面的实例，用于链式调用
     */
    public LoginPageAndroid login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLogin();
        return this;
    }

    /**
     * Check if the login page is displayed
     * @return True if login page is displayed, false otherwise
     */
    public boolean isDisplayed() {
        try {
            return isElementDisplayed(getUsernameField()) &&
                   isElementDisplayed(getPasswordField()) &&
                   isElementDisplayed(getLoginButton());
        } catch (Exception e) {
            return false;
        }
    }
} 