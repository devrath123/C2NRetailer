package com.example.c2n.login.di;

import com.example.c2n.core.usecase.AndroidUseCaseComposer;
import com.example.c2n.core.usecase.UseCaseComposer;

import dagger.Module;
import dagger.Provides;

/**
 * Created by vipul.singhal on 10-05-2018.
 */

@Module
public class LoginModule {
    @Provides
    UseCaseComposer getUseCaseComposer() {
        return new AndroidUseCaseComposer();
    }
}
