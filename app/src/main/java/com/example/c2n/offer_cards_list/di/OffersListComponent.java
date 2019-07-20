package com.example.c2n.offer_cards_list.di;

import com.example.c2n.addproduct.presenter.AddProductPriceFragment;
import com.example.c2n.offer_cards_list.presenter.OffersListActivity;
import com.example.c2n.retailerhome.presenter.RetailerHomeFragment;
import com.example.c2n.view_product.presenter.AppyOfferCardBottomFragment;

import dagger.Component;

/**
 * Created by shivani.singh on 29-06-2018.
 */

@Component(modules = OffersListModule.class)
public interface OffersListComponent {
    void inject(OffersListActivity offersListActivity);

    void inject(AddProductPriceFragment addProductPriceFragment);

    void inject(AppyOfferCardBottomFragment appyOfferCardBottomFragment);

    void inject(RetailerHomeFragment retailerHomeFragment);
}
