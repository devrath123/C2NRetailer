package com.example.c2n.customer_deal.di;

public class CustomerDealDI {
    private static CustomerDealComponent component;

    public static CustomerDealComponent getCustomerDealComponent() {
        if (component == null) {
            component = DaggerCustomerDealComponent.builder().build();
        }
        return component;
    }
}
