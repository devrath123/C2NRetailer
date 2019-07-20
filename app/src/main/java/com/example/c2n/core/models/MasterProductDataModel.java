package com.example.c2n.core.models;

import java.io.Serializable;

public class MasterProductDataModel implements Serializable {

    private String productID;
    private String productDescription;
    private String productName;
//    private double productMRP;
    private String productCategory;
    private String productImageURL;

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

//    public double getProductMRP() {
//        return productMRP;
//    }
//
//    public void setProductMRP(double productMRP) {
//        this.productMRP = productMRP;
//    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public String getProductImageURL() {
        return productImageURL;
    }

    public void setProductImageURL(String productImageURL) {
        this.productImageURL = productImageURL;
    }

    @Override
    public String toString() {
        return "MasterProductDataModel{" +
                "productID='" + productID + '\'' +
                ", productDescription='" + productDescription + '\'' +
                ", productName='" + productName + '\'' +
                ", productCategory='" + productCategory + '\'' +
                ", productImageURL='" + productImageURL + '\'' +
                '}';
    }
}
