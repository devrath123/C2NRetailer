package com.example.c2n.make_offer_card.di;

/**
 * Created by vipul.singhal on 02-07-2018.
 */

public class MakeOfferCardDI {
    private static MakeOfferCardComponent component;

    public static MakeOfferCardComponent getMakeOfferCardComponent() {
        if (component == null) {
            component = DaggerMakeOfferCardComponent.builder().build();
        }
        return component;
    }
}
