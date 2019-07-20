package com.example.c2n.signUp1.di;

/**
 * Created by roshan.nimje on 05-06-2018.
 */

public class SignUp1DI {
    private static SignUp1Component component;

    public static SignUp1Component getSignUp1Component() {
        if (component == null) {
            component = DaggerSignUp1Component.builder().build();
        }
        return component;
    }
}
