package com.example.c2n.core.models;

import com.squareup.moshi.Json;

public class ProductEntity {

    @Json(name = "productName")
    private String productName;
    @Json(name = "productOfferStatus")
    private Boolean productOfferStatus;
    @Json(name = "productOffer")
    private OfferEntity productOffer;
    @Json(name = "productDescription")
    private String productDescription;
    @Json(name = "shopDataModels")
    private Object shopDataModels;
    @Json(name = "productCategory")
    private String productCategory;
    @Json(name = "productID")
    private String productID;
    @Json(name = "productStockStatus")
    private Boolean productStockStatus;
    @Json(name = "productMRP")
    private Integer productMRP;
    @Json(name = "productImageURL")
    private String productImageURL;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Boolean getProductOfferStatus() {
        return productOfferStatus;
    }

    public void setProductOfferStatus(Boolean productOfferStatus) {
        this.productOfferStatus = productOfferStatus;
    }

    public OfferEntity getProductOffer() {
        return productOffer;
    }

    public void setProductOffer(OfferEntity productOffer) {
        this.productOffer = productOffer;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public Object getShopDataModels() {
        return shopDataModels;
    }

    public void setShopDataModels(Object shopDataModels) {
        this.shopDataModels = shopDataModels;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public Boolean getProductStockStatus() {
        return productStockStatus;
    }

    public void setProductStockStatus(Boolean productStockStatus) {
        this.productStockStatus = productStockStatus;
    }

    public Integer getProductMRP() {
        return productMRP;
    }

    public void setProductMRP(Integer productMRP) {
        this.productMRP = productMRP;
    }

    public String getProductImageURL() {
        return productImageURL;
    }

    public void setProductImageURL(String productImageURL) {
        this.productImageURL = productImageURL;
    }


}
