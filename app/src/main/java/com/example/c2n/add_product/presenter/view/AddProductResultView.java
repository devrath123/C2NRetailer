package com.example.c2n.add_product.presenter.view;

import com.example.c2n.core.models.OfferDataModel;

/**
 * Created by vipul.singhal on 13-06-2018.
 */

public interface AddProductResultView {

    void updateProductDescriptionSuccess();

    void setProductDocumentID(String productDocumentID);

    String getShopEmail();

    String getProductName();

    String getProductCategory();

    String getProductID();

    double getProductMRP();

    String getProductPhotoUrl();

    OfferDataModel getProductOffer();

    String getProductDescription();

    void showAddProductProgress(Boolean bool, String msg);

    void isAddProductSuccess(Boolean bool);
}
