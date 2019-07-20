package com.example.c2n.customer_single_shop_products.di;

import com.example.c2n.customer_single_shop_products.presenter.CustomerSingleShopProductsActivity;

import dagger.Component;

@Component(modules = CustomerSingleShopProductsModule.class)
public interface CustomerSingleShopProductsComponent {

    void inject(CustomerSingleShopProductsActivity customerSingleShopProductsActivity);
}
