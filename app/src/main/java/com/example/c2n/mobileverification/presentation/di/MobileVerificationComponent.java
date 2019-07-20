package com.example.c2n.mobileverification.presentation.di;

import com.example.c2n.mobileverification.presentation.presenter.MobileVerificationActivity;

import dagger.Component;

/**
 * Created by roshan.nimje on 16-05-2018.
 */

@Component(modules = MobileVerificationModule.class)
public interface MobileVerificationComponent {
    void inject(MobileVerificationActivity mobileVerificationActivity);
}
