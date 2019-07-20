package com.example.c2n.retailerhome.domain;

import android.content.Context;

import com.example.c2n.core.SharedPrefManager;
import com.example.c2n.core.usecase.UseCase;
import com.example.c2n.core.usecase.UseCaseComposer;
import com.example.c2n.retailerhome.data.RetailerHomeRepository;
import com.google.firebase.firestore.DocumentReference;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by vipul.singhal on 02-07-2018.
 */

public class DeleteExpireOfferCardsUseCase extends UseCase<String, DocumentReference> {
    RetailerHomeRepository retailerHomeRepository;

    @Inject
    protected DeleteExpireOfferCardsUseCase(UseCaseComposer useCaseComposer, RetailerHomeRepository retailerHomeRepository) {
        super(useCaseComposer);
        this.retailerHomeRepository = retailerHomeRepository;
    }

    @Override
    protected Observable<DocumentReference> createUseCaseObservable(String offerID, Context context) {
        SharedPrefManager.Init(context);
        SharedPrefManager.LoadFromPref();
        return retailerHomeRepository.deleteExpireOfferCards(SharedPrefManager.get_userDocumentID(), offerID);
    }

}
