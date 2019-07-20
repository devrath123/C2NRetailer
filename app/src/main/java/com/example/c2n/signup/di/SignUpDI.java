package com.example.c2n.signup.di;

/**
 * Created by vipul.singhal on 10-05-2018.
 */

public class SignUpDI {
    private static SignUpComponent component;

    public static SignUpComponent getUserSignUpComponent() {
        if (component == null) {
            component = DaggerSignUpComponent.builder().build();
        }
        return component;
    }
}
