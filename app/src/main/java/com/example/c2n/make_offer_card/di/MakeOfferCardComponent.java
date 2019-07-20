package com.example.c2n.make_offer_card.di;

import com.example.c2n.make_offer_card.presenter.MakeOfferCardActivity;
import com.example.c2n.offer_cards_list.presenter.OffersListActivity;

import dagger.Component;

/**
 * Created by vipul.singhal on 02-07-2018.
 */
@Component(modules = MakeOfferCardModule.class)
public interface MakeOfferCardComponent {
    void inject(MakeOfferCardActivity makeOfferCardActivity);

    void inject(OffersListActivity offersListActivity);
}
