package com.basesetup.playwright.helpers;

import com.microsoft.playwright.Locator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.junit.jupiter.api.Assertions;

/**
 * AssertionHelper provides reusable assertion methods for Playwright-based tests.
 * This ensures that test failures are logged properly and assertions are consistent.
 */
public class AssertionHelper {
    private static final Logger logger = LoggerFactory.getLogger(AssertionHelper.class);

    /**
     * Asserts that two values are equal.
     *
     * @param expected Expected value.
     * @param actual   Actual value.
     * @param message  Custom failure message.
     */
    public static void assertEquals(Object expected, Object actual, String message) {
        try {
            Assertions.assertEquals(expected, actual, message);
            logger.info("✅ Assertion Passed: Expected = {}, Actual = {}", expected, actual);
        } catch (AssertionError e) {
            logger.error("❌ Assertion Failed: Expected = {}, Actual = {}. {}", expected, actual, message);
            throw e;
        }
    }

    /**
     * Asserts that a condition is true.
     *
     * @param condition Boolean condition to verify.
     * @param message   Custom failure message.
     */
    public static void assertTrue(boolean condition, String message) {
        try {
            Assertions.assertTrue(condition, message);
            logger.info("✅ Assertion Passed: {}", message);
        } catch (AssertionError e) {
            logger.error("❌ Assertion Failed: {}", message);
            throw e;
        }
    }

    /**
     * Asserts that a condition is false.
     *
     * @param condition Boolean condition to verify.
     * @param message   Custom failure message.
     */
    public static void assertFalse(boolean condition, String message) {
        try {
            Assertions.assertFalse(condition, message);
            logger.info("✅ Assertion Passed: {}", message);
        } catch (AssertionError e) {
            logger.error("❌ Assertion Failed: {}", message);
            throw e;
        }
    }

    /**
     * Asserts that an element is visible on the page.
     *
     * @param locator  The Playwright Locator.
     * @param message  Custom failure message.
     */
    public static void assertElementVisible(Locator locator, String message) {
        try {
            Assertions.assertTrue(locator.isVisible(), message);
            logger.info("✅ Assertion Passed: Element is visible - {}", message);
        } catch (AssertionError e) {
            logger.error("❌ Assertion Failed: Element not visible - {}", message);
            throw e;
        }
    }

    /**
     * Asserts that an element is not visible on the page.
     *
     * @param locator  The Playwright Locator.
     * @param message  Custom failure message.
     */
    public static void assertElementNotVisible(Locator locator, String message) {
        try {
            Assertions.assertFalse(locator.isVisible(), message);
            logger.info("✅ Assertion Passed: Element is not visible - {}", message);
        } catch (AssertionError e) {
            logger.error("❌ Assertion Failed: Element is visible - {}", message);
            throw e;
        }
    }

    /**
     * Asserts that an element contains the expected text.
     *
     * @param locator   The Playwright Locator.
     * @param expected  Expected text.
     */
    public static void assertElementText(Locator locator, String expected) {
        String actualText = locator.textContent().trim();
        try {
            Assertions.assertEquals(expected, actualText, "Element text does not match.");
            logger.info("✅ Assertion Passed: Element text matches. Expected = '{}', Actual = '{}'", expected, actualText);
        } catch (AssertionError e) {
            logger.error("❌ Assertion Failed: Expected = '{}', Actual = '{}'", expected, actualText);
            throw e;
        }
    }

    /**
     * Asserts that an element contains the expected attribute value.
     *
     * @param locator      The Playwright Locator.
     * @param attribute    Attribute name (e.g., "href", "class").
     * @param expectedValue Expected attribute value.
     */
    public static void assertElementAttribute(Locator locator, String attribute, String expectedValue) {
        String actualValue = locator.getAttribute(attribute);
        try {
            Assertions.assertEquals(expectedValue, actualValue, "Attribute value does not match.");
            logger.info("✅ Assertion Passed: Attribute '{}' matches. Expected = '{}', Actual = '{}'", attribute, expectedValue, actualValue);
        } catch (AssertionError e) {
            logger.error("❌ Assertion Failed: Attribute '{}' Expected = '{}', Actual = '{}'", attribute, expectedValue, actualValue);
            throw e;
        }
    }

    /**
     * Asserts that a page title matches the expected value.
     *
     * @param actualTitle   Actual page title.
     * @param expectedTitle Expected page title.
     */
    public static void assertPageTitle(String actualTitle, String expectedTitle) {
        try {
            Assertions.assertEquals(expectedTitle, actualTitle, "Page title does not match.");
            logger.info("✅ Assertion Passed: Page title matches. Expected = '{}', Actual = '{}'", expectedTitle, actualTitle);
        } catch (AssertionError e) {
            logger.error("❌ Assertion Failed: Expected Title = '{}', Actual Title = '{}'", expectedTitle, actualTitle);
            throw e;
        }
    }

    /**
     * Asserts that an element is enabled.
     *
     * @param locator  The Playwright Locator.
     * @param message  Custom failure message.
     */
    public static void assertElementEnabled(Locator locator, String message) {
        try {
            Assertions.assertTrue(locator.isEnabled(), message);
            logger.info("✅ Assertion Passed: Element is enabled - {}", message);
        } catch (AssertionError e) {
            logger.error("❌ Assertion Failed: Element is disabled - {}", message);
            throw e;
        }
    }

    /**
     * Asserts that an element is disabled.
     *
     * @param locator  The Playwright Locator.
     * @param message  Custom failure message.
     */
    public static void assertElementDisabled(Locator locator, String message) {
        try {
            Assertions.assertFalse(locator.isEnabled(), message);
            logger.info("✅ Assertion Passed: Element is disabled - {}", message);
        } catch (AssertionError e) {
            logger.error("❌ Assertion Failed: Element is enabled - {}", message);
            throw e;
        }
    }
}
