package com.example.c2n.view_product.di;

/**
 * Created by vipul.singhal on 24-06-2018.
 */

public class ViewUpdateProductDI {
    private static ViewUpdateProductComponent viewUpdateProductComponent;

    public static ViewUpdateProductComponent getViewUpdateProductComponent() {
        if (viewUpdateProductComponent == null) {
            viewUpdateProductComponent = DaggerViewUpdateProductComponent.builder().build();
        }
        return viewUpdateProductComponent;
    }
}
