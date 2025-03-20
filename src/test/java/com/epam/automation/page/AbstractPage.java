package com.epam.automation.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

// abstract page class for common properties
public abstract class AbstractPage
{
    protected WebDriver driver;

    protected final int WAIT_TIMEOUT_SECONDS = 5;
    WebDriverWait wait;
    protected AbstractPage(WebDriver driver)
    {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS));  // initializes WebDriverWait with a timeout (explicit wait)
    }
}
