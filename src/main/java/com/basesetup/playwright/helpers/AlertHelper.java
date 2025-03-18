package com.basesetup.playwright.helpers;

import com.microsoft.playwright.Dialog;
import com.microsoft.playwright.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicReference;

/**
 * Helper class to handle browser alerts (JavaScript dialogs) in Playwright.
 */
public class AlertHelper {
    private final Page page;
    private static final Logger logger = LoggerFactory.getLogger(AlertHelper.class);

    /**
     * Constructor to initialize AlertHelper.
     *
     * @param page Playwright Page instance
     */
    public AlertHelper(Page page) {
        this.page = page;
    }

    /**
     * Handles an alert by either accepting or dismissing it.
     *
     * @param accept true to accept the alert, false to dismiss it
     */
    public void handleAlert(boolean accept) {
        try {
            page.onDialog(dialog -> {
                logger.info("⚠ Alert detected: {}", dialog.message());
                if (accept) {
                    dialog.accept();
                    logger.info("✅ Alert accepted");
                } else {
                    dialog.dismiss();
                    logger.info("❌ Alert dismissed");
                }
            });
        } catch (Exception e) {
            logger.error("❌ Error handling alert: {}", e.getMessage(), e);
        }
    }

    /**
     * Retrieves the text message from an alert.
     *
     * @return the alert message text, or null if no alert is present
     */
    public String getAlertText() {
        AtomicReference<String> alertText = new AtomicReference<>(null);
        try {
            page.onDialog(dialog -> {
                alertText.set(dialog.message());
                logger.info("📢 Alert message: {}", alertText.get());
            });
        } catch (Exception e) {
            logger.error("❌ Error retrieving alert text: {}", e.getMessage(), e);
        }
        return alertText.get();
    }

    /**
     * Accepts an alert and provides input text (for prompt alerts).
     *
     * @param inputText text to input in the alert (for prompt dialogs)
     */
    public void acceptAlertWithText(String inputText) {
        try {
            page.onDialog(dialog -> {
                logger.info("⚠ Prompt alert detected: {} | Inputting text: {}", dialog.message(), inputText);
                dialog.accept(inputText);
                logger.info("✅ Alert accepted with text: {}", inputText);
            });
        } catch (Exception e) {
            logger.error("❌ Error accepting alert with text: {}", e.getMessage(), e);
        }
    }

    /**
     * Checks if an alert is present by setting up a listener.
     *
     * @return true if an alert appears, false otherwise
     */
    public boolean isAlertPresent() {
        AtomicReference<Boolean> alertPresent = new AtomicReference<>(false);
        try {
            page.onDialog(dialog -> {
                logger.info("⚠ Alert detected: {}", dialog.message());
                alertPresent.set(true);
            });
        } catch (Exception e) {
            logger.error("❌ Error checking alert presence: {}", e.getMessage(), e);
        }
        return alertPresent.get();
    }
}
