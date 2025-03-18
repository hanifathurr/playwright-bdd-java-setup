package com.basesetup.playwright.helpers;

import com.microsoft.playwright.Locator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Helper class for handling checkbox interactions in Playwright.
 */
public class CheckboxHelper {
    private static final Logger logger = LoggerFactory.getLogger(CheckboxHelper.class);

    /**
     * Checks a checkbox if it is not already checked.
     *
     * @param locator Playwright Locator for the checkbox
     */
    public void check(Locator locator) {
        try {
            if (!locator.isChecked()) {
                locator.check();
                logger.info("✅ Checked the checkbox: {}", locator);
            } else {
                logger.info("ℹ️ Checkbox is already checked: {}", locator);
            }
        } catch (Exception e) {
            logger.error("❌ Error checking checkbox: {}", e.getMessage(), e);
        }
    }

    /**
     * Unchecks a checkbox if it is not already unchecked.
     *
     * @param locator Playwright Locator for the checkbox
     */
    public void uncheck(Locator locator) {
        try {
            if (locator.isChecked()) {
                locator.uncheck();
                logger.info("✅ Unchecked the checkbox: {}", locator);
            } else {
                logger.info("ℹ️ Checkbox is already unchecked: {}", locator);
            }
        } catch (Exception e) {
            logger.error("❌ Error unchecking checkbox: {}", e.getMessage(), e);
        }
    }

    /**
     * Toggles a checkbox state (check if unchecked, uncheck if checked).
     *
     * @param locator Playwright Locator for the checkbox
     */
    public void toggleCheckbox(Locator locator) {
        try {
            locator.click();
            logger.info("🔄 Toggled the checkbox: {}", locator);
        } catch (Exception e) {
            logger.error("❌ Error toggling checkbox: {}", e.getMessage(), e);
        }
    }

    /**
     * Ensures that a checkbox is checked.
     * If it is not checked, attempts to check it.
     *
     * @param locator Playwright Locator for the checkbox
     */
    public void checkWithVerification(Locator locator) {
        try {
            if (!locator.isChecked()) {
                locator.check();
                if (locator.isChecked()) {
                    logger.info("✅ Checkbox successfully checked: {}", locator);
                } else {
                    logger.warn("⚠️ Failed to check the checkbox: {}", locator);
                }
            }
        } catch (Exception e) {
            logger.error("❌ Error ensuring checkbox is checked: {}", e.getMessage(), e);
        }
    }

    /**
     * Ensures that a checkbox is unchecked.
     * If it is checked, attempts to uncheck it.
     *
     * @param locator Playwright Locator for the checkbox
     */
    public void uncheckWithVerification(Locator locator) {
        try {
            if (locator.isChecked()) {
                locator.uncheck();
                if (!locator.isChecked()) {
                    logger.info("✅ Checkbox successfully unchecked: {}", locator);
                } else {
                    logger.warn("⚠️ Failed to uncheck the checkbox: {}", locator);
                }
            }
        } catch (Exception e) {
            logger.error("❌ Error ensuring checkbox is unchecked: {}", e.getMessage(), e);
        }
    }

    /**
     * Checks if a checkbox is checked.
     *
     * @param locator Playwright Locator for the checkbox
     * @return true if checked, false otherwise
     */
    public boolean isChecked(Locator locator) {
        boolean checked = false;
        try {
            checked = locator.isChecked();
            logger.info("📌 Checkbox checked status [{}]: {}", locator, checked);
        } catch (Exception e) {
            logger.error("❌ Error checking checkbox status: {}", e.getMessage(), e);
        }
        return checked;
    }
}
