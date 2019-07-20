package com.example.c2n.view_product.di;

import com.example.c2n.view_product.presenter.AppyOfferCardBottomFragment;
import com.example.c2n.view_product.presenter.ViewProductActivity;

import dagger.Component;

/**
 * Created by vipul.singhal on 24-06-2018.
 */
@Component(modules = ViewUpdateProductModule.class)
public interface ViewUpdateProductComponent {
    void inject(ViewProductActivity viewProductActivity);

    void inject(AppyOfferCardBottomFragment appyOfferCardBottomFragment);
}
