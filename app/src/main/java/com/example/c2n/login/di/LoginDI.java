package com.example.c2n.login.di;

/**
 * Created by vipul.singhal on 10-05-2018.
 */

public class LoginDI {
    private static LoginComponent component;

    public static LoginComponent getUserLoginComponent()
    {
        if(component == null)
        {
            component = DaggerLoginComponent.builder().build();
        }
        return component;
    }
}
