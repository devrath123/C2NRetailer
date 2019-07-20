package com.example.c2n.customer_single_product.di;

import com.example.c2n.customer_single_product.presenter.CustomerSingleProductActivity;

import dagger.Component;

@Component(modules = CustomerSingleProductModule.class)
public interface CustomerSingleProductComponent {

    void inject(CustomerSingleProductActivity customerSingleProductActivity);
}
