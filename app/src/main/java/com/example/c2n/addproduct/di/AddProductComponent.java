package com.example.c2n.addproduct.di;

import com.example.c2n.addproduct.presenter.AddProductPriceFragment;

import dagger.Component;

@Component(modules = AddProductModule.class)
public interface AddProductComponent {

    void inject(AddProductPriceFragment addProductPriceFragment);
}
