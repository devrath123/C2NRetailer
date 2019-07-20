package com.example.c2n.view_product.domain;

import android.content.Context;

import com.example.c2n.core.SharedPrefManager;
import com.example.c2n.core.usecase.UseCase;
import com.example.c2n.core.usecase.UseCaseComposer;
import com.example.c2n.view_product.data.ViewUpdateProductRepository;
import com.google.firebase.firestore.DocumentReference;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by vipul.singhal on 09-07-2018.
 */

public class RemoveProductOfferCardUseCase extends UseCase<Object[], Boolean> {

    ViewUpdateProductRepository viewUpdateProductRepository;

    @Inject
    protected RemoveProductOfferCardUseCase(UseCaseComposer useCaseComposer, ViewUpdateProductRepository viewUpdateProductRepository) {
        super(useCaseComposer);
        this.viewUpdateProductRepository = viewUpdateProductRepository;
    }

    @Override
    protected Observable<Boolean> createUseCaseObservable(Object[] product, Context context) {
        SharedPrefManager.Init(context);
        SharedPrefManager.LoadFromPref();
//        return viewShopRepository.getShopDetails(shopDataModel).map(documentSnapshot -> documentToShopDataModel.mapDocumentToShopDataModel(documentSnapshot));
        return viewUpdateProductRepository.removeProductOffer((String)product[0],SharedPrefManager.get_userDocumentID(),(String)product[1],(String) product[2]);
    }
}
