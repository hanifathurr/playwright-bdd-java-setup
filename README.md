# Playwright Automation Framework

## 📌 Overview
This repository contains a **scalable and maintainable Playwright automation testing framework** built using **Java, Playwright, and Cucumber (BDD)**. It follows best practices with Page Object Model (POM), helper classes, and environment-based configurations.

## 🚀 Features
- **Playwright Java** for modern browser automation
- **Cucumber BDD** for readable and structured test scenarios
- **Page Object Model (POM)** for better maintainability
- **Thread-safe browser and context management** using `PlaywrightManager`
- **Configurable environment support** using `PropertyFileReader`
- **Helper classes** for common Playwright actions and assertions
- **Automatic screenshot capture** on test failure
- **Parallel execution support**

## 🛠 Setup Instructions

### 1️⃣ Prerequisites
Ensure you have the following installed:
- **Java 11+** ([Download](https://adoptopenjdk.net/))
- **Maven** ([Download](https://maven.apache.org/))
- **Node.js** (for Playwright installation) ([Download](https://nodejs.org/))
- **Playwright Browsers**
  ```sh
  npx playwright install
  ```

### 2️⃣ Clone the Repository
```sh
git clone https://github.com/your-repo/playwright-java-automation.git
cd playwright-java-automation
```

### 3️⃣ Install Dependencies
```sh
mvn clean install
```

### 4️⃣ Configure Environment
The framework supports multiple environments (e.g., `dev`, `qa`, `prod`).

- **Configuration files are located in:**
  ```sh
  src/main/java/com/basesetup/playwright/utils/config/config-{env}.properties
  ```
- Set environment variable before running tests:
  ```sh
  export ENV=dev  # macOS/Linux
  set ENV=dev     # Windows
  ```
  Or pass it as a Maven parameter:
  ```sh
  mvn test -Denv=dev
  ```

## 📜 Framework Structure
```
├── src/main/java/com/basesetup/playwright
│   ├── hooks/                  # Cucumber Hooks (Setup & Teardown)
│   ├── pages/                  # Page Object Model classes
│   ├── steps/                  # Step Definitions for Cucumber
│   ├── utils/
│   │   ├── config/             # PropertyFileReader for configuration management
│   │   ├── helpers/            # Common Playwright actions
│   │   ├── managers/           # PlaywrightManager for browser lifecycle
│   ├── tests/                  # Test runner classes
├── src/test/resources/features # Cucumber feature files
└── README.md                   # Documentation
```

## 🚦 Running Tests
### Run all tests
```sh
mvn test
```

### Run specific feature file
```sh
mvn test -Dcucumber.options="src/test/resources/features/login.feature"
```

### Run tests in headless mode
```sh
mvn test -Dheadless=true
```

## 📸 Capturing Screenshots on Failure
Screenshots are **automatically captured** if a test fails and attached to the Cucumber report.

## 🛠 Enhancements & Customization
- Modify `PropertyFileReader.java` to add custom configurations.
- Extend `PlaywrightManager.java` to handle multiple browser sessions.
- Create new helper methods in Helpers package.

---
**Happy Testing! 🚀🎭**

