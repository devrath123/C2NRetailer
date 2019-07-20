package com.example.c2n.core.models;

import com.bignerdranch.expandablerecyclerview.Model.ParentObject;

import java.util.List;

public class ShopDealDataModel implements ParentObject {

    private ShopDataModel shopDataModel;
    private double totalBill;
    private String userName;
    private String discount;
    private String userID;
    private String key;
    private String status;
    private List<Object> productDataModel;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public List<Object> getChildObjectList() {
        return productDataModel;
    }

    @Override
    public void setChildObjectList(List<Object> list) {
    }
}