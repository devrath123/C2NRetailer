package com.example.c2n.core.model1;

/**
 * Created by shivani.singh on 20-07-2018.
 */

public class RetailerDataModel {

    String retailerID;
    String retailerName;
    String retailerAddress;
    String retailerMobileNo;
    double retailerLatitude;
    double retailerLongitude;
    String retailerDOB;
    String retailerImageURL;

    public RetailerDataModel() {
        retailerID = "";
        retailerName = "";
        retailerAddress = "";
        retailerMobileNo = "";
        retailerLatitude = 0;
        retailerLongitude = 0;
        retailerDOB = "";
        retailerImageURL = "";
    }


    public String getRetailerID() {
        return retailerID;
    }

    public void setRetailerID(String retailerID) {
        this.retailerID = retailerID;
    }

    public String getRetailerName() {
        return retailerName;
    }

    public void setRetailerName(String retailerName) {
        this.retailerName = retailerName;
    }

    public String getRetailerAddress() {
        return retailerAddress;
    }

    public void setRetailerAddress(String retailerAddress) {
        this.retailerAddress = retailerAddress;
    }

    public String getRetailerMobileNo() {
        return retailerMobileNo;
    }

    public void setRetailerMobileNo(String retailerMobileNo) {
        this.retailerMobileNo = retailerMobileNo;
    }

    public double getRetailerLatitude() {
        return retailerLatitude;
    }

    public void setRetailerLatitude(double retailerLatitude) {
        this.retailerLatitude = retailerLatitude;
    }

    public double getRetailerLongitude() {
        return retailerLongitude;
    }

    public void setRetailerLongitude(double retailerLongitude) {
        this.retailerLongitude = retailerLongitude;
    }

    public String getRetailerDOB() {
        return retailerDOB;
    }

    public void setRetailerDOB(String retailerDOB) {
        this.retailerDOB = retailerDOB;
    }

    public String getRetailerImageURL() {
        return retailerImageURL;
    }

    public void setRetailerImageURL(String retailerImageURL) {
        this.retailerImageURL = retailerImageURL;
    }

    @Override
    public String toString() {
        return "RetailerDataModel{" +
                "retailerID='" + retailerID + '\'' +
                ", retailerName='" + retailerName + '\'' +
                ", retailerAddress='" + retailerAddress + '\'' +
                ", retailerMobileNo='" + retailerMobileNo + '\'' +
                ", retailerLatitude=" + retailerLatitude +
                ", retailerLongitude=" + retailerLongitude +
                ", retailerDOB='" + retailerDOB + '\'' +
                ", retailerImageURL='" + retailerImageURL + '\'' +
                '}';
    }
}
