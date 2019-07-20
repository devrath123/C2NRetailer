package com.example.c2n.signup.di;

import com.example.c2n.login.presenter.LoginActivity;
import com.example.c2n.signup.presenter.SignUpActivity;

import dagger.Component;

/**
 * Created by vipul.singhal on 10-05-2018.
 */
@Component(modules = SignUpModule.class)
public interface SignUpComponent {
    void inject(SignUpActivity signUpActivity);

    void inject(LoginActivity loginActivity);
}
