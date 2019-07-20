package com.example.c2n.core.model1;

/**
 * Created by vipul.singhal on 04-09-2018.
 */

public class OfferProductDataModel {
    private String retailerID;
    private String shopID;
    private String productID;


    public String getRetailerID() {
        return retailerID;
    }

    public void setRetailerID(String retailerID) {
        this.retailerID = retailerID;
    }

    public String getShopID() {
        return shopID;
    }

    public void setShopID(String shopID) {
        this.shopID = shopID;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    @Override
    public String toString() {
        return "OfferProductDataModel{" +
                "retailerID='" + retailerID + '\'' +
                ", shopID='" + shopID + '\'' +
                ", productID='" + productID + '\'' +
                '}';
    }
}
