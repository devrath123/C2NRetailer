package com.example.c2n.retailer_offer_products.presenter.presenter.presenter;

import com.example.c2n.core.models.ProductDataModel;
import com.example.c2n.core.models.ShopDataModel;

import java.util.List;

/**
 * Created by roshan.nimje on 29-06-2018.
 */

public interface OfferProductsView {
    void getShopCategory();

    void showShopCategory(List<String> categoryDataModels);

    void getShopList();

    void showShopList(List<ShopDataModel> shopDataModels);

    String getShopID();

    void shopCategoryList(List<ProductDataModel> productDataModels);

    void showProgress(Boolean progress);

    void showUpdateProductOffer(Boolean progress);

    void openOfferCardsActivity();

    void setNoProductBackground();

    Object[] getProductIDs();

    String getOfferCardDocumentID();

    List<ProductDataModel> getProductList();

    void startOfferCardActivity();

    String getUserID();

    void datahanged(List<ProductDataModel> productDataModels);

    void notifyDataSetChnaged();

    void startApplyOffer();
}
