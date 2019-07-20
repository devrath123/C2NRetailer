package com.example.c2n.make_offer_card.presenter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import com.example.c2n.make_offer_card.domain.MakeOfferCardUseCase;
import com.example.c2n.make_offer_card.domain.UpdateOfferCardUseCase;
import com.google.firebase.firestore.DocumentReference;

import java.util.ArrayList;

import javax.inject.Inject;

/**
 * Created by vipul.singhal on 02-07-2018.
 */

public class MakeOfferCardPresenter {

    MakeOfferCardUseCase makeOfferCardUseCase;
    UpdateOfferCardUseCase updateOfferCardUseCase;
    MakeOfferCardView makeOfferCardView;
    Context context;

    @Inject
    MakeOfferCardPresenter(MakeOfferCardUseCase makeOfferCardUseCase, UpdateOfferCardUseCase updateOfferCardUseCase) {
        this.makeOfferCardUseCase = makeOfferCardUseCase;
        this.updateOfferCardUseCase = updateOfferCardUseCase;
    }

    public void bind(MakeOfferCardView makeOfferCardView, Context context) {
        this.makeOfferCardView = makeOfferCardView;
        this.context = context;
    }


    @SuppressLint("RxLeakedSubscription")
    public void addOfferCard() {
        com.example.c2n.core.models.OfferDataModel offerDataModel = new com.example.c2n.core.models.OfferDataModel();
        offerDataModel.setOfferID("");
        offerDataModel.setOfferDiscount(makeOfferCardView.getOfferCardDiscount());
        offerDataModel.setOfferName(makeOfferCardView.getOfferCardName());
        offerDataModel.setOfferStartDate(makeOfferCardView.getOfferStartDate());
        offerDataModel.setOfferEndDate(makeOfferCardView.getOfferEndDate());
        offerDataModel.setSun(makeOfferCardView.getIsSundayIncludes());
        offerDataModel.setMon(makeOfferCardView.getIsMondayIncludes());
        offerDataModel.setTue(makeOfferCardView.getIsTuesdayIncludes());
        offerDataModel.setWed(makeOfferCardView.getIsWednesdayIncludes());
        offerDataModel.setThu(makeOfferCardView.getIsThursdayIncludes());
        offerDataModel.setFri(makeOfferCardView.getIsFridayIncludes());
        offerDataModel.setSat(makeOfferCardView.getIsSaturdayIncludes());
        offerDataModel.setShopDataModels(new ArrayList<>());
//        offerDataModel.setOfferID(FieldValue.serverTimestamp()+"");


        makeOfferCardUseCase.execute(offerDataModel, context)
                .doOnSubscribe(disposable ->
                        makeOfferCardView.showOfferCardProgress(true, 0))
                .subscribe(this::handleResponse, throwable -> handleError(throwable));
    }

    public void handleResponse(DocumentReference documentReference) {
        Log.d("Document_refersnce:..", "............  " + documentReference + documentReference.getId());
//        signUpView.setAddedUserDocumentId(documentReference.getId());
        makeOfferCardView.isOfferCardAddingSuccess(true);
    }

    public void handleError(Throwable e) {
        Log.d("error...", "..." + e.getMessage());
        makeOfferCardView.isOfferCardAddingSuccess(false);
    }


    @SuppressLint("RxLeakedSubscription")
    public void updateOfferCard() {
        com.example.c2n.core.models.OfferDataModel offerDataModel = new com.example.c2n.core.models.OfferDataModel();
        offerDataModel.setOfferID(makeOfferCardView.getOfferId());
        offerDataModel.setOfferDiscount(makeOfferCardView.getOfferCardDiscount());
        offerDataModel.setOfferName(makeOfferCardView.getOfferCardName());
        offerDataModel.setOfferStartDate(makeOfferCardView.getOfferStartDate());
        offerDataModel.setOfferEndDate(makeOfferCardView.getOfferEndDate());
        offerDataModel.setSun(makeOfferCardView.getIsSundayIncludes());
        offerDataModel.setMon(makeOfferCardView.getIsMondayIncludes());
        offerDataModel.setTue(makeOfferCardView.getIsTuesdayIncludes());
        offerDataModel.setWed(makeOfferCardView.getIsWednesdayIncludes());
        offerDataModel.setThu(makeOfferCardView.getIsThursdayIncludes());
        offerDataModel.setFri(makeOfferCardView.getIsFridayIncludes());
        offerDataModel.setSat(makeOfferCardView.getIsSaturdayIncludes());
        offerDataModel.setOfferStatus(makeOfferCardView.getOfferStatus());
        offerDataModel.setShopDataModels(new ArrayList<>());
        updateOfferCardUseCase.execute(offerDataModel, context)
                .doOnSubscribe(disposable ->
                        makeOfferCardView.showOfferCardProgress(true, 1))
                .subscribe(this::handleUpdateResponse, throwable -> handleUpdateError(throwable));
    }

    public void handleUpdateResponse(Boolean aBoolean) {
//        Log.d("Document_refersnce:..", "............  " + documentReference.get().getResult());
//        signUpView.setAddedUserDocumentId(documentReference.getId());
        makeOfferCardView.isOfferCardUpdatingSuccess(true);
    }

    public void handleUpdateError(Throwable e) {
        Log.d("error...", "..." + e.getMessage());
        makeOfferCardView.isOfferCardUpdatingSuccess(false);
    }

}
