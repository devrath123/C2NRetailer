package com.example.c2n.viewshops.di;

/**
 * Created by roshan.nimje on 21-05-2018.
 */

public class ViewShopsDI {
    private static ViewShopsComponent viewShopsComponent;

    public static ViewShopsComponent getViewShopsComponent() {
        if (viewShopsComponent == null) {
            viewShopsComponent = DaggerViewShopsComponent.builder().build();
        }
        return viewShopsComponent;
    }
}
