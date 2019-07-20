package com.example.c2n.view_product.presenter.view;

import com.example.c2n.core.models.OfferDataModel;
import com.example.c2n.core.models.ProductDataModel;
import com.example.c2n.core.models.ShopDataModel;

import java.util.List;

/**
 * Created by vipul.singhal on 09-07-2018.
 */

public interface ApplyOfferCardBottomFragmentView {
    void showOfferCards(List<OfferDataModel> data);

    void showUpdateProductProgress(Boolean bool);

    void isProductUpdationSuccess(Boolean bool);

    void getDocumentIdSuccess(String id);

    String getProductDocumentId();

    void showProgress(Boolean show);

    String getShopEmail();

    ProductDataModel getProductDataModel();

    ShopDataModel getShopDataModel();

    OfferDataModel getOfferListDataModel();

    String getShopID();

    String getProductCategory();

    OfferDataModel getProductOffer();


}
