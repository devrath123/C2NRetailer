package com.example.c2n.core.models;

import com.squareup.moshi.Json;

public class OfferEntity {

    @Json(name = "offerDiscount")
    private Integer offerDiscount;
    @Json(name = "shopDataModels")
    private Object shopDataModels;
    @Json(name = "mon")
    private Boolean mon;
    @Json(name = "sun")
    private Boolean sun;
    @Json(name = "fri")
    private Boolean fri;
    @Json(name = "tue")
    private Boolean tue;
    @Json(name = "offerStatus")
    private Boolean offerStatus;
    @Json(name = "wed")
    private Boolean wed;
    @Json(name = "offerName")
    private String offerName;
    @Json(name = "thu")
    private Boolean thu;
    @Json(name = "sat")
    private Boolean sat;
    @Json(name = "offerEndDate")
    private OfferEndDateEntity offerEndDate;
    @Json(name = "offerStartDate")
    private OfferStartDateEntity offerStartDate;
    @Json(name = "offerID")
    private String offerID;

    public Integer getOfferDiscount() {
        return offerDiscount;
    }

    public void setOfferDiscount(Integer offerDiscount) {
        this.offerDiscount = offerDiscount;
    }

    public Object getShopDataModels() {
        return shopDataModels;
    }

    public void setShopDataModels(Object shopDataModels) {
        this.shopDataModels = shopDataModels;
    }

    public Boolean getMon() {
        return mon;
    }

    public void setMon(Boolean mon) {
        this.mon = mon;
    }

    public Boolean getSun() {
        return sun;
    }

    public void setSun(Boolean sun) {
        this.sun = sun;
    }

    public Boolean getFri() {
        return fri;
    }

    public void setFri(Boolean fri) {
        this.fri = fri;
    }

    public Boolean getTue() {
        return tue;
    }

    public void setTue(Boolean tue) {
        this.tue = tue;
    }

    public Boolean getOfferStatus() {
        return offerStatus;
    }

    public void setOfferStatus(Boolean offerStatus) {
        this.offerStatus = offerStatus;
    }

    public Boolean getWed() {
        return wed;
    }

    public void setWed(Boolean wed) {
        this.wed = wed;
    }

    public String getOfferName() {
        return offerName;
    }

    public void setOfferName(String offerName) {
        this.offerName = offerName;
    }

    public Boolean getThu() {
        return thu;
    }

    public void setThu(Boolean thu) {
        this.thu = thu;
    }

    public Boolean getSat() {
        return sat;
    }

    public void setSat(Boolean sat) {
        this.sat = sat;
    }

    public OfferEndDateEntity getOfferEndDate() {
        return offerEndDate;
    }

    public void setOfferEndDate(OfferEndDateEntity offerEndDate) {
        this.offerEndDate = offerEndDate;
    }

    public OfferStartDateEntity getOfferStartDate() {
        return offerStartDate;
    }

    public void setOfferStartDate(OfferStartDateEntity offerStartDate) {
        this.offerStartDate = offerStartDate;
    }


//    public String getOfferEndDate() {
//        return offerEndDate;
//    }
//
//    public void setOfferEndDate(String offerEndDate) {
//        this.offerEndDate = offerEndDate;
//    }
//
//    public String getOfferStartDate() {
//        return offerStartDate;
//    }
//
//    public void setOfferStartDate(String offerStartDate) {
//        this.offerStartDate = offerStartDate;
//    }

    public String getOfferID() {
        return offerID;
    }

    public void setOfferID(String offerID) {
        this.offerID = offerID;
    }

}
