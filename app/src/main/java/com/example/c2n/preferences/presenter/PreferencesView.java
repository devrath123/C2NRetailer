package com.example.c2n.preferences.presenter;

import com.example.c2n.core.model.CategoryDataModel;

import java.util.List;

/**
 * Created by roshan.nimje on 18-05-2018.
 */

public interface PreferencesView {

    void loadPreferences();

    void showPreferenceProgress(Boolean bool);

    void isshowPreferenceSuccess(Boolean success);

    void showPreferencesList(List<CategoryDataModel> categoryDataModels);
}
