package com.example.c2n.customer_cart.di;

import com.example.c2n.customer_cart.presenter.CustomerCartActivity;

import dagger.Component;

@Component(modules = CustomerCartModule.class)
public interface CustomerCartComponent {
    void inject(CustomerCartActivity customerCartActivity);
}
