package com.example.c2n.core.model1;

/**
 * Created by shivani.singh on 20-07-2018.
 */

public class CustomerDataModel {
    private String customerID;
    private String customerName;
    String customerDOB;
    private String customerMobileNo;
    private String customerAddress;
    private double customerLatitude;
    private double customerLongitude;
    private String customerImageURL;

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public String getCustomerDOB() {
        return customerDOB;
    }

    public void setCustomerDOB(String customerDOB) {
        this.customerDOB = customerDOB;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerMobileNo() {
        return customerMobileNo;
    }

    public void setCustomerMobileNo(String customerMobileNo) {
        this.customerMobileNo = customerMobileNo;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public double getCustomerLatitude() {
        return customerLatitude;
    }

    public void setCustomerLatitude(double customerLatitude) {
        this.customerLatitude = customerLatitude;
    }

    public double getCustomerLongitude() {
        return customerLongitude;
    }

    public void setCustomerLongitude(double customerLongitude) {
        this.customerLongitude = customerLongitude;
    }

    public String getCustomerImageURL() {
        return customerImageURL;
    }

    public void setCustomerImageURL(String customerImageURL) {
        this.customerImageURL = customerImageURL;
    }

    @Override
    public String toString() {
        return "CustomerDataModel{" +
                "customerID='" + customerID + '\'' +
                ", customerName='" + customerName + '\'' +
                ", customerDOB=" + customerDOB +
                ", customerMobileNo='" + customerMobileNo + '\'' +
                ", customerAddress='" + customerAddress + '\'' +
                ", customerLatitude=" + customerLatitude +
                ", customerLongitude=" + customerLongitude +
                ", customerImageURL='" + customerImageURL + '\'' +
                '}';
    }
}
