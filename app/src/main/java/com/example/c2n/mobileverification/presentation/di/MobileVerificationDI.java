package com.example.c2n.mobileverification.presentation.di;

/**
 * Created by roshan.nimje on 16-05-2018.
 */

public class MobileVerificationDI {
    private static MobileVerificationComponent component;

    public static MobileVerificationComponent getMobileVerificationComponent() {
        if (component == null) {
            component = DaggerMobileVerificationComponent.builder().build();
        }
        return component;
    }
}
