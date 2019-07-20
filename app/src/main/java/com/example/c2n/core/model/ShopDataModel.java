package com.example.c2n.core.model;

import java.io.Serializable;

/**
 * Created by roshan.nimje on 16-05-2018.
 */

@SuppressWarnings("serial") //With this annotation we are going to hide compiler warnings
public class ShopDataModel implements Serializable {

    private String shopDocumentID;
    private String retailerID;
    private String shopName;
    private String shopAddress;
    private String shopLandmark;
    private String shopCellNo1;
    private String shopCellNo2;
    private String shopEmail;
    private String shopImageURL;
    private double shopLatitude;
    private double shopLongitude;

    public ShopDataModel(String shopDocumentID, String shopName, String shopAddress, String shopLandmark, String shopCellNo1, String shopEmail, String shopImageURL, double shopLatitude, double shopLongitude) {
        this.shopDocumentID = shopDocumentID;
        this.shopName = shopName;
        this.shopAddress = shopAddress;
        this.shopLandmark = shopLandmark;
        this.shopCellNo1 = shopCellNo1;
        this.shopEmail = shopEmail;
        this.shopImageURL = shopImageURL;
        this.shopLatitude = shopLatitude;
        this.shopLongitude = shopLongitude;
    }

    public ShopDataModel(String shopDocumentID, String shopName, String shopAddress, String shopCellNo1, String shopEmail) {
        this.shopDocumentID = shopDocumentID;
        this.shopName = shopName;
        this.shopAddress = shopAddress;
        this.shopCellNo1 = shopCellNo1;
        this.shopEmail = shopEmail;
    }

    public ShopDataModel(String retailerID, String shopName, String shopAddress, String shopLandmark, String shopCellNo1, String shopEmail, double shopLatitude, double shopLongitude) {
        this.retailerID = retailerID;
        this.shopName = shopName;
        this.shopAddress = shopAddress;
        this.shopLandmark = shopLandmark;
        this.shopCellNo1 = shopCellNo1;
        this.shopEmail = shopEmail;
        this.shopLatitude = shopLatitude;
        this.shopLatitude = shopLatitude;
    }

    public ShopDataModel(String retailerID, String shopName, String shopAddress, String shopEmail, String shopCellNo1, String shopCellNo2) {
        this.retailerID = retailerID;
        this.shopName = shopName;
        this.shopAddress = shopAddress;
        this.shopEmail = shopEmail;
        this.shopCellNo1 = shopCellNo1;
        this.shopCellNo2 = shopCellNo2;
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

    public String getShopCellNo1() {
        return shopCellNo1;
    }

    public void setShopCellNo1(String shopCellNo1) {
        this.shopCellNo1 = shopCellNo1;
    }

    public String getShopCellNo2() {
        return shopCellNo2;
    }

    public void setShopCellNo2(String shopCellNo2) {
        this.shopCellNo2 = shopCellNo2;
    }

    public String getShopEmail() {
        return shopEmail;
    }

    public void setShopEmail(String shopEmail) {
        this.shopEmail = shopEmail;
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

    public String getRetailerID() {
        return retailerID;
    }

    public void setRetailerID(String retailerID) {
        this.retailerID = retailerID;
    }

    public String getShopDocumentID() {
        return shopDocumentID;
    }

    public void setShopDocumentID(String shopDocumentID) {
        this.shopDocumentID = shopDocumentID;
    }

    public String getShopImageURL() {
        return shopImageURL;
    }

    public void setShopImageURL(String shopImageURL) {
        this.shopImageURL = shopImageURL;
    }

    public String getShopLandmark() {
        return shopLandmark;
    }

    public void setShopLandmark(String shopLandmark) {
        this.shopLandmark = shopLandmark;
    }

    @Override
    public String toString() {
        return "ShopDataModel{" +
                "shopDocumentID='" + shopDocumentID + '\'' +
                ", retailerID='" + retailerID + '\'' +
                ", shopName='" + shopName + '\'' +
                ", shopAddress='" + shopAddress + '\'' +
                ", shopLandmark='" + shopLandmark + '\'' +
                ", shopCellNo1='" + shopCellNo1 + '\'' +
                ", shopCellNo2='" + shopCellNo2 + '\'' +
                ", shopEmail='" + shopEmail + '\'' +
                ", shopImageURL='" + shopImageURL + '\'' +
                ", shopLatitude=" + shopLatitude +
                ", shopLongitude=" + shopLongitude +
                '}';
    }
}
