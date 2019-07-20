package com.example.c2n.core.model;

import android.util.Log;

import com.example.c2n.core.model1.OfferDataModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by vipul.singhal on 02-07-2018.
 */

public class OfferListDataModelToOfferListViewModelMapper {
    List<OfferDataModel> activeOfferCardsList = new ArrayList<>();
    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
    Date todayDate;

    @Inject
    OfferListDataModelToOfferListViewModelMapper() {

    }

    public List<OfferDataModel> mapOffersListToActiveOffersList(List<OfferDataModel> offerListDataModels) {

        for (OfferDataModel offerCard : offerListDataModels) {
            try {
                todayDate = format.parse(format.format(new Date()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            int today = new Date().getDay();
            if (isOfferCardActive(today, offerCard)) {
//                if (todayDate != null && offerCard.getOfferStartDate().compareTo(todayDate) * todayDate.compareTo(offerCard.getOfferEndDate()) >= 0)
//                    activeOfferCardsList.add(new OfferDataModel(offerCard.getOfferID(), offerCard.getOfferName(), offerCard.getOfferBackground(), offerCard.getOfferDiscount(), offerCard.getOfferStartdate(), offerCard.getOfferEndDate(), offerCard.isSunday(), offerCard.isMonday(), offerCard.isTuesday(), offerCard.isWednesday(), offerCard.isThursday(), offerCard.isFriday(), offerCard.isSaturday()));
            }
        }
        Log.d("offer_cardlist", activeOfferCardsList.toString());
        return activeOfferCardsList;
    }

    public boolean isOfferCardActive(int todaysDay, OfferDataModel offerCard) {
        switch (todaysDay) {
            case 0:
                if (offerCard.isSun())
                    return true;
            case 1:
                if (offerCard.isMon())
                    return true;
            case 2:
                if (offerCard.isTue())
                    return true;
            case 3:
                if (offerCard.isWed())
                    return true;
            case 4:
                if (offerCard.isThu())
                    return true;
            case 5:
                if (offerCard.isFri())
                    return true;
            case 6:
                if (offerCard.isSat())
                    return true;
        }
        return false;
    }
}
