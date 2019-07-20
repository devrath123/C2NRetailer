package com.example.c2n.customer_offer_cards.di;

import com.example.c2n.customer_offer_cards.presenter.CustomerOfferFragment;

import dagger.Component;

/**
 * Created by shivani.singh on 28-08-2018.
 */

@Component(modules = CustomerOfferModule.class)
public interface CustomerOfferComponent {

    void inject(CustomerOfferFragment customerOfferFragment);
}
