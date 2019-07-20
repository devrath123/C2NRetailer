package com.example.c2n.make_offer_card.domain;

import android.content.Context;
import android.util.Log;

import com.example.c2n.core.SharedPrefManager;
import com.example.c2n.core.models.OfferDataModel;
import com.example.c2n.core.usecase.UseCase;
import com.example.c2n.core.usecase.UseCaseComposer;
import com.example.c2n.make_offer_card.data.MakeOfferCardRepository;
import com.google.firebase.firestore.DocumentReference;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by vipul.singhal on 02-07-2018.
 */

public class MakeOfferCardUseCase extends UseCase<OfferDataModel, DocumentReference> {
    MakeOfferCardRepository makeOfferCardRepository;

    @Inject
    protected MakeOfferCardUseCase(UseCaseComposer useCaseComposer, MakeOfferCardRepository makeOfferCardRepository) {
        super(useCaseComposer);
        this.makeOfferCardRepository = makeOfferCardRepository;
    }

    @Override
    protected Observable<DocumentReference> createUseCaseObservable(OfferDataModel offerCard, Context context) {
        Log.d("offer_card", offerCard.toString());
        SharedPrefManager.Init(context);
        SharedPrefManager.LoadFromPref();
        return makeOfferCardRepository.addOfferCard(offerCard, SharedPrefManager.get_userEmail());
    }
}
