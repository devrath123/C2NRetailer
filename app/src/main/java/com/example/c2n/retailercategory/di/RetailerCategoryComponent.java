package com.example.c2n.retailercategory.di;

import com.example.c2n.addproduct.presenter.AddProductCategoryFragment;
import com.example.c2n.customer_home.presenter.CustomerHomeFragment;
import com.example.c2n.customer_single_shop_products.presenter.CustomerSingleShopProductsActivity;
import com.example.c2n.retailer_offer_products.presenter.presenter.presenter.OfferProductsActivity;
import com.example.c2n.retailercategory.presenter.RetailerCategoryFragment;

import dagger.Component;

/**
 * Created by roshan.nimje on 18-06-2018.
 */

@Component(modules = RetailerCategoryModule.class)
public interface RetailerCategoryComponent {
    void inject(RetailerCategoryFragment retailerCategoryFragment);

    void inject(OfferProductsActivity offerProductsActivity);

    void inject(CustomerHomeFragment customerHomeFragment);

    void inject(CustomerSingleShopProductsActivity customerSingleShopProductsActivity);

    void inject(AddProductCategoryFragment addProductInfoFragment);
}
