package com.example.c2n.preferences.di;

import com.example.c2n.core.usecase.AndroidUseCaseComposer;
import com.example.c2n.core.usecase.UseCaseComposer;

import dagger.Module;
import dagger.Provides;

/**
 * Created by roshan.nimje on 18-05-2018.
 */

@Module
public class PreferencesModule {
    @Provides
    UseCaseComposer getUseCaseComposer() {
        return new AndroidUseCaseComposer();
    }
}
