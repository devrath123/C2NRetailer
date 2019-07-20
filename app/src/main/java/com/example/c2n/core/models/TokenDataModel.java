package com.example.c2n.core.models;

public class TokenDataModel {

    private String retailer;
    private String customer;

    public String getRetailer() {
        return retailer;
    }

    public void setRetailer(String retailer) {
        this.retailer = retailer;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    @Override
    public String toString() {
        return "TokenDataModel{" +
                "retailer='" + retailer + '\'' +
                ", customer='" + customer + '\'' +
                '}';
    }
}