package com.example.c2n.core.model1;

import java.io.Serializable;

/**
 * Created by shivani.singh on 20-07-2018.
 */

public class ShopDataModel implements Serializable {

    private String retailerID;
    private String shopName;
    private String shopAddress;
    private String shopCellNo;
    private String shopID;
    private String shopImageURL;
    private double shopLatitude;
    private double shopLongitude;

    public ShopDataModel() {
        retailerID = "";
        shopName = "";
        shopAddress = "";
        shopCellNo = "";
        shopID = "";
        shopImageURL = "";
        shopLatitude = 0;
        shopLongitude = 0;
    }

    public String getRetailerID() {
        return retailerID;
    }

    public void setRetailerID(String retailerID) {
        this.retailerID = retailerID;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopAddress() {
        return shopAddress;
    }

    public void setShopAddress(String shopAddress) {
        this.shopAddress = shopAddress;
    }

    public String getShopCellNo() {
        return shopCellNo;
    }

    public void setShopCellNo(String shopCellNo) {
        this.shopCellNo = shopCellNo;
    }

    public String getShopID() {
        return shopID;
    }

    public void setShopID(String shopID) {
        this.shopID = shopID;
    }

    public String getShopImageURL() {
        return shopImageURL;
    }

    public void setShopImageURL(String shopImageURL) {
        this.shopImageURL = shopImageURL;
    }

    public double getShopLatitude() {
        return shopLatitude;
    }

    public void setShopLatitude(double shopLatitude) {
        this.shopLatitude = shopLatitude;
    }

    public double getShopLongitude() {
        return shopLongitude;
    }

    public void setShopLongitude(double shopLongitude) {
        this.shopLongitude = shopLongitude;
    }

    @Override
    public String toString() {
        return "ShopDataModel{" +
                "retailerID='" + retailerID + '\'' +
                ", shopName='" + shopName + '\'' +
                ", shopAddress='" + shopAddress + '\'' +
                ", shopCellNo='" + shopCellNo + '\'' +
                ", shopID='" + shopID + '\'' +
                ", shopImageURL='" + shopImageURL + '\'' +
                ", shopLatitude=" + shopLatitude +
                ", shopLongitude=" + shopLongitude +
                '}';
    }
}
