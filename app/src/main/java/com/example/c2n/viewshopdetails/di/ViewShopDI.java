package com.example.c2n.viewshopdetails.di;

/**
 * Created by roshan.nimje on 24-05-2018.
 */

public class ViewShopDI {
    private static ViewShopComponent viewShopComponent;

    public static ViewShopComponent getViewShopComponent() {
        if (viewShopComponent == null) {
            viewShopComponent = DaggerViewShopComponent.builder().build();
        }
        return viewShopComponent;
    }
}
