package com.example.c2n.retailer_deal.di;

import com.example.c2n.retailer_deal.presenter.RetailerDealFragment;

import dagger.Component;

@Component(modules = RetailerDealModule.class)
public interface RetailerDealComponent {
    void inject(RetailerDealFragment retailerDealFragment);
}
