package com.example.c2n.retailer_shop_products_list.di;

/**
 * Created by vipul.singhal on 21-06-2018.
 */

public class RetailerShopProductsDI {
    private static RetailerShopProductsComponent retailerShopProductsComponent;

    public static RetailerShopProductsComponent getRetailerShopProductsComponent() {
        if (retailerShopProductsComponent == null) {
            retailerShopProductsComponent = DaggerRetailerShopProductsComponent.builder().build();
        }
        return retailerShopProductsComponent;
    }
}
