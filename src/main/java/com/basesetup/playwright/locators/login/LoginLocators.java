package com.basesetup.playwright.locators.login;

import com.basesetup.playwright.locators.BaseLocators;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class LoginLocators extends BaseLocators {

    public LoginLocators(Page page) {
        super(page);
    }

    public Locator usernameInput() {
        return byPlaceholder("Username");
    }

    public Locator passwordInput() {
        return byPlaceholder("Password");
    }

    public Locator loginButton() {
        return byId("login-button");
    }

    public Locator errorMessageByText(String message) {
        return byText(message);
    }
}

