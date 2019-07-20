package com.example.c2n.viewshopdetails.util;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Patterns;

import javax.inject.Inject;

/**
 * Created by vipul.singhal on 11-05-2018.
 */

public class ViewShopDetailsValidator {

    @Inject
    public ViewShopDetailsValidator() {

    }

    @RequiresApi(api = Build.VERSION_CODES.FROYO)
    public boolean isValidEmail(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
