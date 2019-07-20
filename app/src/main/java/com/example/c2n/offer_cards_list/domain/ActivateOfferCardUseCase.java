package com.example.c2n.offer_cards_list.domain;

import android.content.Context;

import com.example.c2n.core.SharedPrefManager;
import com.example.c2n.core.usecase.UseCase;
import com.example.c2n.core.usecase.UseCaseComposer;
import com.example.c2n.offer_cards_list.data.OffersRepository;
import com.google.firebase.firestore.DocumentReference;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by vipul.singhal on 29-07-2018.
 */

public class ActivateOfferCardUseCase extends UseCase<String, Boolean> {
    OffersRepository offersRepository;

    @Inject
    protected ActivateOfferCardUseCase(UseCaseComposer useCaseComposer, OffersRepository offersRepository) {
        super(useCaseComposer);
        this.offersRepository = offersRepository;
    }

    @Override
    protected Observable<Boolean> createUseCaseObservable(String offerDocumentId, Context context) {
        SharedPrefManager.Init(context);
        SharedPrefManager.LoadFromPref();
        return offersRepository.activateOfferCard(offerDocumentId, SharedPrefManager.get_userDocumentID());
//        return offersRepository.activateOfferCard(SharedPrefManager.get_userDocumentID(), offerDocumentId);
    }
}
