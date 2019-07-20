package com.example.c2n.customer_cart.presenter;

import com.example.c2n.core.models.MasterProductDataModel;
import com.example.c2n.core.models.ShopDataModel;

import java.util.List;

public interface CustomerCartView {
    String getProductsList();

    double getLongitude();

    double getLatitude();

    int getRadious();

    void showProgress(boolean flag, String msg);

    void loadShops(List<ShopDataModel> shopDataModels);

    void loadMylist(List<MasterProductDataModel> productIDs);

    void showErrorMsg();

    String getRetailerIDs();

    String getRate();

    String getUserName();
}
