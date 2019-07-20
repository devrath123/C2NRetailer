package com.example.c2n.retailer_shop_products_list.di;

import com.example.c2n.retailer_offer_products.presenter.presenter.presenter.OfferProductsActivity;
import com.example.c2n.retailer_shop_products_list.presenter.RetailerShopProductsActivity;

import dagger.Component;

/**
 * Created by vipul.singhal on 21-06-2018.
 */

@Component(modules = RetailerShopProductsModule.class)
public interface RetailerShopProductsComponent {
    void inject(RetailerShopProductsActivity retailerShopProductsActivity);

    void inject(OfferProductsActivity offerProductsActivity);

//    void inject(ProductStatusFragment productStatusFragment);
}
