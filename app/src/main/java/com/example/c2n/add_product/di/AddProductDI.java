package com.example.c2n.add_product.di;

/**
 * Created by vipul.singhal on 29-05-2018.
 */

public class AddProductDI {
    private static AddProductComponent component;

    public static AddProductComponent getAddProductComponent() {
        if (component == null) {
            component = DaggerAddProductComponent.builder().build();
        }
        return component;
    }
}
