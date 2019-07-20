package com.example.c2n.core.models;

public class RetailerDealDataModel {

    private String key;
    private int percent;
    private String status;
    private ShopDataModel shopDataModel;
    private String userName;
    private String userID;

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getPercent() {
        return percent;
    }

    public void setPercent(int percent) {
        this.percent = percent;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ShopDataModel getShopDataModel() {
        return shopDataModel;
    }

    public void setShopDataModel(ShopDataModel shopDataModel) {
        this.shopDataModel = shopDataModel;
    }

    @Override
    public String toString() {
        return "RetailerDealDataModel{" +
                "key='" + key + '\'' +
                ", percent=" + percent +
                ", status='" + status + '\'' +
                ", shopDataModel=" + shopDataModel +
                ", userName='" + userName + '\'' +
                ", userID='" + userID + '\'' +
                '}';
    }
}
