package com.example.c2n.core.model;

import java.io.Serializable;

/**
 * Created by vipul.singhal on 23-05-2018.
 */

public class ProductDataModel implements Serializable {
    String productOfferID;
    String productId;
    String productCategory;
    String productName;
    String productPhotoUrl;
    String productSupplier;
    String productManufacturer;
    String productManufacturingDate;
    String productExpiryDate;
    String productMRP;
    String productScheme;
    String shopEmail;
    String productDescription;
    String productDiscount = "";
    Boolean isChecked = false;

    public ProductDataModel(String productDiscount) {
        this.productDiscount = productDiscount;
    }

    public ProductDataModel(String temp, String productId, String productCategory, String shopEmail, String productDescription) {
        this.productId = productId;
        this.productCategory = productCategory;
        this.shopEmail = shopEmail;
        this.productDescription = productDescription;
    }

    public ProductDataModel(String shopemail, String productCategory, String productName, String productPhotoUrl, String productMRP, String productScheme) {
        this.shopEmail = shopemail;
        this.productCategory = productCategory;
        this.productName = productName;
        this.productPhotoUrl = productPhotoUrl;
        this.productMRP = productMRP;
        this.productScheme = productScheme;
    }

    public ProductDataModel(String shopemail, String productCategory, String productName, String productPhotoUrl, String productMRP, String productScheme, String productOfferID) {
        this.shopEmail = shopemail;
        this.productCategory = productCategory;
        this.productName = productName;
        this.productPhotoUrl = productPhotoUrl;
        this.productMRP = productMRP;
        this.productScheme = productScheme;
        this.productOfferID = productOfferID;
    }


    public ProductDataModel(String productId, String productCategory, String productName, String productPhotoUrl, String productMRP, String productScheme, String shopEmail, String productDescription) {
        this.productId = productId;
        this.productCategory = productCategory;
        this.productName = productName;
        this.productPhotoUrl = productPhotoUrl;
        this.productMRP = productMRP;
        this.productScheme = productScheme;
        this.shopEmail = shopEmail;
        this.productDescription = productDescription;
    }


    public ProductDataModel(String productOfferID, String productId, String productCategory, String productName, String productPhotoUrl, String productMRP, String productScheme, String shopEmail, String productDescription) {
        this.productOfferID = productOfferID;
        this.productId = productId;
        this.productCategory = productCategory;
        this.productName = productName;
        this.productPhotoUrl = productPhotoUrl;
        this.productMRP = productMRP;
        this.productScheme = productScheme;
        this.shopEmail = shopEmail;
        this.productDescription = productDescription;
    }

    public ProductDataModel(String productCategory, String productName, String productPhotoUrl, String productMRP) {
        this.productCategory = productCategory;
        this.productName = productName;
        this.productPhotoUrl = productPhotoUrl;
        this.productMRP = productMRP;
    }

    public ProductDataModel(String productId, String productCategory, String productName, String productPhotoUrl, String productSupplier, String productManufacturer, String productManufacturingDate, String productExpiryDate, String productMRP, String productScheme) {
        this.productId = productId;
        this.productCategory = productCategory;
        this.productName = productName;
        this.productPhotoUrl = productPhotoUrl;
        this.productSupplier = productSupplier;
        this.productManufacturer = productManufacturer;
        this.productManufacturingDate = productManufacturingDate;
        this.productExpiryDate = productExpiryDate;
        this.productMRP = productMRP;
        this.productScheme = productScheme;
    }

    public String getShopEmail() {
        return shopEmail;
    }

    public void setShopEmail(String shopEmail) {
        this.shopEmail = shopEmail;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductSupplier() {
        return productSupplier;
    }

    public void setProductSupplier(String productSupplier) {
        this.productSupplier = productSupplier;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getProductManufacturer() {
        return productManufacturer;
    }

    public void setProductManufacturer(String productManufacturer) {
        this.productManufacturer = productManufacturer;
    }

    public String getProductManufacturingDate() {
        return productManufacturingDate;
    }

    public void setProductManufacturingDate(String productManufacturingDate) {
        this.productManufacturingDate = productManufacturingDate;
    }

    public String getProductExpiryDate() {
        return productExpiryDate;
    }

    public void setProductExpiryDate(String productExpiryDate) {
        this.productExpiryDate = productExpiryDate;
    }

    public String getProductMRP() {
        return productMRP;
    }

    public void setProductMRP(String productMRP) {
        this.productMRP = productMRP;
    }

    public String getProductScheme() {
        return productScheme;
    }

    public void setProductScheme(String productScheme) {
        this.productScheme = productScheme;
    }

    public String getProductPhotoUrl() {
        return productPhotoUrl;
    }

    public void setProductPhotoUrl(String productPhotoUrl) {
        this.productPhotoUrl = productPhotoUrl;
    }

    @Override
    public String toString() {
        return "ProductDataModel{" +
                "productId='" + productId + '\'' +
                ", productOfferID='" + productOfferID + '\'' +
                ", productCategory='" + productCategory + '\'' +
                ", productName='" + productName + '\'' +
                ", productPhotoUrl='" + productPhotoUrl + '\'' +
                ", productSupplier='" + productSupplier + '\'' +
                ", productManufacturer='" + productManufacturer + '\'' +
                ", productManufacturingDate='" + productManufacturingDate + '\'' +
                ", productExpiryDate='" + productExpiryDate + '\'' +
                ", productMRP='" + productMRP + '\'' +
                ", productScheme='" + productScheme + '\'' +
                ", shopEmail='" + shopEmail + '\'' +
                ", productDescription='" + productDescription + '\'' +
                '}';
    }

    public Boolean getChecked() {
        return isChecked;
    }

    public void setChecked(Boolean checked) {
        isChecked = checked;
    }

    public String getProductOfferID() {
        return productOfferID;
    }

    public void setProductOfferID(String productOfferID) {
        this.productOfferID = productOfferID;
    }

    public String getProductDiscount() {
        return productDiscount;
    }

    public void setProductDiscount(String productDiscount) {
        this.productDiscount = productDiscount;
    }
}
