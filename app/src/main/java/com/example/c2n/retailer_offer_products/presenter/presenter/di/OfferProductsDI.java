package com.example.c2n.retailer_offer_products.presenter.presenter.di;

/**
 * Created by roshan.nimje on 04-07-2018.
 */

public class OfferProductsDI {
    private static OfferProductsComponent component;

    public static OfferProductsComponent getOfferProductsComponent() {
        if (component == null) {
            component = DaggerOfferProductsComponent.builder().build();
        }
        return component;
    }
}
