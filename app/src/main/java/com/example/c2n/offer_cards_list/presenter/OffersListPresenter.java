package com.example.c2n.offer_cards_list.presenter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import com.example.c2n.core.models.OfferDataModel;
import com.example.c2n.offer_cards_list.domain.ActivateOfferCardUseCase;
import com.example.c2n.offer_cards_list.domain.DeactivateOfferCardUseCase;
import com.example.c2n.offer_cards_list.domain.DeleteOfferCardUseCase;
import com.example.c2n.offer_cards_list.domain.GetOfferCardListUseCase;
import com.google.firebase.firestore.DocumentReference;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by shivani.singh on 29-06-2018.
 */

public class OffersListPresenter {

    private final String TAG = "OffersListPresenter";

    GetOfferCardListUseCase getOfferCardListUseCase;
    DeleteOfferCardUseCase deleteOfferCardUseCase;
    ActivateOfferCardUseCase activateOfferCardUseCase;
    DeactivateOfferCardUseCase deactivateOfferCardUseCase;
    OffersListView offersListView;
    Context context;


    @Inject
    public OffersListPresenter(GetOfferCardListUseCase getOfferCardListUseCase, DeleteOfferCardUseCase deleteOfferCardUseCase, ActivateOfferCardUseCase activateOfferCardUseCase, DeactivateOfferCardUseCase deactivateOfferCardUseCase) {
        this.getOfferCardListUseCase = getOfferCardListUseCase;
        this.deleteOfferCardUseCase = deleteOfferCardUseCase;
        this.activateOfferCardUseCase = activateOfferCardUseCase;
        this.deactivateOfferCardUseCase = deactivateOfferCardUseCase;
    }

    public void bind(OffersListView offersListView, Context context) {
        this.offersListView = offersListView;
        this.context = context;

    }

    @SuppressLint("RxLeakedSubscription")
    public void loadOffersList() {
        getOfferCardListUseCase.execute(null, context)
                .doOnSubscribe(disposable -> offersListView.showProgress(true))
                .subscribe(this::handleResponse, throwable -> handleError(throwable));
    }

    private void handleError(Throwable throwable) {
        offersListView.showProgress(false);
        Log.e("loadOffersList..", throwable.getMessage());
    }

    private void handleResponse(List<OfferDataModel> offerDataModels) {
//        Log.d("", "loadOffersList.." + offerDataModels.size());
        offersListView.showProgress(false);
        offersListView.showOffersList(offerDataModels);
    }

    @SuppressLint("RxLeakedSubscription")
    public void deleteOfferCard() {
        deleteOfferCardUseCase.execute(offersListView.getOffercardDocumentId(), context)
                .doOnSubscribe(disposable -> offersListView.showDeletionProgress(true))
                .subscribe(this::handleDeletionResponse, throwable -> handleDeletionError(throwable));
    }

    private void handleDeletionError(Throwable throwable) {
        Log.e("deleteOfferCard..", throwable.getMessage());
        offersListView.isOfferCardDeletionSuccess(false);
    }

    private void handleDeletionResponse(Boolean aBoolean) {
        Log.d("", "deleteOfferCard.." + "-success");
        offersListView.isOfferCardDeletionSuccess(true);
    }

    @SuppressLint("RxLeakedSubscription")
    public void activateOfferCard() {
        Log.d(TAG, "OfferID : " + offersListView.getOffercardDocumentId());
        activateOfferCardUseCase.execute(offersListView.getOffercardDocumentId(), context)
                .doOnSubscribe(disposable -> offersListView.showActivationProgress(true))
                .subscribe(this::handleActivationResponse, throwable -> handleActivationError(throwable));
    }

    private void handleActivationError(Throwable throwable) {
        Log.e("activateOfferCard..", throwable.getMessage());
        offersListView.isOfferCardActivationSuccess(false);
    }

    private void handleActivationResponse(Boolean flag) {
        Log.d("", "activateOfferCard.." + "-success");
        offersListView.isOfferCardActivationSuccess(true);
    }

    @SuppressLint("RxLeakedSubscription")
    public void deactivateOfferCard() {
        deactivateOfferCardUseCase.execute(offersListView.getOffercardDocumentId(), context)
                .doOnSubscribe(disposable -> offersListView.showDeactivationProgress(true))
                .subscribe(this::handleDeactivationResponse, throwable -> handleDeactivationError(throwable));
    }

    private void handleDeactivationError(Throwable throwable) {
        Log.e("deactivateOfferCard..", throwable.getMessage());
        offersListView.isOfferCardDeactivationSuccess(false);
    }

    private void handleDeactivationResponse(Boolean flag) {
        Log.d("", "deactivateOfferCard.." + "-success");
        offersListView.isOfferCardDeactivationSuccess(true);
    }
}
