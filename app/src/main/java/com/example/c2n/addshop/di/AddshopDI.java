package com.example.c2n.addshop.di;

/**
 * Created by roshan.nimje on 16-05-2018.
 */

public class AddshopDI {
    private static AddshopComponent component;

    public static AddshopComponent getAddshopComponent() {
        if (component == null) {
            component = DaggerAddshopComponent.builder().build();
        }
        return component;
    }
}
