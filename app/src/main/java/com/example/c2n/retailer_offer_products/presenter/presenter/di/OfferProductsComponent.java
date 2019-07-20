package com.example.c2n.retailer_offer_products.presenter.presenter.di;

import com.example.c2n.retailer_offer_products.presenter.presenter.presenter.OfferProductsActivity;

import dagger.Component;

/**
 * Created by roshan.nimje on 04-07-2018.
 */

@Component(modules = OfferProductsModule.class)
public interface OfferProductsComponent {
    void inject(OfferProductsActivity offerProductsActivity);
}
