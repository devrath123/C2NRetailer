package com.example.c2n.retailerhome.di;

import com.example.c2n.add_product.presenter.AddProductPriceFragment;
import com.example.c2n.retailerhome.presenter.RetailerHomeFragment;
import com.example.c2n.view_product.presenter.AppyOfferCardBottomFragment;

import dagger.Component;

/**
 * Created by shivani.singh on 21-06-2018.
 */

@Component(modules = RetailerHomeModule.class)
public interface RetailerHomeComponent {
    void inject(RetailerHomeFragment retailerHomeFragment);

    void inject(AddProductPriceFragment addProductPriceFragment);

    void inject(AppyOfferCardBottomFragment appyOfferCardBottomFragment);
}
