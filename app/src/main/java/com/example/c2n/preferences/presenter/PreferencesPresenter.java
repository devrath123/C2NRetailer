package com.example.c2n.preferences.presenter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import com.example.c2n.core.model.CategoryDataModel;
import com.example.c2n.preferences.domain.PreferencesUseCase;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by roshan.nimje on 18-05-2018.
 */

public class PreferencesPresenter {

    PreferencesUseCase preferencesUseCase;
    PreferencesView preferencesView;
    Context context;

    @Inject
    PreferencesPresenter(PreferencesUseCase preferencesUseCase) {
        this.preferencesUseCase = preferencesUseCase;
    }

    public void bind(PreferencesView preferencesView, Context context) {
        this.preferencesView = preferencesView;
        this.context = context;
    }

    @SuppressLint("RxLeakedSubscription")
    public void loadPreferences() {
        preferencesUseCase.execute(null, context)
                .doOnSubscribe(disposable -> preferencesView.showPreferenceProgress(true))
                .subscribe(this::handleResponse, throwable -> handleError(throwable));
    }

    private void handleError(Throwable throwable) {
        preferencesView.showPreferenceProgress(false);
        Log.d("PreferencesPresenter", "" + throwable.getMessage());
    }

    private void handleResponse(List<CategoryDataModel> categoryDataModels) {
        preferencesView.showPreferenceProgress(false);
        preferencesView.showPreferencesList(categoryDataModels);
        Log.d("PreferencesPresenter", "" + categoryDataModels.toString());
    }
}
