package com.example.c2n.signUp1.di;

import com.example.c2n.signUp1.presenter.SignUpDetailsActivity;

import dagger.Component;

/**
 * Created by roshan.nimje on 05-06-2018.
 */

@Component(modules = SignUp1Module.class)
public interface SignUp1Component {
    void inject(SignUpDetailsActivity signUpDetailsActivity);
}
