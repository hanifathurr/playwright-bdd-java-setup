package com.basesetup.playwright.hooks;

import com.basesetup.playwright.utils.managers.PlaywrightManager;
import com.basesetup.playwright.utils.config.PropertyFileReader;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.PlaywrightException;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Hooks {
    private static final Logger logger = LoggerFactory.getLogger(Hooks.class);

    @Before
    public void setup(Scenario scenario) {
        logger.info("🚀 Starting Scenario: {}", scenario.getName());

        PlaywrightManager.initBrowser();
        Page page = PlaywrightManager.createPage();

        String baseUrl = PropertyFileReader.getProperty("baseUrl", "https://example.com");
        if (baseUrl == null || baseUrl.isEmpty()) {
            logger.warn("⚠️ Base URL is not set in the configuration.");
        } else {
            logger.info("🌍 Navigating to base URL: {}", baseUrl);
            try {
                page.navigate(baseUrl);
            } catch (PlaywrightException e) {
                logger.error("❌ Failed to navigate to Base URL: {} | Error: {}", baseUrl, e.getMessage());
            }
        }
    }

    @After
    public void tearDown(Scenario scenario) {
        Page page = PlaywrightManager.getPage();

        if (scenario.isFailed()) {
            logger.error("❌ Scenario Failed: {}", scenario.getName());
            takeScreenshot(scenario, page);
        } else {
            logger.info("✅ Scenario Passed: {}", scenario.getName());
        }

        PlaywrightManager.closeContext(); // Close only thread-local Page & Context
        logger.info("🛑 Closed browser context after scenario: {}", scenario.getName());
    }

    /**
     * Takes a screenshot if a scenario fails.
     */
    private void takeScreenshot(Scenario scenario, Page page) {
        if (page == null) {
            logger.warn("⚠️ No active page found to take a screenshot.");
            return;
        }

        try {
            logger.info("📸 Capturing screenshot on failure. Current URL: {}", page.url());
            byte[] screenshot = page.screenshot();
            scenario.attach(screenshot, "image/png", "Failure Screenshot");
            logger.info("✅ Screenshot captured for failed scenario: {}", scenario.getName());
        } catch (PlaywrightException e) {
            logger.error("❌ Failed to capture screenshot: {}", e.getMessage());
        }
    }
}
