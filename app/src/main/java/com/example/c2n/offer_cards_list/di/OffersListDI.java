package com.example.c2n.offer_cards_list.di;


/**
 * Created by shivani.singh on 29-06-2018.
 */

public class OffersListDI {
    private static OffersListComponent component;

    public static OffersListComponent getOfferComponent(){
        if (component == null){
            component = DaggerOffersListComponent.builder().build();

        }
        return component;
    }


}
