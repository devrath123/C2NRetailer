package com.example.c2n.customer_products_list.di;

/**
 * Created by vipul.singhal on 23-05-2018.
 */

public class ProductsListDI {
    private static ProductsListComponent component;

    public static ProductsListComponent getProductsListComponent() {
        if (component == null) {
            component = DaggerProductsListComponent.builder().build();
        }
        return component;
    }
}
