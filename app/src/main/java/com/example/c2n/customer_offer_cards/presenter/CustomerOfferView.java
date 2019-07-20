package com.example.c2n.customer_offer_cards.presenter;

import com.example.c2n.core.model1.OfferDataModel;
import com.example.c2n.core.model1.OfferDetailsDataModel;
import com.example.c2n.core.model1.ProductDataModel;

import java.util.HashMap;
import java.util.List;

/**
 * Created by shivani.singh on 28-08-2018.
 */

public interface CustomerOfferView {
    void loadOffers( List<HashMap<OfferDataModel,List<ProductDataModel>>> offerDetailsDataModels);

    void showProgress(Boolean flag);

    String shopId();

    String productID();



}
