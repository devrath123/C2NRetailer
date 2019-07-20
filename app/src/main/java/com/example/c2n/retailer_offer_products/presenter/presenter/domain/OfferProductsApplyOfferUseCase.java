package com.example.c2n.retailer_offer_products.presenter.presenter.domain;

import android.content.Context;

import com.example.c2n.core.SharedPrefManager;
import com.example.c2n.core.usecase.UseCase;
import com.example.c2n.core.usecase.UseCaseComposer;
import com.example.c2n.retailer_offer_products.presenter.presenter.data.OfferProductsRepository;
import com.google.firebase.firestore.DocumentReference;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by roshan.nimje on 04-07-2018.
 */

public class OfferProductsApplyOfferUseCase extends UseCase<Object[], Boolean> {

    OfferProductsRepository offerProductsRepository;

    @Inject
    protected OfferProductsApplyOfferUseCase(UseCaseComposer useCaseComposer, OfferProductsRepository offerProductsRepository) {
        super(useCaseComposer);
        this.offerProductsRepository = offerProductsRepository;
    }

    @Override
    protected Observable<Boolean> createUseCaseObservable(Object[] objects, Context context) {
//        Log.d("OfferProductsUseCase_", "" + "in update offer use case " + productDataModelList.toString());
        SharedPrefManager.Init(context);
        SharedPrefManager.LoadFromPref();
        return offerProductsRepository.applyOffers(objects, SharedPrefManager.get_userDocumentID());
    }
}
