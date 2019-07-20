package com.example.c2n.core.models;

import com.squareup.moshi.Json;

import java.util.List;

public class ShopEntity {

    @Json(name = "shopImageURL")
    private String shopImageURL;
    @Json(name = "shopLongitude")
    private Double shopLongitude;
    @Json(name = "shopLatitude")
    private Double shopLatitude;
    @Json(name = "shopID")
    private String shopID;
    @Json(name = "productsList")
    private List<ProductEntity> productsList = null;
    @Json(name = "shopName")
    private String shopName;
    @Json(name = "shopCellNo")
    private String shopCellNo;
    @Json(name = "shopAddress")
    private String shopAddress;
    @Json(name = "offerDataModel")
    private Object offerDataModel;
    @Json(name = "retailerID")
    private String retailerID;
    @Json(name = "shopEmail")
    private String shopEmail;

    public String getShopImageURL() {
        return shopImageURL;
    }

    public void setShopImageURL(String shopImageURL) {
        this.shopImageURL = shopImageURL;
    }

    public Double getShopLongitude() {
        return shopLongitude;
    }

    public void setShopLongitude(Double shopLongitude) {
        this.shopLongitude = shopLongitude;
    }

    public Double getShopLatitude() {
        return shopLatitude;
    }

    public void setShopLatitude(Double shopLatitude) {
        this.shopLatitude = shopLatitude;
    }

    public String getShopID() {
        return shopID;
    }

    public void setShopID(String shopID) {
        this.shopID = shopID;
    }

    public List<ProductEntity> getProductsList() {
        return productsList;
    }

    public void setProductsList(List<ProductEntity> productsList) {
        this.productsList = productsList;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopCellNo() {
        return shopCellNo;
    }

    public void setShopCellNo(String shopCellNo) {
        this.shopCellNo = shopCellNo;
    }

    public String getShopAddress() {
        return shopAddress;
    }

    public void setShopAddress(String shopAddress) {
        this.shopAddress = shopAddress;
    }

    public Object getOfferDataModel() {
        return offerDataModel;
    }

    public void setOfferDataModel(Object offerDataModel) {
        this.offerDataModel = offerDataModel;
    }

    public String getRetailerID() {
        return retailerID;
    }

    public void setRetailerID(String retailerID) {
        this.retailerID = retailerID;
    }

    public String getShopEmail() {
        return shopEmail;
    }

    public void setShopEmail(String shopEmail) {
        this.shopEmail = shopEmail;
    }


}
