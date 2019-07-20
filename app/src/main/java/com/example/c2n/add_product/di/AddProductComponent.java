package com.example.c2n.add_product.di;

import com.example.c2n.add_product.presenter.AddProductResultFragment;
import com.example.c2n.add_product.presenter.ProductCategoryNameBottomFragment;

import dagger.Component;

/**
 * Created by vipul.singhal on 29-05-2018.
 */

@Component(modules = AddProductModule.class)
public interface AddProductComponent {

    void inject(ProductCategoryNameBottomFragment productCategoryNameBottomFragment);

    void inject(AddProductResultFragment addProductResultFragment);
}
