package com.example.c2n.customer_deal.di;

import com.example.c2n.customer_cart.di.CustomerCartModule;
import com.example.c2n.customer_deal.presenter.CustomerDealFragment;

import dagger.Component;

@Component(modules = CustomerDealModule.class)
public interface CustomerDealComponent {
    void inject(CustomerDealFragment customerDealFragment);
}
