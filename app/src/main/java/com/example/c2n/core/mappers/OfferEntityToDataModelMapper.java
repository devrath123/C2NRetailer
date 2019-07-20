package com.example.c2n.core.mappers;

import android.util.Log;

import com.example.c2n.core.models.OfferDataModel;
import com.example.c2n.core.models.OfferEntity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.inject.Inject;

public class OfferEntityToDataModelMapper {

    private static final String TAG = OfferEntityToDataModelMapper.class.getSimpleName();
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

    @Inject
    public OfferEntityToDataModelMapper() {

    }

    OfferDataModel mapEntityToData(OfferEntity offerEntity) {
        OfferDataModel offerDataModel = new OfferDataModel();

        offerDataModel.setOfferID(offerEntity.getOfferID());
        offerDataModel.setOfferDiscount(offerEntity.getOfferDiscount());
        offerDataModel.setOfferStartDate(changeDate(offerEntity.getOfferStartDate().getSeconds()));
        offerDataModel.setOfferEndDate(changeDate(offerEntity.getOfferEndDate().getSeconds()));
//        offerDataModel.setOfferStartDate(getdate(offerEntity.getOfferStartDate()));
//        offerDataModel.setOfferEndDate(getdate(offerEntity.getOfferEndDate()));
        offerDataModel.setOfferName(offerEntity.getOfferName());
        offerDataModel.setMon(offerEntity.getMon());
        offerDataModel.setTue(offerEntity.getTue());
        offerDataModel.setWed(offerEntity.getWed());
        offerDataModel.setThu(offerEntity.getThu());
        offerDataModel.setFri(offerEntity.getFri());
        offerDataModel.setSat(offerEntity.getSat());
        offerDataModel.setSun(offerEntity.getSun());
        offerDataModel.setOfferStatus(offerEntity.getOfferStatus());
        offerDataModel.setShopDataModels(null);
        return offerDataModel;
    }

    public Date changeDate(long seconds) {
        Date date = new java.util.Date(seconds * 1000);
        SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd-MM-yyyy");
        sdf.setTimeZone(java.util.TimeZone.getTimeZone("GMT+5:30"));
        Log.d(TAG, "changeDate: seconds : " + seconds + " Date : " + sdf.format(date));
        Date offerDate = null;
        try {
            offerDate = sdf.parse(sdf.format(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return offerDate;
    }

    public Date getdate(String temp) {
        String date = temp.substring(8, 10) + "/" + temp.substring(5, 7) + "/" + temp.substring(0, 3);
        try {
            return simpleDateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}