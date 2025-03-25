package com.epam.automation.page;

import com.epam.automation.service.ConfigReader;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

// The Page Object Model (POM) is a design pattern used in Selenium automation testing to improve code maintainability, reusability, and readability.
// It helps organize web elements and actions logically by creating separate Java classes for each page of a web application.
public class SaucedemoLoginPage extends AbstractPage {

    // WebElements using @FindBy annotation
    @FindBy(css="#user-name")
    private WebElement usernameField;
    @FindBy(css="#password")
    private WebElement passwordField;
    @FindBy(css="#login-button")
    private WebElement loginButton;
    @FindBy(css="h3[data-test='error']")
    private WebElement errorMessage;    // Error message displayed for invalid login attempts
    @FindBy(css=".app_logo")
    private WebElement loginSuccessfulTitle;   // Logo that appears after successful login


    //Provides methods for login actions (enterUsername(), enterPassword(), clickLogin()).
    //Uses ExpectedConditions to wait until elements are clickable/visible before interacting with them.
    public SaucedemoLoginPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);  // PageFactory provides an easy way to initialize web elements with the @FindBy annotation, reducing the need for driver.findElement
    }

    public void openPage() {
        driver.get(ConfigReader.getProperty("base.url"));           // opens the login page URL in the browser "https://www.saucedemo.com/"
        wait.until(ExpectedConditions.visibilityOf(usernameField));   //wait until page was loaded
    }

    public void enterUsername(String username) {
        wait.until(ExpectedConditions.elementToBeClickable(usernameField));  // wait until the element is clickable (timeout is set in CommonConditions class)
        usernameField.sendKeys(username);
    }

    public void enterPassword(String password) {
        wait.until(ExpectedConditions.elementToBeClickable(passwordField));
        passwordField.sendKeys(password);
    }

    public void clickLogin() {
        wait.until(ExpectedConditions.elementToBeClickable(loginButton));
        loginButton.click();
    }

    public String getErrorMessage() {
        wait.until(ExpectedConditions.visibilityOf(errorMessage));  // wait until the error message is visible
        return errorMessage.getText();
    }

    public String getLoginMessage() {
        wait.until(ExpectedConditions.visibilityOf(loginSuccessfulTitle));
        return loginSuccessfulTitle.getText();
    }

    public void clearPassword(){
        passwordField.sendKeys(Keys.CONTROL + "a", Keys.BACK_SPACE); // select all and delete
    }

    public void clearFields() {
        usernameField.sendKeys(Keys.CONTROL + "a", Keys.BACK_SPACE);  // select all and delete
        passwordField.sendKeys(Keys.CONTROL + "a", Keys.BACK_SPACE);
        //clear() method didn't work in Chrome browser
    }
}