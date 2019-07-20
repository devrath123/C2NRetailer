package com.example.c2n.customer_cart.di;

public class CustomerCartDI {
    private static CustomerCartComponent customerCartComponent;

    public static CustomerCartComponent getCustomerCartComponent() {
        if (customerCartComponent == null) {
            customerCartComponent = DaggerCustomerCartComponent.builder().build();
        }
        return customerCartComponent;
    }
}
