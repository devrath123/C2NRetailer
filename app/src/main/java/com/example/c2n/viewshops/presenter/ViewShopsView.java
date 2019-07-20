package com.example.c2n.viewshops.presenter;

import com.example.c2n.core.models.ShopDataModel;

import java.util.List;

/**
 * Created by roshan.nimje on 21-05-2018.
 */

public interface ViewShopsView {

    void showNoShopScreen();

    String getUserID();

    void loadShops();

    void showViewShopProgress(Boolean bool);

    void showShopsList(List<ShopDataModel> categoriesDataModels);

    void addShop();
}
