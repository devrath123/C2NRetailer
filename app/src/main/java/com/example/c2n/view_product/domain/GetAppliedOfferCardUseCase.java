package com.example.c2n.view_product.domain;

import android.content.Context;

import com.example.c2n.core.SharedPrefManager;
import com.example.c2n.core.model.DocumentToOfferListDataModel;
import com.example.c2n.core.models.OfferDataModel;
import com.example.c2n.core.usecase.UseCase;
import com.example.c2n.core.usecase.UseCaseComposer;
import com.example.c2n.view_product.data.ViewUpdateProductRepository;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by vipul.singhal on 09-07-2018.
 */

public class GetAppliedOfferCardUseCase extends UseCase<String, OfferDataModel> {
    ViewUpdateProductRepository viewUpdateProductRepository;
    DocumentToOfferListDataModel documentToOfferListDataModel;

    @Inject
    protected GetAppliedOfferCardUseCase(UseCaseComposer useCaseComposer, ViewUpdateProductRepository viewUpdateProductRepository, DocumentToOfferListDataModel documentToOfferListDataModel) {
        super(useCaseComposer);
        this.viewUpdateProductRepository = viewUpdateProductRepository;
        this.documentToOfferListDataModel = documentToOfferListDataModel;
    }

    @Override
    protected Observable<OfferDataModel> createUseCaseObservable(String offerId, Context context) {
        SharedPrefManager.Init(context);
        SharedPrefManager.LoadFromPref();
        return viewUpdateProductRepository.getOfferCard(SharedPrefManager.get_userDocumentID(),offerId).map(documentSnapshotList -> documentToOfferListDataModel.mapDocumentToOfferListDataModel(documentSnapshotList));
    }
}
