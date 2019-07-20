package com.example.c2n.customer_offer_cards.di;

/**
 * Created by shivani.singh on 28-08-2018.
 */

public class CustomerOfferDI {
    private static CustomerOfferComponent component;

    public static CustomerOfferComponent getComponent(){
        if (component == null)
        {
            component = DaggerCustomerOfferComponent.builder().build();
        }
        return component;
    }
}
