package com.example.c2n.core.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class CartProductDataModel {

//    @ColumnInfo(name = "retailer_id")
//    private String retailerID;
//
//    @ColumnInfo(name = "shop_id")
//    private String shopID;

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "product_id")
    private String productID;

    @NonNull
    public String getProductID() {
        return productID;
    }

    public void setProductID(@NonNull String productID) {
        this.productID = productID;
    }

    @Override
    public String toString() {
        return "CartProductDataModel{" +
                "productID='" + productID + '\'' +
                '}';
    }

    //    @ColumnInfo(name = "product_name")
//    private String productName;
//
//    @ColumnInfo(name = "product_image_url")
//    private String productImageURL;
//
//    @ColumnInfo(name = "product_mrp")
//    private double productMRP;
//
//    @ColumnInfo(name = "product_category")
//    private String productCategory;
//
//    @ColumnInfo(name = "product_description")
//    private String productDescription;
//
//    @ColumnInfo(name = "product_stock_status")
//    private Boolean productStockStatus;
//
//    @ColumnInfo(name = "product_offer_status")
//    private Boolean productOfferStatus;
//
//    @ColumnInfo(name = "offer_id")
//    private String offerID;
//
//    @ColumnInfo(name = "offer_discount")
//    private int offerDiscount;
//
//    @ColumnInfo(name = "offer_start_date")
//    private Date offerStartDate;
//
//    @ColumnInfo(name = "offer_end_date")
//    private Date offerEndDate;
//
//    @ColumnInfo(name = "offer_name")
//    private String offerName;
//
//    @ColumnInfo(name = "sun")
//    private boolean sun;
//
//    @ColumnInfo(name = "mon")
//    private boolean mon;
//
//    @ColumnInfo(name = "tue")
//    private boolean tue;
//
//    @ColumnInfo(name = "wed")
//    private boolean wed;
//
//    @ColumnInfo(name = "thu")
//    private boolean thu;
//
//    @ColumnInfo(name = "fri")
//    private boolean fri;
//
//    @ColumnInfo(name = "sat")
//    private boolean sat;
//
//    @ColumnInfo(name = "offer_status")
//    private boolean offerStatus;
//
//    public CartProductDataModel() {
//    }
//
//    public String getRetailerID() {
//        return retailerID;
//    }
//
//    public void setRetailerID(String retailerID) {
//        this.retailerID = retailerID;
//    }
//
//    public String getShopID() {
//        return shopID;
//    }
//
//    public void setShopID(String shopID) {
//        this.shopID = shopID;
//    }
//
//    @NonNull
//    public String getProductID() {
//        return productID;
//    }
//
//    public void setProductID(@NonNull String productID) {
//        this.productID = productID;
//    }
//
//    public String getProductName() {
//        return productName;
//    }
//
//    public void setProductName(String productName) {
//        this.productName = productName;
//    }
//
//    public String getProductImageURL() {
//        return productImageURL;
//    }
//
//    public void setProductImageURL(String productImageURL) {
//        this.productImageURL = productImageURL;
//    }
//
//    public double getProductMRP() {
//        return productMRP;
//    }
//
//    public void setProductMRP(double productMRP) {
//        this.productMRP = productMRP;
//    }
//
//    public String getProductCategory() {
//        return productCategory;
//    }
//
//    public void setProductCategory(String productCategory) {
//        this.productCategory = productCategory;
//    }
//
//    public String getProductDescription() {
//        return productDescription;
//    }
//
//    public void setProductDescription(String productDescription) {
//        this.productDescription = productDescription;
//    }
//
//    public Boolean getProductStockStatus() {
//        return productStockStatus;
//    }
//
//    public void setProductStockStatus(Boolean productStockStatus) {
//        this.productStockStatus = productStockStatus;
//    }
//
//    public Boolean getProductOfferStatus() {
//        return productOfferStatus;
//    }
//
//    public void setProductOfferStatus(Boolean productOfferStatus) {
//        this.productOfferStatus = productOfferStatus;
//    }
//
//    public String getOfferID() {
//        return offerID;
//    }
//
//    public void setOfferID(String offerID) {
//        this.offerID = offerID;
//    }
//
//    public int getOfferDiscount() {
//        return offerDiscount;
//    }
//
//    public void setOfferDiscount(int offerDiscount) {
//        this.offerDiscount = offerDiscount;
//    }
//
//    public Date getOfferStartDate() {
//        return offerStartDate;
//    }
//
//    public void setOfferStartDate(Date offerStartDate) {
//        this.offerStartDate = offerStartDate;
//    }
//
//    public Date getOfferEndDate() {
//        return offerEndDate;
//    }
//
//    public void setOfferEndDate(Date offerEndDate) {
//        this.offerEndDate = offerEndDate;
//    }
//
//    public String getOfferName() {
//        return offerName;
//    }
//
//    public void setOfferName(String offerName) {
//        this.offerName = offerName;
//    }
//
//    public boolean isSun() {
//        return sun;
//    }
//
//    public void setSun(boolean sun) {
//        this.sun = sun;
//    }
//
//    public boolean isMon() {
//        return mon;
//    }
//
//    public void setMon(boolean mon) {
//        this.mon = mon;
//    }
//
//    public boolean isTue() {
//        return tue;
//    }
//
//    public void setTue(boolean tue) {
//        this.tue = tue;
//    }
//
//    public boolean isWed() {
//        return wed;
//    }
//
//    public void setWed(boolean wed) {
//        this.wed = wed;
//    }
//
//    public boolean isThu() {
//        return thu;
//    }
//
//    public void setThu(boolean thu) {
//        this.thu = thu;
//    }
//
//    public boolean isFri() {
//        return fri;
//    }
//
//    public void setFri(boolean fri) {
//        this.fri = fri;
//    }
//
//    public boolean isSat() {
//        return sat;
//    }
//
//    public void setSat(boolean sat) {
//        this.sat = sat;
//    }
//
//    public boolean isOfferStatus() {
//        return offerStatus;
//    }
//
//    public void setOfferStatus(boolean offerStatus) {
//        this.offerStatus = offerStatus;
//    }
//
//    @Override
//    public String toString() {
//        return "CartProductDataModel{" +
//                "retailerID='" + retailerID + '\'' +
//                ", shopID='" + shopID + '\'' +
//                ", productID='" + productID + '\'' +
//                ", productName='" + productName + '\'' +
//                ", productImageURL='" + productImageURL + '\'' +
//                ", productMRP=" + productMRP +
//                ", productCategory='" + productCategory + '\'' +
//                ", productDescription='" + productDescription + '\'' +
//                ", productStockStatus=" + productStockStatus +
//                ", productOfferStatus=" + productOfferStatus +
//                ", offerID='" + offerID + '\'' +
//                ", offerDiscount=" + offerDiscount +
//                ", offerStartDate=" + offerStartDate +
//                ", offerEndDate=" + offerEndDate +
//                ", offerName='" + offerName + '\'' +
//                ", sun=" + sun +
//                ", mon=" + mon +
//                ", tue=" + tue +
//                ", wed=" + wed +
//                ", thu=" + thu +
//                ", fri=" + fri +
//                ", sat=" + sat +
//                ", offerStatus=" + offerStatus +
//                '}';
//    }
}
