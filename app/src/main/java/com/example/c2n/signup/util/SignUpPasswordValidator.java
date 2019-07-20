package com.example.c2n.signup.util;

import android.text.TextUtils;
import android.util.Patterns;

import javax.inject.Inject;

/**
 * Created by vipul.singhal on 10-05-2018.
 */

public class SignUpPasswordValidator {

    private static final String PASSWORD_PATTERN = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}";
//    private static final String USERNAME_PATTERN = "^[a-zA-Z0-9._-]{3,}";


    @Inject
    public SignUpPasswordValidator() {

    }


    public boolean isValidEmail(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public boolean isValidPassword(String password) {
        return password.matches(PASSWORD_PATTERN);
    }

    public boolean isPasswordMatched(String password, String confirmPassword) {
        return (password.equals(confirmPassword));
    }
}
