package com.example.c2n.make_offer_card.di;

import com.example.c2n.core.usecase.AndroidUseCaseComposer;
import com.example.c2n.core.usecase.UseCaseComposer;

import dagger.Module;
import dagger.Provides;

/**
 * Created by vipul.singhal on 02-07-2018.
 */
@Module
public class MakeOfferCardModule {
    @Provides
    UseCaseComposer getUseCaseComposer() {
        return new AndroidUseCaseComposer();
    }
}
