package com.example.c2n.login.util;

import android.util.Patterns;

import javax.inject.Inject;

/**
 * Created by vipul.singhal on 11-05-2018.
 */

public class LoginValidator {
    private static final String PASSWORD_PATTERN = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}";
    private static final String USERNAME_PATTERN = "^[a-zA-Z0-9._-]{3,}";


    @Inject
    public LoginValidator() {

    }

    public boolean isValidEmail(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public boolean isValidPassword(String password) {
        return password.matches(PASSWORD_PATTERN);
    }

}
