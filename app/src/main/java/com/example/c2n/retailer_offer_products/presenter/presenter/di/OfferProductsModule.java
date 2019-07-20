package com.example.c2n.retailer_offer_products.presenter.presenter.di;

import com.example.c2n.core.usecase.AndroidUseCaseComposer;
import com.example.c2n.core.usecase.UseCaseComposer;

import dagger.Module;
import dagger.Provides;

/**
 * Created by roshan.nimje on 04-07-2018.
 */

@Module
public class OfferProductsModule {
    @Provides
    UseCaseComposer getUseCaseComposer() {
        return new AndroidUseCaseComposer();
    }
}
