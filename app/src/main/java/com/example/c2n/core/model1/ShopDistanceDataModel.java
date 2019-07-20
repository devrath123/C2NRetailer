package com.example.c2n.core.model1;

import java.io.Serializable;

/**
 * Created by shivani.singh on 20-07-2018.
 */

public class ShopDistanceDataModel implements Serializable {

    private double shopDistance;
    private ShopDataModel shopDataModel;

    public double getShopDistance() {
        return shopDistance;
    }

    public void setShopDistance(double shopDistance) {
        this.shopDistance = shopDistance;
    }

    public ShopDataModel getShopDataModel() {
        return shopDataModel;
    }

    public void setShopDataModel(ShopDataModel shopDataModel) {
        this.shopDataModel = shopDataModel;
    }

    @Override
    public String toString() {
        return "ShopDistanceDataModel{" +
                "shopDistance=" + shopDistance +
                ", shopDataModel=" + shopDataModel +
                '}';
    }
}
