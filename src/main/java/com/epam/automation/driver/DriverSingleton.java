package com.epam.automation.driver;

import com.epam.automation.service.ConfigReader;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DriverSingleton {

    public static final String UNSUPPORTED_BROWSER = "Unsupported browser: ";
    public static final String ERROR_INITIALIZING_WEB_DRIVER = "Error initializing WebDriver: {}";
    public static final String FAILED_TO_START_WEB_DRIVER = "Failed to start WebDriver";

    //    private static WebDriver driver;
    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();      // using ThreadLocal to ensure WebDriver is thread-safe in parallel test execution
    protected static final Logger logger = LoggerFactory.getLogger(DriverSingleton.class);

    public static WebDriver getDriver(String browser) {
        if (driver.get() == null) {
            try {
                if (browser.equalsIgnoreCase(ConfigReader.getProperty("chrome.browser"))) {
                WebDriverManager.chromedriver().setup();            // setting up ChromeDriver using WebDriverManager
                    driver.set(new ChromeDriver());                // creating new instance of ChromeDriver
                } else if (browser.equalsIgnoreCase(ConfigReader.getProperty("firefox.browser"))) {
                WebDriverManager.firefoxdriver().setup();   // setting up FirefoxDriver using WebDriverManager
                    driver.set(new FirefoxDriver());        // creating new instance of FirefoxDriver
                } else {
                    throw new IllegalArgumentException(UNSUPPORTED_BROWSER + browser);  // throw exception if an invalid browser is passed
                }
                driver.get().manage().window().maximize();   // maximizing the browser window
            }
            catch(Exception e){
                logger.error(ERROR_INITIALIZING_WEB_DRIVER, e.getMessage());   // logging error message
                throw new RuntimeException(FAILED_TO_START_WEB_DRIVER, e);
            }
        }
        return driver.get();   // returning the WebDriver instance for the current thread
    }

    public static void quitDriver() {
        if (driver.get() != null) {
            driver.get().quit();     // quit the browser
            driver.remove();         // remove the WebDriver instance from ThreadLocal
        }
    }
}
