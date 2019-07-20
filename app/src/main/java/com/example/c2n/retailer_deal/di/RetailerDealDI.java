package com.example.c2n.retailer_deal.di;

public class RetailerDealDI {
    private static RetailerDealComponent component;

    public static RetailerDealComponent getRetailerDealComponent() {
        if (component == null) {
            component = DaggerRetailerDealComponent.builder().build();
        }
        return component;
    }
}
