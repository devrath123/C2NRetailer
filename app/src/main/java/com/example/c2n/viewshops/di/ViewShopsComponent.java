package com.example.c2n.viewshops.di;

import com.example.c2n.retailer_offer_products.presenter.presenter.presenter.OfferProductsActivity;
import com.example.c2n.retailer_shop_products_list.presenter.RetailerShopProductsActivity;
import com.example.c2n.retailercategory.presenter.RetailerCategoryFragment;
import com.example.c2n.retailerhome.presenter.RetailerHomeFragment;
//import com.example.c2n.viewshops.presenter.ViewShopsActivity;
import com.example.c2n.viewshops.presenter.ViewShopsFragment;

import dagger.Component;

/**
 * Created by roshan.nimje on 21-05-2018.
 */

@Component(modules = ViewShopsModule.class)
public interface ViewShopsComponent {
    void inject(ViewShopsFragment viewShopsActivity);
//    void inject(ViewShopsActivity viewShopsActivity);

    void inject(RetailerHomeFragment retailerHomeFragment);

    void inject(OfferProductsActivity offerProductsActivity);

    void inject(RetailerCategoryFragment retailerCategoryFragment);

    void inject(RetailerShopProductsActivity retailerShopProductsActivity);
}
