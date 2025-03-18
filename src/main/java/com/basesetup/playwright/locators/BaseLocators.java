package com.basesetup.playwright.locators;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.function.Supplier;

public class BaseLocators {
    protected Page page;
    private static final Logger logger = LoggerFactory.getLogger(BaseLocators.class);
    private static final int MAX_RETRIES = 2;
    private static final Duration RETRY_DELAY = Duration.ofSeconds(1);

    public BaseLocators(Page page) {
        this.page = page;
    }

    // ✅ Generic retry wrapper
    private Locator retryLocator(String description, Runnable logAction, Supplier<Locator> locatorSupplier) {
        for (int attempt = 1; attempt <= MAX_RETRIES; attempt++) {
            try {
                logAction.run();
                Locator locator = locatorSupplier.get();
                if (locator.count() > 0) return locator;
                throw new RuntimeException("Element not found: " + description);
            } catch (Exception e) {
                logger.warn("⚠️ Attempt {}/{} failed for {} | Error: {}", attempt, MAX_RETRIES, description, e.getMessage());
                if (attempt == MAX_RETRIES) {
                    logger.error("❌ Final failure: Unable to locate {}", description);
                    return null; // Optional: Return null instead of throwing
                }
                try {
                    Thread.sleep(RETRY_DELAY.toMillis());
                } catch (InterruptedException ignored) {}
            }
        }
        return null;  // Should never reach here
    }

    // ✅ Find element by ID
    public Locator byId(String id) {
        return retryLocator("ID: #" + id,
                () -> logger.info("🔍 Locating element by ID: #{}", id),
                () -> page.locator("#" + id)
        );
    }

    // ✅ Find element by CSS Selector
    public Locator byCss(String selector) {
        return retryLocator("CSS Selector: " + selector,
                () -> logger.info("🔍 Locating element by CSS Selector: {}", selector),
                () -> page.locator(selector)
        );
    }

    // ✅ Find element by XPath
    public Locator byXpath(String xpath) {
        return retryLocator("XPath: " + xpath,
                () -> logger.info("🔍 Locating element by XPath: {}", xpath),
                () -> page.locator("xpath=" + xpath)
        );
    }

    // ✅ Find element by Text
    public Locator byText(String text) {
        return retryLocator("Text: " + text,
                () -> logger.info("🔍 Locating element by Text: {}", text),
                () -> page.getByText(text)
        );
    }

    // ✅ Find element by Role (with optional attributes)
    public Locator byRole(String role, String name) {
        return retryLocator("Role: " + role + " (Name: " + name + ")",
                () -> logger.info("🔍 Locating element by Role: {} with name: {}", role, name),
                () -> {
                    Page.GetByRoleOptions options = new Page.GetByRoleOptions();
                    if (name != null && !name.isEmpty()) options.setName(name);
                    return page.getByRole(AriaRole.valueOf(role.toUpperCase()), options);
                }
        );
    }

    // ✅ Find element by Placeholder
    public Locator byPlaceholder(String placeholder) {
        return retryLocator("Placeholder: " + placeholder,
                () -> logger.info("🔍 Locating element by Placeholder: {}", placeholder),
                () -> page.getByPlaceholder(placeholder)
        );
    }

    // ✅ Find element by Test ID
    public Locator byTestId(String testId) {
        return retryLocator("Test ID: " + testId,
                () -> logger.info("🔍 Locating element by Test ID: {}", testId),
                () -> page.getByTestId(testId)
        );
    }

    // ✅ Wait for element with proper logging
    public Locator waitForLocator(String selector, int timeoutMs) {
        try {
            logger.info("⏳ Waiting for locator: {} (Timeout: {}ms)", selector, timeoutMs);
            page.waitForSelector(selector, new Page.WaitForSelectorOptions().setTimeout(timeoutMs));
            return page.locator(selector);  // Convert ElementHandle to Locator
        } catch (Exception e) {
            logger.error("❌ Locator {} not found within timeout: {}", selector, e.getMessage());
            return null;
        }
    }

}
