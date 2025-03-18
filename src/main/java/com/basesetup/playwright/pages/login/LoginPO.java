package com.basesetup.playwright.pages.login;

import com.basesetup.playwright.helpers.GeneralHelper;
import com.basesetup.playwright.locators.login.LoginLocators;
import com.microsoft.playwright.Page;

public class LoginPO {
    private final LoginLocators loginLocators;
    private final GeneralHelper playwright;

    public LoginPO(Page page) {
        this.loginLocators = new LoginLocators(page);
        this.playwright = new GeneralHelper(page);
    }

    public void enterUsername(String username) {
        playwright.fillInput(loginLocators.usernameInput(),username);
    }

    public void enterPassword(String password) {
        playwright.fillInput(loginLocators.passwordInput(),password);
    }

    public void clickLogin() {
        playwright.click(loginLocators.loginButton());
    }

}
