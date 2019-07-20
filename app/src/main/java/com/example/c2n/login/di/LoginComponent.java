package com.example.c2n.login.di;

import com.example.c2n.login.presenter.LoginActivity;

import dagger.Component;

/**
 * Created by vipul.singhal on 10-05-2018.
 */
@Component(modules = LoginModule.class)
public interface LoginComponent {
     void inject(LoginActivity loginActivity);
}
