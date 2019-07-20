package com.example.c2n.retailer_offer_products.presenter.presenter.domain;

import android.content.Context;

import com.example.c2n.core.usecase.UseCase;
import com.example.c2n.core.usecase.UseCaseComposer;
import com.example.c2n.retailer_offer_products.presenter.presenter.data.OfferProductsRepository;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by roshan.nimje on 04-07-2018.
 */

//public class OfferProductsUseCase extends UseCase<List<ProductDataModel>, DocumentReference> {
public class RemoveOfferProductsUseCase extends UseCase<Object[], List<QueryDocumentSnapshot>> {

    OfferProductsRepository offerProductsRepository;

    @Inject
    protected RemoveOfferProductsUseCase(UseCaseComposer useCaseComposer, OfferProductsRepository offerProductsRepository) {
        super(useCaseComposer);
        this.offerProductsRepository = offerProductsRepository;
    }

    @Override
    protected Observable<List<QueryDocumentSnapshot>> createUseCaseObservable(Object[] objects, Context context) {
//        Log.d("OfferProductsUseCase_", "" + "in update offer use case " + productDataModelList.toString());
        return offerProductsRepository.removeOffer(objects);
//        return null;
    }
}
