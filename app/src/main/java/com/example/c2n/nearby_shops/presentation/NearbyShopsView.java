package com.example.c2n.nearby_shops.presentation;

import com.example.c2n.core.models.ShopDistanceDataModel;

import java.util.List;

/**
 * Created by shivani.singh on 22-08-2018.
 */

public interface NearbyShopsView {
    void loadShops(List<ShopDistanceDataModel> shopDataModels);

    int getRange();
}
