package com.example.c2n.core.models;

import java.io.Serializable;
import java.util.List;

/**
 * Created by shivani.singh on 20-07-2018.
 */

public class ShopDataModel implements Serializable {

    private String shopID;
    private String retailerID;
    private String shopName;
    private String shopAddress;
    private String shopCellNo;
    private String shopEmail;
    private String shopImageURL;
    private double shopLatitude;
    private double shopLongitude;
    private List<ProductDataModel> productsList;
    private OfferDataModel offerDataModel;
    private double productMRP;

    public String getShopID() {
        return shopID;
    }

    public void setShopID(String shopID) {
        this.shopID = shopID;
    }

    public List<ProductDataModel> getProductsList() {
        return productsList;
    }

    public OfferDataModel getOfferDataModel() {
        return offerDataModel;
    }

    public void setOfferDataModel(OfferDataModel offerDataModel) {
        this.offerDataModel = offerDataModel;
    }

    public void setProductsList(List<ProductDataModel> productsList) {
        this.productsList = productsList;
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

    public String getShopEmail() {
        return shopEmail;
    }

    public void setShopEmail(String shopEmail) {
        this.shopEmail = shopEmail;
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

    public double getProductMRP() {
        return productMRP;
    }

    public void setProductMRP(double productMRP) {
        this.productMRP = productMRP;
    }

    @Override
    public String toString() {
        return "ShopDataModel{" +
                "shopID='" + shopID + '\'' +
                ", retailerID='" + retailerID + '\'' +
                ", shopName='" + shopName + '\'' +
                ", shopAddress='" + shopAddress + '\'' +
                ", shopCellNo='" + shopCellNo + '\'' +
                ", shopEmail='" + shopEmail + '\'' +
                ", shopImageURL='" + shopImageURL + '\'' +
                ", shopLatitude=" + shopLatitude +
                ", shopLongitude=" + shopLongitude +
                ", productsList=" + productsList +
                ", offerDataModel=" + offerDataModel +
                ", productMRP=" + productMRP +
                '}';
    }
}
