# SauceDemo Automation Testing

This project is a Selenium-based test automation framework for testing login functionality on the SauceDemo website. It utilizes TestNG for parallel test execution, WebDriverManager for managing web browsers (Chrome and Firefox), and SLF4J for logging.

## 1. Used Technologies

- **Java JDK** 17.0.1
- **Selenium WebDriver** 4.29.0
- **WebDriverManager** 5.9.3
- **TestNG** 7.11.0
- **JUnit** 4.13.2
- **SLF4J Logging** 2.0.17
- **AssertJ** 3.27.3

## 2. Project Structure

```
test/java
├── com.epam.automation
│   ├── driver
│   │   ├── DriverSingleton.java    # Singleton WebDriver management with ThreadLocal to ensure WebDriver is thread-safe in parallel test execution
│   ├── page
│   │   ├── AbstractPage.java       # Base class for page objects
│   │   ├── LoginException.java     # Custom exception for login failures
│   │   ├── SaucedemoLoginPage.java # Page Object Model for SauceDemo login
│   ├── test
│   │   ├── UserAccessTests.java    # Test cases for login functionality
│   │   ├── CommonConditions.java   # Base test setup and teardown
resources
├── logback.xml   # Logging configuration
├── testng.xml    # Parallel test execution setup
```

## 3. Installation & Setup

### Prerequisites:

- Install **Java JDK 17.0.1**
- Install **IntelliJ IDEA**
- Install **Maven**
- Set up environment variables for `JAVA_HOME` and `MAVEN_HOME`

### Steps:

1. Clone the repository:
   ```sh
   git clone https://github.com/radu97/Swag-Labs---test-automation-java
   ```
2. Import the project in IntelliJ.

## 4. Running Tests

Right-click on `testng.xml` file → Click **Run**

## 5. Test Cases

### **UC-1: Login with Empty Credentials**

- Enter empty username & password
- Validate error message: **"Username is required"**

### **UC-2: Login with Missing Password**

- Enter username but no password
- Validate error message: **"Password is required"**

### **UC-3: Login with Valid Credentials**

- Enter valid username & password
- Validate successful login by checking the page title "Swag Labs"

There are 14 tests in total: one UC-1 test, one UC-2 test and five UC-3 tests (5 users), all running in Chrome and Firefox browsers (7*2=14 tests).
Two tests fail (one in each browser) because the user is locked out. The remaining twelve tests are successful (six in each browser).
The testng.xml file enables parallel test execution.

## 6. WebDriver Singleton with ThreadLocal
The Singleton WebDriver instance uses **ThreadLocal**. This ensures that each thread gets its own instance of WebDriver while maintaining a singleton-like structure.

## 7. Login class with Page Object Model
The Page Object Model (POM) is a design pattern used in Selenium automation testing to improve code quality by creating separate Java classes for each page of a web application.
PageFactory is used in POM to provide an easy way to initialize web elements with the @FindBy annotation, reducing the need for `driver.findElement`.

## 8. Logging Configuration

Logging is managed using **SLF4J**. Logs are stored in the console and in the `test.log` file.

### Logging format:
- `%d{yyyy-MM-dd HH:mm:ss}` → Timestamp (e.g., 2025-03-20 14:30:45).
- `[%thread]` → Thread name (useful in multi-threaded applications).
- `%-5level` → Log level (e.g., INFO, ERROR, DEBUG, etc.).
- `%logger{36}` → Logger name (limited to 36 characters for readability).
- `%msg%n` → Log message followed by a newline (`%n`).

Sets the default logging level to INFO. Logs below INFO (DEBUG, TRACE) will NOT be displayed. Logs INFO, WARN, ERROR, FATAL will be shown.
Logs are configured in `logback.xml` file.

### Example logs:

```
2025-03-19 18:26:43 [TestNG-tests-2] INFO  c.e.automation.test.CommonConditions - firefox driver quit
2025-03-19 18:26:46 [TestNG-tests-2] INFO  c.e.automation.test.CommonConditions - firefox tests started
2025-03-19 18:26:47 [TestNG-tests-2] INFO  c.e.automation.test.CommonConditions - firefox UC-3 test successful: login with correct credentials (problem_user)
2025-03-19 18:26:47 [TestNG-tests-2] INFO  c.e.automation.test.CommonConditions - firefox driver quit
```
