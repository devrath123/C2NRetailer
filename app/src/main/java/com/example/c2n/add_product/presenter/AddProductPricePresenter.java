package com.example.c2n.add_product.presenter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import com.example.c2n.core.models.OfferDataModel;
import com.example.c2n.retailerhome.domain.GetOfferCardsUseCase;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by vipul.singhal on 30-05-2018.
 */

public class AddProductPricePresenter {

    GetOfferCardsUseCase getOfferCardsUseCase;
    AddProductPriceView addProductPriceView;
    Context context;

    @Inject
    AddProductPricePresenter(GetOfferCardsUseCase getOfferCardsUseCase) {
        this.getOfferCardsUseCase = getOfferCardsUseCase;
    }

    public void bind(AddProductPriceView addProductPriceView, Context context) {
        this.addProductPriceView = addProductPriceView;
        this.context = context;
    }

    @SuppressLint("RxLeakedSubscription")
    public void loadOfferCards() {
        getOfferCardsUseCase.execute(null, context)
                .doOnSubscribe(disposable -> addProductPriceView.showLoadingOffersProgress(true))
                .subscribe(this::handleOfferCardsResponse, throwable -> handleOfferCardsError(throwable));
    }

    private void handleOfferCardsResponse(List<OfferDataModel> offerDataModels) {
        Log.d("AddProductPricePresent_", "" + offerDataModels.size());
        addProductPriceView.showLoadingOffersProgress(false);
        addProductPriceView.showOfferCards(offerDataModels);
    }

    private void handleOfferCardsError(Throwable throwable) {
        Log.d("AddProductPricePresent_", "" + throwable.getMessage());
        addProductPriceView.showLoadingOffersProgress(false);
    }
}
