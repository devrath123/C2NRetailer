package com.example.c2n.preferences.domain;

import android.content.Context;

import com.example.c2n.core.model.CategoryDataModel;
import com.example.c2n.core.model.CategoriesQueryDocumentSnapshotToCategoriesDataModel;
import com.example.c2n.core.usecase.UseCase;
import com.example.c2n.core.usecase.UseCaseComposer;
import com.example.c2n.preferences.data.PreferencesRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by roshan.nimje on 18-05-2018.
 */

public class PreferencesUseCase extends UseCase<Void, List<CategoryDataModel>> {

    PreferencesRepository preferencesRepository;
    CategoriesQueryDocumentSnapshotToCategoriesDataModel categoriesQueryDocumentSnapshotToCategoriesDataModel;

    @Inject
    protected PreferencesUseCase(UseCaseComposer useCaseComposer, PreferencesRepository preferencesRepository, CategoriesQueryDocumentSnapshotToCategoriesDataModel categoriesQueryDocumentSnapshotToCategoriesDataModel) {
        super(useCaseComposer);
        this.preferencesRepository = preferencesRepository;
        this.categoriesQueryDocumentSnapshotToCategoriesDataModel = categoriesQueryDocumentSnapshotToCategoriesDataModel;
    }

    @Override
    protected Observable<List<CategoryDataModel>> createUseCaseObservable(Void param, Context context) {
        return preferencesRepository.loadPreferences().map(queryDocumentSnapshots -> categoriesQueryDocumentSnapshotToCategoriesDataModel.queryDocumentSnapshotToCategoriesDataModel(queryDocumentSnapshots));
    }
}
