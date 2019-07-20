package com.example.c2n.view_product.presenter.view;

import com.example.c2n.core.models.OfferDataModel;
import com.example.c2n.core.models.ProductDataModel;

/**
 * Created by vipul.singhal on 24-06-2018.
 */

public interface ViewUpdateProductView {
    void uploadImage();

    void updateProductDetails();

    void showUpdateProductProgress(Boolean bool);

    void showLoadingOfferProgress(Boolean bool);

    void isLoadOfferSuccess(OfferDataModel offercard);

    void isProductUpdationSuccess(Boolean bool);

    void getDocumentIdSuccess(String id);

    void setProductMrp(String price);

    void setProductDiscountedPrice(String discountedPrice);

    void setProductOffer(String offer);

    void setProductNameError();

    void setProductMrpError();

    String getShopEmail();

    String getProductCategory();

    String getProductDocumentId();

    String getProductImageURL();

    String getProductName();

    double getProductMrp();

    String getProductDiscountedPrice();

    String getProductOfferId();

    String getShopID();

    OfferDataModel getProductOffer();

//    Boolean getProductOfferStatus();

    String getProductDescription();

    void setProductDataModel(ProductDataModel productDataModel);

    void removeOfferSuccess();
}
