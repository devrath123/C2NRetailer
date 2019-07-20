package com.example.c2n.customer_home.di;

/**
 * Created by shivani.singh on 16-08-2018.
 */

public class CustomerHomeDI {

    private static CustomerHomeComponent customerHomeComponent;

    public static CustomerHomeComponent getCustomerHomeComponent() {
        if (customerHomeComponent == null) {
            customerHomeComponent = DaggerCustomerHomeComponent.builder().build();
        }
        return customerHomeComponent;
    }
}
