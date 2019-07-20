package com.example.c2n.core.mapper;

import android.util.Log;

import com.example.c2n.core.models.OfferDataModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by shivani.singh on 20-07-2018.
 */

public class OfferDataModelToOfferViewModelMapper {

    List<OfferDataModel> activeOfferCardsList = new ArrayList<>();
    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
    Date todayDate;
    OfferDataModel offerDataModel;

    @Inject
    OfferDataModelToOfferViewModelMapper() {

    }

    public List<OfferDataModel> mapOffersListToActiveOffersList(List<OfferDataModel> offerDataModels) {

        for (OfferDataModel offerCard : offerDataModels) {
            if (offerCard.getShopDataModels() != null) {
                offerDataModel = new OfferDataModel();
                try {
                    todayDate = format.parse(format.format(new Date()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                int today = new Date().getDay();
                if (offerCard.isOfferStatus()) {
                    if (isOfferCardActive(today, offerCard)) {
                        if (todayDate != null && offerCard.getOfferStartDate().compareTo(todayDate) * todayDate.compareTo(offerCard.getOfferEndDate()) >= 0) {
                            offerDataModel.setOfferID(offerCard.getOfferID());
                            offerDataModel.setOfferDiscount(offerCard.getOfferDiscount());
                            offerDataModel.setOfferName(offerCard.getOfferName());
                            offerDataModel.setOfferStartDate(offerCard.getOfferStartDate());
                            offerDataModel.setOfferEndDate(offerCard.getOfferEndDate());
                            offerDataModel.setMon(offerCard.isMon());
                            offerDataModel.setTue(offerCard.isTue());
                            offerDataModel.setWed(offerCard.isWed());
                            offerDataModel.setThu(offerCard.isThu());
                            offerDataModel.setFri(offerCard.isFri());
                            offerDataModel.setSat(offerCard.isSat());
                            offerDataModel.setSun(offerCard.isSun());
                            offerDataModel.setShopDataModels(offerCard.getShopDataModels());
                            activeOfferCardsList.add(offerDataModel);
                        }
                    }
                }
            }
        }
        Log.d("active_offer_cards", activeOfferCardsList.toString());
        return activeOfferCardsList;
    }

    public boolean isOfferCardActive(int todaysDay, OfferDataModel offerCard) {
        switch (todaysDay) {
            case 0:
                if (offerCard.isSun())
                    return true;
                break;
            case 1:
                if (offerCard.isMon())
                    return true;
                break;
            case 2:
                if (offerCard.isTue())
                    return true;
                break;
            case 3:
                if (offerCard.isWed())
                    return true;
                break;
            case 4:
                if (offerCard.isThu())
                    return true;
                break;
            case 5:
                if (offerCard.isFri())
                    return true;
                break;
            case 6:
                if (offerCard.isSat())
                    return true;
                break;
        }
        return false;
    }
}
