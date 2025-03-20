package com.epam.automation.test;

import com.epam.automation.page.LoginException;
import org.openqa.selenium.TimeoutException;
import static org.assertj.core.api.Assertions.assertThat;
import org.testng.annotations.*;


public class UserAccessTests extends CommonConditions {

    //testcase 1: enter credentials, clear fields and hit login
    @Test(groups = "UC-1", dataProvider = "invalidCredentials")  //invalidCredentials dataProvider contains a random user and password
    public void testLoginWithEmptyCredentials(String username, String password) {
        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
        loginPage.clearFields();
        loginPage.clickLogin();
        String errorMsg = loginPage.getErrorMessage();  //get error message
        try {
            assertThat(errorMsg).contains("Username is required");  // asserts that error message is as expected
            logger.info("{} UC-1 test successful: an empty credentials error message was displayed", browserName);
        }
        catch (AssertionError e){
            logger.info("{} UC-1: Expected error message not found!", browserName);
            throw e;
        }
    }

    //testcase 2: enter credentials, clear password and hit login
    @Test(groups = "UC-2", dataProvider = "invalidCredentials")  //invalidCredentials dataProvider provides a random user and password
    public void testLoginWithMissingPassword(String username, String password) {
        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
        loginPage.clearPassword();
        loginPage.clickLogin();
        String errorMsg = loginPage.getErrorMessage();  //get error message
        try {
            assertThat(errorMsg).contains("Password is required");   // asserts that error message is as expected
            logger.info("{} UC-2 test successful: a missing password error message was displayed", browserName);
        }
        catch (AssertionError e){
            logger.info("{} UC-2: Expected error message not found!", browserName);
            throw e;
        }
    }

    //testcase 3: test login with valid credentials
    @Test(groups = "UC-3", dataProvider = "validCredentials")  //validCredentials DataProvider provides a list of test users with their credentials
    public void testLoginWithValidCredentials(String username, String password, String expectedTitle) {
        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
        loginPage.clickLogin();

        try {    //checks if the "Swag Labs" title appears on the dashboard after a successful login
            String loginTitle = loginPage.getLoginMessage();
            assertThat(loginTitle).isEqualTo(expectedTitle);
            logger.info("{} UC-3 test successful: login with correct credentials ({})", browserName, username);
        }
        catch (TimeoutException e) {   //if login failed, log the error message and throw custom LoginException
            String errorMsg = loginPage.getErrorMessage();
            logger.info("{} UC-3: login failed ({}) with error message: {}",browserName, username, errorMsg);
            throw new LoginException(browserName + ": " + errorMsg + " - " + username);
        }

        catch(AssertionError e){  //if login is successful, but title does not match, log a message and throw custom LoginException
            logger.info("{} UC-3: Login successful ({}) but title does not match!", browserName, username);
            throw new LoginException(browserName + " UC-3: Login successful but title does not match!");
        }
    }

    //data provider for invalid credentials tests (UC-1 and UC-2)
    @DataProvider(name = "invalidCredentials")
    public Object[][] provideEmptyCredentials() {
        return new Object[][] { {"any_user", "any_password"} };
    }

    // data provider for valid credentials test (UC-3)
    @DataProvider(name = "validCredentials")
    public Object[][] provideValidCredentials() {
        return new Object[][] { { "standard_user", "secret_sauce", "Swag Labs" },
                                { "locked_out_user", "secret_sauce", "Swag Labs" },
                                { "problem_user", "secret_sauce", "Swag Labs" },
                                { "performance_glitch_user", "secret_sauce", "Swag Labs" },  //This test will pass because WebDrivers waits 30 seconds (default timeout) until a website is load. This timeout can be changed using: driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
                                { "visual_user", "secret_sauce", "Swag Labs" }
        };
    }

}