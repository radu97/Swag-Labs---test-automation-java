package com.epam.automation.test;

import com.epam.automation.driver.DriverSingleton;
import com.epam.automation.page.SaucedemoLoginPage;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.*;


// base test class containing setup and teardown methods
public class CommonConditions {

    protected WebDriver driver;        // WebDriver instance
    protected static final Logger logger = LoggerFactory.getLogger(CommonConditions.class);   // Logger instance
    protected SaucedemoLoginPage loginPage;        // Login page instance
    protected String browserName;          // This variable stores the browser name. This variable is used to log messages.

    @BeforeMethod(alwaysRun = true)        // runs once before any test methods in the class
    @Parameters({"browser"})         // Allows parameterized test execution. It specifies which browser will be used for testing.
    public void setUp(String browser) {        //saucedemo webpage is loaded and a start message is logged
        driver = DriverSingleton.getDriver(browser);
        loginPage = new SaucedemoLoginPage(driver);
        loginPage.openPage();
        browserName = browser;
        logger.info("{} tests started", browserName);
    }

    @AfterMethod(alwaysRun = true)       // runs once after all test methods in the class
    public void tearDown() {     //quits the WebDriver and logs a final message
        DriverSingleton.quitDriver();
        logger.info("{} driver quit", browserName);
    }
}
