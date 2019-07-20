package com.example.c2n.offer_cards_list.presenter;

import com.example.c2n.core.models.OfferDataModel;

import java.util.List;

/**
 * Created by shivani.singh on 29-06-2018.
 */

public interface OffersListView {

    String getOffercardDocumentId();

    void showProgress(Boolean progress);

    void showDeletionProgress(Boolean progress);

    void showActivationProgress(Boolean progress);

    void isOfferCardActivationSuccess(Boolean success);

    void showDeactivationProgress(Boolean progress);

    void isOfferCardDeactivationSuccess(Boolean success);

    void addNewOffers();

    void showOffersList(List<OfferDataModel> offerDataModels);

    void isOfferCardDeletionSuccess(Boolean success);
}
