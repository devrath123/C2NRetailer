package com.example.c2n.make_offer_card.domain;

import android.content.Context;

import com.example.c2n.core.SharedPrefManager;
import com.example.c2n.core.models.OfferDataModel;
import com.example.c2n.core.usecase.UseCase;
import com.example.c2n.core.usecase.UseCaseComposer;
import com.example.c2n.make_offer_card.data.MakeOfferCardRepository;
import com.google.firebase.firestore.DocumentReference;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by vipul.singhal on 06-07-2018.
 */

public class UpdateOfferCardUseCase extends UseCase<OfferDataModel, Boolean> {
    MakeOfferCardRepository makeOfferCardRepository;

    @Inject
    protected UpdateOfferCardUseCase(UseCaseComposer useCaseComposer, MakeOfferCardRepository makeOfferCardRepository) {
        super(useCaseComposer);
        this.makeOfferCardRepository = makeOfferCardRepository;
    }

    @Override
    protected Observable<Boolean> createUseCaseObservable(OfferDataModel offerCard, Context context) {
        SharedPrefManager.Init(context);
        SharedPrefManager.LoadFromPref();
        return makeOfferCardRepository.updateOfferCard(offerCard, SharedPrefManager.get_userDocumentID());
    }
}
