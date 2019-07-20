package com.example.c2n.addshop.util;

import android.util.Patterns;

import javax.inject.Inject;

/**
 * Created by vipul.singhal on 11-05-2018.
 */

public class AddShopValidator {

    @Inject
    public AddShopValidator() {

    }

    public boolean isValidEmail(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
