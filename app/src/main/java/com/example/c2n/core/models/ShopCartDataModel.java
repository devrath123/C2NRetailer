package com.example.c2n.core.models;

import com.bignerdranch.expandablerecyclerview.Model.ParentObject;

import java.util.List;

public class ShopCartDataModel implements ParentObject {

    private ShopDataModel shopDataModel;
    private double totalBill;
    private double distance;
    private List<Object> productDataModel;

    public ShopDataModel getShopDataModel() {
        return shopDataModel;
    }

    public void setShopDataModel(ShopDataModel shopDataModel) {
        this.shopDataModel = shopDataModel;
    }

    public List<Object> getProductDataModel() {
        return productDataModel;
    }

    public void setProductDataModel(List<Object> productDataModel) {
        this.productDataModel = productDataModel;
    }

    public double getTotalBill() {
        return totalBill;
    }

    public void setTotalBill(double totalBill) {
        this.totalBill = totalBill;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    @Override
    public List<Object> getChildObjectList() {
        return productDataModel;
    }

    @Override
    public void setChildObjectList(List<Object> list) {

    }
}
