package com.basesetup.playwright.helpers;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.options.SelectOption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Helper class for handling dropdown interactions in Playwright.
 */
public class DropdownHelper {
    private static final Logger logger = LoggerFactory.getLogger(DropdownHelper.class);

    /**
     * Selects an option from the dropdown by visible text.
     *
     * @param locator Playwright Locator for the dropdown
     * @param text    Visible text of the option to select
     */
    public void selectByText(Locator locator, String text) {
        try {
            logger.info("🔽 Selecting '{}' from dropdown {}", text, locator);
            locator.selectOption(text);
            logger.info("✅ Successfully selected '{}'", text);
        } catch (Exception e) {
            logger.error("❌ Error selecting '{}' from dropdown: {}", text, e.getMessage(), e);
        }
    }

    /**
     * Selects an option from the dropdown by its value attribute.
     *
     * @param locator Playwright Locator for the dropdown
     * @param value   Value attribute of the option to select
     */
    public void selectByValue(Locator locator, String value) {
        try {
            logger.info("🔽 Selecting value '{}' from dropdown {}", value, locator);
            locator.selectOption(new SelectOption().setValue(value));
            logger.info("✅ Successfully selected value '{}'", value);
        } catch (Exception e) {
            logger.error("❌ Error selecting value '{}' from dropdown: {}", value, e.getMessage(), e);
        }
    }

    /**
     * Selects an option from the dropdown by its index.
     *
     * @param locator Playwright Locator for the dropdown
     * @param index   Index of the option to select (starting from 0)
     */
    public void selectByIndex(Locator locator, int index) {
        try {
            logger.info("🔽 Selecting index '{}' from dropdown {}", index, locator);
            locator.selectOption(new SelectOption().setIndex(index));
            logger.info("✅ Successfully selected index '{}'", index);
        } catch (Exception e) {
            logger.error("❌ Error selecting index '{}' from dropdown: {}", index, e.getMessage(), e);
        }
    }

    /**
     * Retrieves the currently selected option from the dropdown.
     *
     * @param locator Playwright Locator for the dropdown
     * @return The visible text of the selected option
     */
    public String getSelectedOption(Locator locator) {
        try {
            String selected = locator.evaluate("el => el.options[el.selectedIndex].text").toString();
            logger.info("📌 Selected option: {}", selected);
            return selected;
        } catch (Exception e) {
            logger.error("❌ Error getting selected option: {}", e.getMessage(), e);
            return null;
        }
    }

    /**
     * Retrieves all available options from the dropdown.
     *
     * @param locator Playwright Locator for the dropdown
     * @return List of all options available in the dropdown
     */
    public List<String> getAllOptions(Locator locator) {
        try {
            List<String> options = (List<String>) locator.evaluateAll("options => options.map(option => option.textContent)");
            logger.info("📌 Dropdown options: {}", options);
            return options;
        } catch (Exception e) {
            logger.error("❌ Error retrieving dropdown options: {}", e.getMessage(), e);
            return null;
        }
    }

    /**
     * Checks if a specific option is available in the dropdown.
     *
     * @param locator Playwright Locator for the dropdown
     * @param option  The visible text of the option to check
     * @return true if the option is available, false otherwise
     */
    public boolean isOptionAvailable(Locator locator, String option) {
        try {
            List<String> options = getAllOptions(locator);
            boolean available = options != null && options.contains(option);
            logger.info("🔎 Checking if option '{}' is available: {}", option, available);
            return available;
        } catch (Exception e) {
            logger.error("❌ Error checking if option '{}' is available: {}", option, e.getMessage(), e);
            return false;
        }
    }
}
