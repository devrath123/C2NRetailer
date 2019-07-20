package com.example.c2n.customer_products_list.di;

import com.example.c2n.customer_products_list.presenter.ProductsListFragment;

import dagger.Component;

/**
 * Created by vipul.singhal on 23-05-2018.
 */

@Component(modules = ProductsListModule.class)
public interface ProductsListComponent {
    void inject(ProductsListFragment productsListFragment);
}
