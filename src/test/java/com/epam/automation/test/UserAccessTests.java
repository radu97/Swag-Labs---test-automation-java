package com.epam.automation.test;

import com.epam.automation.exceptions.LoginException;
import com.epam.automation.service.ConfigReader;
import org.openqa.selenium.TimeoutException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import static org.assertj.core.api.Assertions.assertThat;



public class UserAccessTests extends CommonConditions {

    public static final String UC_1_TEST_SUCCESSFUL = "{} UC-1 test successful: an empty credentials error message was displayed";
    public static final String USERNAME_REQUIRED = "Username is required";
    public static final String UC_1_EXPECTED_MESSAGE_NOT_FOUND = "{} UC-1: Expected error message not found!";
    public static final String UC_2_EXPECTED_MESSAGE_NOT_FOUND = "{} UC-2: Expected error message not found!";
    public static final String UC_3_TITLE_NOT_MATCH = "%s UC-3: Login successful (%s) but title does not match!";
    public static final String SWAG_LABS_TITLE = "Swag Labs";
    public static final String UC_3_TEST_SUCCESSFUL_LOGIN = "{} UC-3 test successful: login with correct credentials ({})";
    public static final String UC_3_LOGIN_FAILED = "{} UC-3: login failed ({}) with error message: {}";

    //testcase 1: enter credentials, clear fields and hit login
    @Test(groups = "UC-1")
    public void testLoginWithEmptyCredentials() {
        loginPage.enterUsername(ConfigReader.getProperty("default.username"));  //default username and password are read from config file
        loginPage.enterPassword(ConfigReader.getProperty("default.password"));
        loginPage.clearFields();
        loginPage.clickLogin();

        String errorMsg = loginPage.getErrorMessage();  //get error message
        assertThat(errorMsg)                            // asserts that error message is as expected
                .withFailMessage(UC_1_EXPECTED_MESSAGE_NOT_FOUND, browserName)
                .contains(USERNAME_REQUIRED);
        logger.info(UC_1_TEST_SUCCESSFUL, browserName);
    }

    //testcase 2: enter credentials, clear password and hit login
    @Test(groups = "UC-2")
    public void testLoginWithMissingPassword(/*String username, String password*/) {
        loginPage.enterUsername(ConfigReader.getProperty("default.username"));
        loginPage.enterPassword(ConfigReader.getProperty("default.password"));
        loginPage.clearPassword();
        loginPage.clickLogin();

        String errorMsg = loginPage.getErrorMessage();  //get error message
        assertThat(errorMsg)                            // asserts that error message is as expected
                .withFailMessage(UC_2_EXPECTED_MESSAGE_NOT_FOUND, browserName)
                .contains("Password is required");
        logger.info("{} UC-2 test successful: a missing password error message was displayed", browserName);
    }

    //testcase 3: test login with valid credentials
    @Test(groups = "UC-3", dataProvider = "validCredentials")  //validCredentials DataProvider provides a list of test users with their credentials
    public void testLoginWithValidCredentials(String username, String password) {
        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
        loginPage.clickLogin();

        try {    //checks if the "Swag Labs" title appears on the dashboard after a successful login
            String loginTitle = loginPage.getLoginMessage();
            assertThat(loginTitle)
                    .withFailMessage(UC_3_TITLE_NOT_MATCH, browserName, username)
                    .isEqualTo(SWAG_LABS_TITLE);
            logger.info(UC_3_TEST_SUCCESSFUL_LOGIN, browserName, username);
        }
        catch (TimeoutException e) {   //if login failed, log the error message and throw custom LoginException
            String errorMsg = loginPage.getErrorMessage();
            logger.info(UC_3_LOGIN_FAILED,browserName, username, errorMsg);
            throw new LoginException(browserName + ": " + errorMsg + " - " + username);
        }
    }

    // data provider for valid credentials test (UC-3)
    @DataProvider(name = "validCredentials")
    public Object[][] provideValidCredentials() {
        return new Object[][] { { "standard_user", "secret_sauce" },
                                { "locked_out_user", "secret_sauce" },
                                { "problem_user", "secret_sauce" },
                                { "performance_glitch_user", "secret_sauce" },  //This test will pass because WebDrivers waits 30 seconds (default timeout) until a website is load. This timeout can be changed using: driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
                                { "visual_user", "secret_sauce" }
        };
    }

}