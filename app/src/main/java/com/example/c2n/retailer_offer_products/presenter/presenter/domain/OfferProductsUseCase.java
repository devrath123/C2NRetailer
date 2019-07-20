package com.example.c2n.retailer_offer_products.presenter.presenter.domain;

import android.content.Context;

import com.example.c2n.core.usecase.UseCase;
import com.example.c2n.core.usecase.UseCaseComposer;
import com.example.c2n.retailer_offer_products.presenter.presenter.data.OfferProductsRepository;
import com.google.firebase.firestore.DocumentReference;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by roshan.nimje on 04-07-2018.
 */

public class OfferProductsUseCase extends UseCase<Object[], DocumentReference> {

    OfferProductsRepository offerProductsRepository;

    @Inject
    protected OfferProductsUseCase(UseCaseComposer useCaseComposer, OfferProductsRepository offerProductsRepository) {
        super(useCaseComposer);
//        this.offerProductsRepository = offerProductsRepository;
        this.offerProductsRepository = new OfferProductsRepository();
    }

    @Override
    protected Observable<DocumentReference> createUseCaseObservable(Object[] objects, Context context) {
//        Log.d("OfferProductsUseCase_", "" + "in update offer use case " + productDataModelList.toString());
        return offerProductsRepository.updateProducts(objects);
    }
}
