package com.example.c2n.preferences.di;

import com.example.c2n.preferences.presenter.PreferencesActivity;

/**
 * Created by roshan.nimje on 18-05-2018.
 */

public class PreferencesDI {
    private static PreferencesComponent preferencesComponent;

    public static PreferencesComponent getPreferencesComponent() {
        if (preferencesComponent == null) {
            preferencesComponent = DaggerPreferencesComponent.builder().build();
        }
        return preferencesComponent;
    }
}
