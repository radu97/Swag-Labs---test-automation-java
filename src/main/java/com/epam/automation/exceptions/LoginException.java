package com.epam.automation.exceptions;

// Custom exception class for handling login-related errors (UC-3 test case)
public class LoginException extends RuntimeException {
    public LoginException(String message) {
        super(message);
    }
}
