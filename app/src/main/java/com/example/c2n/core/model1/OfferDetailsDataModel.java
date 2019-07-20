package com.example.c2n.core.model1;

import com.example.c2n.core.model1.OfferDataModel;

/**
 * Created by shivani.singh on 29-08-2018.
 */

public class OfferDetailsDataModel {

    private String retailerID;
    private OfferDataModel offerDataModel;

    public String getRetailerID() {
        return retailerID;
    }

    public void setRetailerID(String retailerID) {
        this.retailerID = retailerID;
    }

    public OfferDataModel getOfferDataModel() {
        return offerDataModel;
    }

    public void setOfferDataModel(OfferDataModel offerDataModel) {
        this.offerDataModel = offerDataModel;
    }

    @Override
    public String toString() {
        return "OfferDetailsDataModel{" +
                "retailerID='" + retailerID + '\'' +
                ", offerDataModel=" + offerDataModel +
                '}';
    }
}
