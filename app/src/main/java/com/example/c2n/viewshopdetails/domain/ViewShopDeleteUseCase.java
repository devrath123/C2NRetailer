package com.example.c2n.viewshopdetails.domain;

import android.content.Context;

import com.example.c2n.core.model.DocumentToShopDataModel;
import com.example.c2n.core.models.ShopDataModel;
import com.example.c2n.core.usecase.UseCase;
import com.example.c2n.core.usecase.UseCaseComposer;
import com.example.c2n.viewshopdetails.data.ViewShopRepository;
import com.google.firebase.firestore.DocumentReference;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by roshan.nimje on 24-05-2018.
 */

public class ViewShopDeleteUseCase extends UseCase<ShopDataModel, DocumentReference> {

    ViewShopRepository viewShopRepository;
    DocumentToShopDataModel documentToShopDataModel;

    @Inject
    protected ViewShopDeleteUseCase(UseCaseComposer useCaseComposer, ViewShopRepository viewShopRepository, DocumentToShopDataModel documentToShopDataModel) {
        super(useCaseComposer);
        this.viewShopRepository = viewShopRepository;
        this.documentToShopDataModel = documentToShopDataModel;
    }

    @Override
    protected Observable<DocumentReference> createUseCaseObservable(ShopDataModel shopDataModel, Context context) {
//        return viewShopRepository.getShopDetails(shopDataModel).map(documentSnapshot -> documentToShopDataModel.mapDocumentToShopDataModel(documentSnapshot));
        return viewShopRepository.deleteShop(shopDataModel);
    }
}
