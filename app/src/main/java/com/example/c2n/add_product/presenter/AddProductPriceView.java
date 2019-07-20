package com.example.c2n.add_product.presenter;

import com.example.c2n.core.models.OfferDataModel;

import java.util.List;

/**
 * Created by vipul.singhal on 30-05-2018.
 */

public interface AddProductPriceView {

    void showOfferCards(List<OfferDataModel> offerCards);

    void showLoadingOffersProgress(Boolean progress);
}
