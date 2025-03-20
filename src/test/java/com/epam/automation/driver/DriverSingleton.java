package com.epam.automation.driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class DriverSingleton {

//    private static WebDriver driver;
private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();      // using ThreadLocal to ensure WebDriver is thread-safe in parallel test execution
    public static WebDriver getDriver(String browser) {
        if (driver.get() == null) {
            try {
                if (browser.equalsIgnoreCase("chrome")) {
                WebDriverManager.chromedriver().setup();            // setting up ChromeDriver using WebDriverManager
                    driver.set(new ChromeDriver());                // creating new instance of ChromeDriver
                } else if (browser.equalsIgnoreCase("firefox")) {
                WebDriverManager.firefoxdriver().setup();   // setting up FirefoxDriver using WebDriverManager
                    driver.set(new FirefoxDriver());        // creating new instance of FirefoxDriver
                } else {
                    throw new IllegalArgumentException("Unsupported browser: " + browser);  // throw exception if an invalid browser is passed
                }
                driver.get().manage().window().maximize();   // maximizing the browser window
            }
            catch(Exception e){
                System.err.println("Error initializing WebDriver: " + e.getMessage());   // logging error message
                throw new RuntimeException("Failed to start WebDriver", e);
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
