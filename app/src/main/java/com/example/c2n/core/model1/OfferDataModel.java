package com.example.c2n.core.model1;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by shivani.singh on 20-07-2018.
 */

public class OfferDataModel implements Serializable, Comparable {

    String offerID = "";
    int offerDiscount;
    Date offerStartDate;
    Date offerEndDate;
    String offerName;
    boolean sun;
    boolean mon;
    boolean tue;
    boolean wed;
    boolean thu;
    boolean fri;
    boolean sat;
    boolean offerStatus;
    //    List<ShopOfferDataModel> offerProducts;
//    ShopOfferDataModel shopOfferDataModel;
    List<HashMap<String, List<String>>> offerProducts;

    public String getOfferID() {
        return offerID;
    }

    public void setOfferID(String offerID) {
        this.offerID = offerID;
    }

    public List<HashMap<String, List<String>>> getOfferProducts() {
        return offerProducts;
    }

    public void setOfferProducts(List<HashMap<String, List<String>>> offerProducts) {
        this.offerProducts = offerProducts;
    }

    public boolean isOfferStatus() {
        return offerStatus;
    }

    public void setOfferStatus(boolean offerStatus) {
        this.offerStatus = offerStatus;
    }

    public int getOfferDiscount() {
        return offerDiscount;
    }

    public void setOfferDiscount(int offerDiscount) {
        this.offerDiscount = offerDiscount;
    }

    public Date getOfferStartDate() {
        return offerStartDate;
    }

    public void setOfferStartDate(Date offerStartDate) {
        this.offerStartDate = offerStartDate;
    }

    public Date getOfferEndDate() {
        return offerEndDate;
    }

    public void setOfferEndDate(Date offerEndDate) {
        this.offerEndDate = offerEndDate;
    }

    public String getOfferName() {
        return offerName;
    }

    public void setOfferName(String offerName) {
        this.offerName = offerName;
    }

    public boolean isSun() {
        return sun;
    }

    public void setSun(boolean sun) {
        this.sun = sun;
    }

    public boolean isMon() {
        return mon;
    }

    public void setMon(boolean mon) {
        this.mon = mon;
    }

    public boolean isTue() {
        return tue;
    }

    public void setTue(boolean tue) {
        this.tue = tue;
    }

    public boolean isWed() {
        return wed;
    }

    public void setWed(boolean wed) {
        this.wed = wed;
    }

    public boolean isThu() {
        return thu;
    }

    public void setThu(boolean thu) {
        this.thu = thu;
    }

    public boolean isFri() {
        return fri;
    }

    public void setFri(boolean fri) {
        this.fri = fri;
    }

    public boolean isSat() {
        return sat;
    }

    public void setSat(boolean sat) {
        this.sat = sat;
    }


    public static Comparator<OfferDataModel> offerDiscountDescendingComparator = new Comparator<OfferDataModel>() {
        @Override
        public int compare(OfferDataModel o1, OfferDataModel o2) {
            Integer offer1Discount = o1.getOfferDiscount();
            Integer offer2Discount = o2.getOfferDiscount();
            return offer2Discount.compareTo(offer1Discount);
        }
    };
//
//    @Override
//    public int compareTo(OfferListDataModel offerListDataModel) {
//        int compareDiscount = Integer.parseInt(offerListDataModel.getOfferDiscount());
//
//        return Integer.parseInt(this.offerDiscount) - compareDiscount;
//    }

    @Override
    public int compareTo(@NonNull Object o) {

        OfferDataModel offerListDataModel = (OfferDataModel) o;
        int compareDiscount = offerListDataModel.getOfferDiscount();

        return this.offerDiscount - compareDiscount;
    }

    @Override
    public String toString() {
        return "OfferDataModel{" +
                "offerID='" + offerID + '\'' +
                ", offerDiscount=" + offerDiscount +
                ", offerStartDate=" + offerStartDate +
                ", offerEndDate=" + offerEndDate +
                ", offerName='" + offerName + '\'' +
                ", sun=" + sun +
                ", mon=" + mon +
                ", tue=" + tue +
                ", wed=" + wed +
                ", thu=" + thu +
                ", fri=" + fri +
                ", sat=" + sat +
                ", offerStatus=" + offerStatus +
                ", offerProducts=" + offerProducts +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OfferDataModel that = (OfferDataModel) o;

        return offerID.equals(that.offerID);
    }

    @Override
    public int hashCode() {
        return offerID.hashCode();
    }
}


