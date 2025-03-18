package com.basesetup.playwright.helpers;

import com.microsoft.playwright.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Helper class for browser navigation actions.
 */
public class NavigationHelper {
    private final Page page;
    private static final Logger logger = LoggerFactory.getLogger(NavigationHelper.class);

    public NavigationHelper(Page page) {
        this.page = page;
    }

    /**
     * Navigates to the specified URL.
     *
     * @param url The URL to visit.
     */
    public void goTo(String url) {
        try {
            logger.info("🌍 Navigating to {}", url);
            page.navigate(url);
        } catch (Exception e) {
            logger.error("❌ Failed to navigate to {}: {}", url, e.getMessage(), e);
        }
    }

    /**
     * Refreshes the current page.
     */
    public void refresh() {
        try {
            logger.info("🔄 Refreshing the page");
            page.reload();
        } catch (Exception e) {
            logger.error("❌ Failed to refresh the page: {}", e.getMessage(), e);
        }
    }

    /**
     * Goes back in browser history.
     */
    public void goBack() {
        try {
            logger.info("⬅️ Going back in browser history");
            page.goBack();
        } catch (Exception e) {
            logger.error("❌ Failed to go back in history: {}", e.getMessage(), e);
        }
    }

    /**
     * Goes forward in browser history.
     */
    public void goForward() {
        try {
            logger.info("➡️ Going forward in browser history");
            page.goForward();
        } catch (Exception e) {
            logger.error("❌ Failed to go forward in history: {}", e.getMessage(), e);
        }
    }

    /**
     * Gets the current URL of the page.
     *
     * @return The current page URL.
     */
    public String getCurrentUrl() {
        try {
            String currentUrl = page.url();
            logger.info("🔗 Current URL: {}", currentUrl);
            return currentUrl;
        } catch (Exception e) {
            logger.error("❌ Failed to get current URL: {}", e.getMessage(), e);
            return "";
        }
    }
}
