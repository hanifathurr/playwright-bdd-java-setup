package com.basesetup.playwright.utils.managers;

import com.basesetup.playwright.utils.config.PropertyFileReader;
import com.microsoft.playwright.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Objects;

public class PlaywrightManager {
    private static final Logger logger = LoggerFactory.getLogger(PlaywrightManager.class);

    private static Playwright playwright;
    private static Browser browser;
    private static final ThreadLocal<BrowserContext> contextThreadLocal = new ThreadLocal<>();
    private static final ThreadLocal<Page> pageThreadLocal = new ThreadLocal<>();

    /**
     * Initializes Playwright and launches the browser.
     */
    public static synchronized void initBrowser() {
        if (playwright == null) {
            try {
                playwright = Playwright.create();
                String browserType = PropertyFileReader.getProperty("browser", "chromium");
                boolean headless = PropertyFileReader.getBooleanProperty("headless", true);

                browser = launchBrowser(browserType, headless);
                logger.info("🎭 Playwright Initialized | Browser: {} | Headless: {}", browserType, headless);
            } catch (Exception e) {
                logger.error("❌ Failed to initialize Playwright: {}", e.getMessage(), e);
                throw new RuntimeException("Playwright initialization failed", e);
            }
        }
    }

    /**
     * Launches the specified browser type with given options.
     */
    private static Browser launchBrowser(String browserType, boolean headless) {
        BrowserType.LaunchOptions options = new BrowserType.LaunchOptions()
                .setHeadless(headless)
                .setSlowMo(PropertyFileReader.getIntProperty("slowMo", 0))
                .setArgs(Arrays.asList("--disable-gpu", "--no-sandbox"));;

        return switch (browserType.toLowerCase()) {
            case "chromium" -> playwright.chromium().launch(options);
            case "firefox" -> playwright.firefox().launch(options);
            case "webkit" -> playwright.webkit().launch(options);
            default -> throw new IllegalArgumentException("Unsupported browser: " + browserType);
        };
    }

    /**
     * Creates and returns a new BrowserContext.
     */
    public static BrowserContext createContext() {
        if (browser == null) {
            logger.warn("⚠️ Browser not initialized. Initializing now...");
            initBrowser();
        }

        BrowserContext context = browser.newContext();
        contextThreadLocal.set(context);
        logger.info("🌐 New BrowserContext created for thread: {}", Thread.currentThread().getId());

        return context;
    }

    /**
     * Creates and returns a new Page.
     */
    public static Page createPage() {
        BrowserContext context = getContext();
        if (context == null) {
            context = createContext();
        }

        Page page = context.newPage();
        page.setDefaultTimeout(PropertyFileReader.getIntProperty("defaultTimeout", 30000));  // Set default timeout
        pageThreadLocal.set(page);

        logger.info("📄 New Page created for thread: {}", Thread.currentThread().getId());
        return page;
    }

    /**
     * Retrieves the current BrowserContext for the thread.
     */
    public static BrowserContext getContext() {
        return contextThreadLocal.get();
    }

    /**
     * Retrieves the current Page for the thread.
     */
    public static Page getPage() {
        Page page = pageThreadLocal.get();
        if (page == null) {
            logger.warn("⚠️ No active Page found for thread {}. Creating a new one.", Thread.currentThread().getId());
            return createPage();
        }
        return page;
    }

    /**
     * Closes the Page and BrowserContext for the current thread.
     */
    public static void closeContext() {
        try {
            Page page = pageThreadLocal.get();
            if (page != null) {
                page.close();
                pageThreadLocal.remove();
                logger.info("❌ Page closed for thread: {}", Thread.currentThread().getId());
            }

            BrowserContext context = contextThreadLocal.get();
            if (context != null) {
                context.close();
                contextThreadLocal.remove();
                logger.info("❌ BrowserContext closed for thread: {}", Thread.currentThread().getId());
            }
        } catch (Exception e) {
            logger.error("⚠️ Error while closing context: {}", e.getMessage(), e);
        }
    }

    /**
     * Closes the browser instance and Playwright.
     */
    public static synchronized void closeBrowser() {
        closeContext();  // Ensure thread-local resources are released

        try {
            if (browser != null) {
                browser.close();
                logger.info("❌ Browser closed");
                browser = null;
            }
            if (playwright != null) {
                playwright.close();
                logger.info("❌ Playwright instance closed");
                playwright = null;
            }
        } catch (Exception e) {
            logger.error("⚠️ Error while closing Playwright: {}", e.getMessage(), e);
        }
    }
}
