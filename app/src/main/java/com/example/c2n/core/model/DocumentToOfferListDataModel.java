package com.example.c2n.core.model;

import com.example.c2n.core.models.OfferDataModel;
import com.example.c2n.core.model1.ShopOfferDataModel;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by vipul.singhal on 09-07-2018.
 */

public class DocumentToOfferListDataModel {
    @Inject
    DocumentToOfferListDataModel() {

    }

    public OfferDataModel mapDocumentToOfferListDataModel(DocumentSnapshot documentSnapshot) {
//        Log.d("mapDocumentToUserDataMo", "" + documentReference.get().getResult().get("userMobileNo"));
//        return null;
        OfferDataModel offerDataModel = new OfferDataModel();
        offerDataModel.setOfferID(documentSnapshot.getId());
        offerDataModel.setOfferDiscount(Integer.parseInt(documentSnapshot.getData().get("offerDiscount").toString()));
        offerDataModel.setOfferName(documentSnapshot.getData().get("offerName").toString());
        offerDataModel.setOfferStartDate((Date) documentSnapshot.getData().get("offerStartDate"));
        offerDataModel.setOfferEndDate((Date) documentSnapshot.getData().get("offerEndDate"));
        offerDataModel.setMon(Boolean.parseBoolean(documentSnapshot.getData().get("mon").toString()));
        offerDataModel.setTue(Boolean.parseBoolean(documentSnapshot.getData().get("tue").toString()));
        offerDataModel.setWed(Boolean.parseBoolean(documentSnapshot.getData().get("wed").toString()));
        offerDataModel.setThu(Boolean.parseBoolean(documentSnapshot.getData().get("thu").toString()));
        offerDataModel.setFri(Boolean.parseBoolean(documentSnapshot.getData().get("fri").toString()));
        offerDataModel.setSat(Boolean.parseBoolean(documentSnapshot.getData().get("sat").toString()));
        offerDataModel.setSun(Boolean.parseBoolean(documentSnapshot.getData().get("sun").toString()));
//        offerDataModel.setOfferProducts((String[]) documentSnapshot.getData().get("productID"));
//        offerDataModel.setOfferProducts((List<ShopOfferDataModel>) documentSnapshot.getData().get("offerProducts"));
        return offerDataModel;
    }
}
