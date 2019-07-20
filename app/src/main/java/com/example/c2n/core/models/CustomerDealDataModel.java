package com.example.c2n.core.models;

public class CustomerDealDataModel {

    private int percent;
    private String status;
    private ShopDataModel shopDataModel;

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
        return "CustomerDealDataModel{" +
                "percent=" + percent +
                ", status='" + status + '\'' +
                ", shopDataModel=" + shopDataModel +
                '}';
    }
}
