package com.example.c2n.view_product.domain;

import android.content.Context;
import android.util.Log;

import com.example.c2n.core.SharedPrefManager;
import com.example.c2n.core.model.DocumentToShopDataModel;
import com.example.c2n.core.models.ProductDataModel;
import com.example.c2n.core.usecase.UseCase;
import com.example.c2n.core.usecase.UseCaseComposer;
import com.example.c2n.view_product.data.ViewUpdateProductRepository;
import com.google.firebase.firestore.DocumentReference;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by vipul.singhal on 24-06-2018.
 */

public class ViewUpdateProductUseCase extends UseCase<Object[], Boolean> {
    ViewUpdateProductRepository viewUpdateProductRepository;
    DocumentToShopDataModel documentToShopDataModel;

    @Inject
    protected ViewUpdateProductUseCase(UseCaseComposer useCaseComposer, ViewUpdateProductRepository viewUpdateProductRepository, DocumentToShopDataModel documentToShopDataModel) {
        super(useCaseComposer);
        this.viewUpdateProductRepository = viewUpdateProductRepository;
        this.documentToShopDataModel = documentToShopDataModel;
    }

    @Override
    protected Observable<Boolean> createUseCaseObservable(Object[] updatedProduct, Context context) {
        SharedPrefManager.Init(context);
        SharedPrefManager.LoadFromPref();
//        return viewShopRepository.getShopDetails(shopDataModel).map(documentSnapshot -> documentToShopDataModel.mapDocumentToShopDataModel(documentSnapshot));
        return viewUpdateProductRepository.updateProductDetails(SharedPrefManager.get_userDocumentID(), (ProductDataModel) updatedProduct[0], (String) updatedProduct[1]);
    }
}
