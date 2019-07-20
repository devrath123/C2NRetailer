package com.example.c2n.preferences.di;

import com.example.c2n.preferences.presenter.PreferencesActivity;

import dagger.Component;

/**
 * Created by roshan.nimje on 18-05-2018.
 */

@Component(modules = PreferencesModule.class)
public interface PreferencesComponent {
    void inject(PreferencesActivity preferencesActivity);
}
