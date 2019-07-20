package com.example.c2n.view_product.domain;

import android.content.Context;
import android.util.Log;

import com.example.c2n.core.SharedPrefManager;
import com.example.c2n.core.models.OfferDataModel;
import com.example.c2n.core.models.ProductDataModel;
import com.example.c2n.core.models.ShopDataModel;
import com.example.c2n.core.usecase.UseCase;
import com.example.c2n.core.usecase.UseCaseComposer;
import com.example.c2n.view_product.data.ViewUpdateProductRepository;
import com.google.firebase.firestore.DocumentReference;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by vipul.singhal on 09-07-2018.
 */

public class UpdateProductOfferCardUseCase extends UseCase<Object[], Boolean> {

    ViewUpdateProductRepository viewUpdateProductRepository;

    @Inject
    protected UpdateProductOfferCardUseCase(UseCaseComposer useCaseComposer, ViewUpdateProductRepository viewUpdateProductRepository) {
        super(useCaseComposer);
        this.viewUpdateProductRepository = viewUpdateProductRepository;
    }

    @Override
    protected Observable<Boolean> createUseCaseObservable(Object[] updatedProductOffer, Context context) {
        SharedPrefManager.Init(context);
        SharedPrefManager.LoadFromPref();
//        return viewShopRepository.getShopDetails(shopDataModel).map(documentSnapshot -> documentToShopDataModel.mapDocumentToShopDataModel(documentSnapshot));
        return viewUpdateProductRepository.updateProductOffer((ProductDataModel) updatedProductOffer[0], (ShopDataModel) updatedProductOffer[1], (OfferDataModel) updatedProductOffer[2], SharedPrefManager.get_userEmail());
    }
}
