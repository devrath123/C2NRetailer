package com.example.c2n.viewshopdetails.di;

/**
 * Created by roshan.nimje on 24-05-2018.
 */

import com.example.c2n.viewshopdetails.presenter.ViewShopActivity;
import com.example.c2n.viewshopdetails.presenter.ViewShoppFragment;

import dagger.Component;

@Component(modules = ViewShopModule.class)
public interface ViewShopComponent {
    void inject(ViewShoppFragment viewShoppFragment);
}
