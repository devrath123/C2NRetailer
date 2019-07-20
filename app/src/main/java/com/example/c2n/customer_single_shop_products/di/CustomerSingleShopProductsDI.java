package com.example.c2n.customer_single_shop_products.di;

public class CustomerSingleShopProductsDI {
    private static CustomerSingleShopProductsComponent customerSingleShopProductsComponent;

    public static CustomerSingleShopProductsComponent getCustomerSingleShopProductsComponent() {
        if (customerSingleShopProductsComponent == null) {
            customerSingleShopProductsComponent = DaggerCustomerSingleShopProductsComponent.builder().build();
        }
        return customerSingleShopProductsComponent;
    }
}
