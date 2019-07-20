package com.example.c2n.customer_home.di;

import com.example.c2n.customer_cart.presenter.CustomerCartActivity;
import com.example.c2n.customer_home.presenter.CustomerHomeFragment;
import com.example.c2n.customer_single_product.presenter.CustomerSingleProductActivity;
import com.example.c2n.customer_single_shop_products.presenter.CustomerSingleShopProductsActivity;
import com.example.c2n.nearby_shops.presentation.NearbyShopsFragment;

import dagger.Component;

/**
 * Created by shivani.singh on 16-08-2018.
 */

@Component(modules = CustomerHomeModule.class)
public interface CustomerHomeComponent {
    void inject(CustomerHomeFragment customerHomeFragment);

    void inject(NearbyShopsFragment nearbyShopsFragment);

    void inject(CustomerSingleShopProductsActivity customerSingleShopProductsActivity);

    void inject(CustomerCartActivity customerCartActivity);

    void inject(CustomerSingleProductActivity customerSingleProductActivity);
}
