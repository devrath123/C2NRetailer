package com.example.c2n.make_offer_card.presenter;

import java.util.Date;

/**
 * Created by vipul.singhal on 02-07-2018.
 */

public interface MakeOfferCardView {

    String getOfferCardName();

//    int getOfferCardBackground();

    int getOfferCardDiscount();

    Date getOfferStartDate();

    Date getOfferEndDate();

    Boolean getIsSundayIncludes();

    Boolean getIsMondayIncludes();

    Boolean getIsTuesdayIncludes();

    Boolean getIsWednesdayIncludes();

    Boolean getIsThursdayIncludes();

    Boolean getIsFridayIncludes();

    Boolean getIsSaturdayIncludes();


    Boolean getOfferStatus();

    String getOfferId();

    void showOfferCardProgress(Boolean progress, int type);

    void isOfferCardAddingSuccess(Boolean success);

    void isOfferCardUpdatingSuccess(Boolean success);
}
