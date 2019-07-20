package com.example.c2n.core.models;


import java.io.Serializable;

public class ProductDetailsDataModel implements Serializable {

    private String retailerID;
    private String shopID;
    private boolean whishlisted;
    private ProductDataModel productDataModel;

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

    public boolean isWhishlisted() {
        return whishlisted;
    }

    public void setWhishlisted(boolean whishlisted) {
        this.whishlisted = whishlisted;
    }

    public ProductDataModel getProductDataModel() {
        return productDataModel;
    }

    public void setProductDataModel(ProductDataModel productDataModel) {
        this.productDataModel = productDataModel;
    }

    @Override
    public String toString() {
        return "ProductDetailsDataModel{" +
                "retailerID='" + retailerID + '\'' +
                ", shopID='" + shopID + '\'' +
                ", whishlisted=" + whishlisted +
                ", productDataModel=" + productDataModel +
                '}';
    }
}
