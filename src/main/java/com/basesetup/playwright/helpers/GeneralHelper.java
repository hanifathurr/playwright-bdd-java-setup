package com.basesetup.playwright.helpers;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * General helper class for common Playwright interactions.
 */
public class GeneralHelper {
    private final Page page;
    private static final Logger logger = LoggerFactory.getLogger(GeneralHelper.class);

    public GeneralHelper(Page page) {
        this.page = page;
    }

    /**
     * Clicks on the given element.
     *
     * @param locator The element to click.
     */
    public void click(Locator locator) {
        try {
            logger.info("🖱️ Clicking element: {}", locator);
            locator.click();
        } catch (Exception e) {
            logger.error("❌ Failed to click element {}: {}", locator, e.getMessage(), e);
        }
    }

    /**
     * Types text into an input field.
     *
     * @param locator The input field.
     * @param text    The text to type.
     */
    public void fillInput(Locator locator, String text) {
        try {
            logger.info("⌨️ Typing '{}' into {}", text, locator);
            locator.fill(text);
        } catch (Exception e) {
            logger.error("❌ Failed to type into {}: {}", locator, e.getMessage(), e);
        }
    }

    /**
     * Retrieves text from an element.
     *
     * @param locator The element.
     * @return The text content.
     */
    public String getText(Locator locator) {
        try {
            logger.info("📋 Getting text from {}", locator);
            return locator.textContent();
        } catch (Exception e) {
            logger.error("❌ Failed to get text from {}: {}", locator, e.getMessage(), e);
            return "";
        }
    }

    /**
     * Clears an input field.
     *
     * @param locator The input field.
     */
    public void clearText(Locator locator) {
        try {
            logger.info("🧹 Clearing text from {}", locator);
            locator.clear();
        } catch (Exception e) {
            logger.error("❌ Failed to clear text in {}: {}", locator, e.getMessage(), e);
        }
    }

    /**
     * Checks if an element is visible on the page.
     *
     * @param locator The element.
     * @return true if visible, false otherwise.
     */
    public boolean isVisible(Locator locator) {
        try {
            boolean visible = locator.isVisible();
            logger.info("👀 Checking visibility of {}: {}", locator, visible);
            return visible;
        } catch (Exception e) {
            logger.error("❌ Failed to check visibility of {}: {}", locator, e.getMessage(), e);
            return false;
        }
    }

    /**
     * Hovers over an element.
     *
     * @param locator The element.
     */
    public void hover(Locator locator) {
        try {
            logger.info("🎯 Hovering over {}", locator);
            locator.hover();
        } catch (Exception e) {
            logger.error("❌ Failed to hover over {}: {}", locator, e.getMessage(), e);
        }
    }

    /**
     * Waits for an element to be visible.
     *
     * @param locator The element to wait for.
     * @param timeout Timeout in milliseconds.
     */
    public void waitForVisibility(Locator locator, int timeout) {
        try {
            logger.info("⏳ Waiting for visibility of {} for {} ms", locator, timeout);
            locator.waitFor(new Locator.WaitForOptions().setTimeout(timeout));
        } catch (Exception e) {
            logger.error("❌ Failed to wait for visibility of {}: {}", locator, e.getMessage(), e);
        }
    }

    /**
     * Double clicks on an element.
     *
     * @param locator The element.
     */
    public void doubleClick(Locator locator) {
        try {
            logger.info("🖱️🖱️ Double-clicking {}", locator);
            locator.dblclick();
        } catch (Exception e) {
            logger.error("❌ Failed to double-click {}: {}", locator, e.getMessage(), e);
        }
    }

    /**
     * Checks if an element contains specific text.
     *
     * @param locator The element.
     * @param text    The expected text.
     * @return true if element contains the text, false otherwise.
     */
    public boolean containsText(Locator locator, String text) {
        try {
            String elementText = locator.textContent();
            boolean contains = elementText != null && elementText.contains(text);
            logger.info("🔍 Checking if '{}' contains '{}': {}", elementText, text, contains);
            return contains;
        } catch (Exception e) {
            logger.error("❌ Failed to check text in {}: {}", locator, e.getMessage(), e);
            return false;
        }
    }
}
