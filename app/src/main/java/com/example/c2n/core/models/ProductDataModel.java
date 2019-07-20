package com.example.c2n.core.models;

import java.io.Serializable;
import java.util.List;

/**
 * Created by shivani.singh on 20-07-2018.
 */

public class ProductDataModel implements Serializable {
    String productID = "";
    String productName;
    String productImageURL;
    double productMRP;
    String productCategory;
    String productDescription;
    Boolean productStockStatus;
    Boolean productOfferStatus;
    List<ShopDataModel> shopDataModels;
    OfferDataModel productOffer;

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productId) {
        this.productID = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductImageURL() {
        return productImageURL;
    }

    public void setProductImageURL(String productImageURL) {
        this.productImageURL = productImageURL;
    }

    public double getProductMRP() {
        return productMRP;
    }

    public void setProductMRP(double productMRP) {
        this.productMRP = productMRP;
    }

    public Boolean getProductOfferStatus() {
        return productOfferStatus;
    }

    public void setProductOfferStatus(Boolean productOfferStatus) {
        this.productOfferStatus = productOfferStatus;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public Boolean getProductStockStatus() {
        return productStockStatus;
    }

    public void setProductStockStatus(Boolean productStockStatus) {
        this.productStockStatus = productStockStatus;
    }

    public OfferDataModel getProductOffer() {
        return productOffer;
    }

    public void setProductOffer(OfferDataModel productOffer) {
        this.productOffer = productOffer;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public List<ShopDataModel> getShopDataModels() {
        return shopDataModels;
    }

    public void setShopDataModels(List<ShopDataModel> shopDataModels) {
        this.shopDataModels = shopDataModels;
    }

    @Override
    public String toString() {
        return "ProductDataModel{" +
                "productID='" + productID + '\'' +
                ", productName='" + productName + '\'' +
                ", productImageURL='" + productImageURL + '\'' +
                ", productMRP=" + productMRP +
                ", productCategory='" + productCategory + '\'' +
                ", productDescription='" + productDescription + '\'' +
                ", productStockStatus=" + productStockStatus +
                ", productOfferStatus=" + productOfferStatus +
                ", shopDataModels=" + shopDataModels +
                ", productOffer=" + productOffer +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductDataModel that = (ProductDataModel) o;

        return productID.equals(that.productID);
    }

    @Override
    public int hashCode() {
        return productID.hashCode();
    }
}