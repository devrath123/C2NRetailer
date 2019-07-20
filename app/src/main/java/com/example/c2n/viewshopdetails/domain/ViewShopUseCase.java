package com.example.c2n.viewshopdetails.domain;

import android.content.Context;

import com.example.c2n.core.SharedPrefManager;
import com.example.c2n.core.model.DocumentToShopDataModel;
import com.example.c2n.core.models.ShopDataModel;
import com.example.c2n.core.usecase.UseCase;
import com.example.c2n.core.usecase.UseCaseComposer;
import com.example.c2n.viewshopdetails.data.ViewShopRepository;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by roshan.nimje on 24-05-2018.
 */

public class ViewShopUseCase extends UseCase<ShopDataModel, Boolean> {

    ViewShopRepository viewShopRepository;
    DocumentToShopDataModel documentToShopDataModel;

    @Inject
    protected ViewShopUseCase(UseCaseComposer useCaseComposer, ViewShopRepository viewShopRepository, DocumentToShopDataModel documentToShopDataModel) {
        super(useCaseComposer);
        this.viewShopRepository = viewShopRepository;
        this.documentToShopDataModel = documentToShopDataModel;
    }

    @Override
    protected Observable<Boolean> createUseCaseObservable(ShopDataModel shopDataModel, Context context) {
//        return viewShopRepository.getShopDetails(shopDataModel).map(documentSnapshot -> documentToShopDataModel.mapDocumentToShopDataModel(documentSnapshot));
        SharedPrefManager.Init(context);
        SharedPrefManager.LoadFromPref();
        return viewShopRepository.updateShopDetails(shopDataModel,SharedPrefManager.get_userEmail());
    }
}
