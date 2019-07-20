package com.example.c2n.customer_home.di;

import android.app.Activity;
import android.content.Context;

import com.example.c2n.core.usecase.AndroidUseCaseComposer;
import com.example.c2n.core.usecase.UseCaseComposer;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * Created by shivani.singh on 16-08-2018.
 */

@Module
public class CustomerHomeModule {

    @Provides
    UseCaseComposer getUseCaseComposer() {
        return new AndroidUseCaseComposer();
    }
}