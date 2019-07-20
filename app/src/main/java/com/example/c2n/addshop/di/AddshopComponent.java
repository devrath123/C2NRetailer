package com.example.c2n.addshop.di;

import com.example.c2n.addshop.presenter.AddShop1Fragment;

import dagger.Component;

/**
 * Created by roshan.nimje on 16-05-2018.
 */

@Component(modules = AddshopModule.class)
public interface AddshopComponent {
    void inject(AddShop1Fragment addShop1Fragment);
}
