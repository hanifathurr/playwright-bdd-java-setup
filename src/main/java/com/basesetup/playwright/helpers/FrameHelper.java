package com.basesetup.playwright.helpers;

import com.microsoft.playwright.FrameLocator;
import com.microsoft.playwright.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Helper class for handling iframe interactions in Playwright.
 */
public class FrameHelper {
    private final Page page;
    private static final Logger logger = LoggerFactory.getLogger(FrameHelper.class);

    public FrameHelper(Page page) {
        this.page = page;
    }

    /**
     * Switches to a frame using its name or ID.
     *
     * @param nameOrId The name or ID of the frame.
     * @return The FrameLocator if found, null otherwise.
     */
    public FrameLocator switchToFrame(String nameOrId) {
        try {
            logger.info("🔄 Switching to frame by name/ID: {}", nameOrId);
            return page.frameLocator("iframe[name='" + nameOrId + "'], iframe#" + nameOrId);
        } catch (Exception e) {
            logger.error("❌ Failed to switch to frame '{}': {}", nameOrId, e.getMessage(), e);
            return null;
        }
    }

    /**
     * Switches to a frame using its index position.
     *
     * @param index The index of the frame (starting from 0).
     * @return The FrameLocator if found, null otherwise.
     */
    public FrameLocator switchToFrameByIndex(int index) {
        try {
            logger.info("🔄 Switching to frame by index: {}", index);
            return page.frameLocator("iframe").nth(index);
        } catch (Exception e) {
            logger.error("❌ Failed to switch to frame index {}: {}", index, e.getMessage(), e);
            return null;
        }
    }

    /**
     * Switches to a frame using its CSS selector.
     *
     * @param selector The CSS selector for the frame.
     * @return The FrameLocator if found, null otherwise.
     */
    public FrameLocator switchToFrameBySelector(String selector) {
        try {
            logger.info("🔄 Switching to frame by selector: {}", selector);
            return page.frameLocator(selector);
        } catch (Exception e) {
            logger.error("❌ Failed to switch to frame '{}': {}", selector, e.getMessage(), e);
            return null;
        }
    }

    /**
     * Switches back to the main content (default context).
     */
    public void switchToDefaultContent() {
        try {
            logger.info("🔄 Switching back to default content.");
            page.bringToFront(); // Ensures page is active
        } catch (Exception e) {
            logger.error("❌ Failed to switch to default content: {}", e.getMessage(), e);
        }
    }
}
