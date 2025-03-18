package com.basesetup.playwright.stepdefinitions;

import com.basesetup.playwright.pages.login.LoginPO;
import com.basesetup.playwright.utils.config.PropertyFileReader;
import com.basesetup.playwright.utils.managers.PlaywrightManager;
import com.microsoft.playwright.Page;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class LoginStepDefs {
    private final LoginPO loginPage;

    public LoginStepDefs() {
        this.loginPage = new LoginPO(PlaywrightManager.getPage());
    }

    @Given("User accesses the Login page")
    public void userAccessesTheLoginPage() {
    }

    @When("User enters username {string} and password {string}")
    public void userEntersUsernameAndPassword(String username, String password) {
        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
        loginPage.clickLogin();
    }

    @Then("User redirected to the Home page")
    public void userRedirectedToTheHomePage() {

    }
}
