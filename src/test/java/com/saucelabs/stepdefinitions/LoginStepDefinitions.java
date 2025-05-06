package com.saucelabs.stepdefinitions;

import com.saucelabs.framework.core.Configuration;
import com.saucelabs.framework.pages.PageFactory;
import com.saucelabs.framework.pages.android.LoginPageAndroid;
import com.saucelabs.framework.pages.android.ProductsPageAndroid;
import com.saucelabs.framework.pages.ios.LoginPageIOS;
import com.saucelabs.framework.pages.ios.ProductsPageIOS;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

public class LoginStepDefinitions {

    private LoginPageAndroid loginPageAndroid;
    private LoginPageIOS loginPageIOS;
    private ProductsPageAndroid productsPageAndroid;
    private ProductsPageIOS productsPageIOS;

    @Given("I am on the login screen")
    public void iAmOnTheLoginScreen() {
        if (Configuration.isAndroid()) {
            loginPageAndroid = (LoginPageAndroid) PageFactory.getLoginPage();
            Assert.assertTrue("Login page is not displayed", loginPageAndroid.isDisplayed());
        } else if (Configuration.isIOS()) {
            loginPageIOS = (LoginPageIOS) PageFactory.getLoginPage();
            Assert.assertTrue("Login page is not displayed", loginPageIOS.isDisplayed());
        }
    }

    @When("I enter username {string}")
    public void iEnterUsername(String username) {
        if (Configuration.isAndroid()) {
            loginPageAndroid.enterUsername(username);
        } else if (Configuration.isIOS()) {
            loginPageIOS.enterUsername(username);
        }
    }

    @And("I enter password {string}")
    public void iEnterPassword(String password) {
        if (Configuration.isAndroid()) {
            loginPageAndroid.enterPassword(password);
        } else if (Configuration.isIOS()) {
            loginPageIOS.enterPassword(password);
        }
    }

    @And("I tap on the login button")
    public void iTapOnTheLoginButton() {
        if (Configuration.isAndroid()) {
            loginPageAndroid.clickLogin();
            productsPageAndroid = new ProductsPageAndroid();
        } else if (Configuration.isIOS()) {
            loginPageIOS.clickLogin();
            productsPageIOS = new ProductsPageIOS();
        }
    }

    @Then("I should see the products page")
    public void iShouldSeeTheProductsPage() {
        if (Configuration.isAndroid()) {
            Assert.assertTrue("Products page is not displayed", productsPageAndroid.isDisplayed());
        } else if (Configuration.isIOS()) {
            Assert.assertTrue("Products page is not displayed", productsPageIOS.isDisplayed());
        }
    }

    @Then("I should see the error message {string}")
    public void iShouldSeeTheErrorMessage(String errorMessage) {
        if (Configuration.isAndroid()) {
            Assert.assertEquals("Error message does not match", errorMessage, loginPageAndroid.getErrorMessage());
            Assert.assertTrue("Error message is not displayed", loginPageAndroid.isErrorMessageDisplayed());
        } else if (Configuration.isIOS()) {
            Assert.assertEquals("Error message does not match", errorMessage, loginPageIOS.getErrorMessage());
            Assert.assertTrue("Error message is not displayed", loginPageIOS.isErrorMessageDisplayed());
        }
    }
} 