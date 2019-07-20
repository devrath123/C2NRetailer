package com.example.c2n.core.mapper;

import android.util.Log;

import com.example.c2n.core.model1.OfferDataModel;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by shivani.singh on 24-07-2018.
 */

public class DocumentToOfferDataModelMapper {

    List<OfferDataModel> offerCardsList = new ArrayList<>();
    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");


    @Inject
    public DocumentToOfferDataModelMapper() {
    }

    public List<OfferDataModel> mapDocumentListToOfferCardsList(List<QueryDocumentSnapshot> documentSnapshotList) {
        if (offerCardsList.size() == 0)
            offerCardsList.clear();
        for (QueryDocumentSnapshot documentSnapshot : documentSnapshotList) {
            Log.d("offer_doc.id", documentSnapshot.getId());
            OfferDataModel offerDataModel = new OfferDataModel();
            if (documentSnapshot.get("offerName") != null && documentSnapshot.get("offerBackground") != null) {
                offerDataModel.setOfferID(documentSnapshot.getId());
                offerDataModel.setOfferDiscount(Integer.parseInt(documentSnapshot.get("offerDiscount").toString()));
                offerDataModel.setMon(Boolean.parseBoolean(documentSnapshot.get("mon").toString()));
                offerDataModel.setTue(Boolean.parseBoolean(documentSnapshot.get("tue").toString()));
                offerDataModel.setWed(Boolean.parseBoolean(documentSnapshot.get("wed").toString()));
                offerDataModel.setThu(Boolean.parseBoolean(documentSnapshot.getData().get("thu").toString()));
                offerDataModel.setFri(Boolean.parseBoolean(documentSnapshot.getData().get("fri").toString()));
                offerDataModel.setSat(Boolean.parseBoolean(documentSnapshot.getData().get("sat").toString()));
                offerDataModel.setSun(Boolean.parseBoolean(documentSnapshot.getData().get("sun").toString()));
                offerDataModel.setOfferEndDate((Date) documentSnapshot.getData().get("offerEndDate"));
                offerDataModel.setOfferStartDate((Date) documentSnapshot.getData().get("offerStartDate"));
                offerDataModel.setOfferName(documentSnapshot.getData().get("offerName").toString());
//                offerDataModel.setOfferBackground(Integer.parseInt(documentSnapshot.getData().get("offerBackground").toString()));
                offerCardsList.add(offerDataModel);


            }
//                offerCardsList.add(new OfferDataModel(documentSnapshot.getId(), documentSnapshot.getData().get("offerName").toString(), Integer.parseInt(documentSnapshot.getData().get("offerBackground").toString()), documentSnapshot.getData().get("offerDiscount").toString(), (Date) documentSnapshot.getData().get("offerStartdate"), (Date) documentSnapshot.getData().get("offerEndDate"), Boolean.parseBoolean(documentSnapshot.getData().get("sunday").toString()), Boolean.parseBoolean(documentSnapshot.getData().get("monday").toString()), Boolean.parseBoolean(documentSnapshot.getData().get("tuesday").toString()), Boolean.parseBoolean(documentSnapshot.getData().get("wednesday").toString()), Boolean.parseBoolean(documentSnapshot.getData().get("thursday").toString()), Boolean.parseBoolean(documentSnapshot.getData().get("friday").toString()), Boolean.parseBoolean(documentSnapshot.getData().get("saturday").toString())));
            else {
                offerDataModel.setOfferID(documentSnapshot.getId());
                offerDataModel.setOfferDiscount(Integer.parseInt(documentSnapshot.get("offerDiscount").toString()));
                offerDataModel.setMon(Boolean.parseBoolean(documentSnapshot.getData().get("mon").toString()));
                offerDataModel.setTue(Boolean.parseBoolean(documentSnapshot.getData().get("tue").toString()));
                offerDataModel.setWed(Boolean.parseBoolean(documentSnapshot.getData().get("wed").toString()));
                offerDataModel.setThu(Boolean.parseBoolean(documentSnapshot.getData().get("thu").toString()));
                offerDataModel.setFri(Boolean.parseBoolean(documentSnapshot.getData().get("fri").toString()));
                offerDataModel.setSat(Boolean.parseBoolean(documentSnapshot.getData().get("sat").toString()));
                offerDataModel.setSun(Boolean.parseBoolean(documentSnapshot.getData().get("sun").toString()));
                offerDataModel.setOfferEndDate((Date) documentSnapshot.getData().get("offerEndDate"));
                offerDataModel.setOfferStartDate((Date) documentSnapshot.getData().get("offerStartDate"));
                offerDataModel.setOfferName(documentSnapshot.getData().get("offerName").toString());
                offerCardsList.add(offerDataModel);

            }
//                offerCardsList.add(new OfferDataModel(documentSnapshot.getId(), documentSnapshot.getData().get("offerDiscount").toString(), (Date) documentSnapshot.getData().get("offerStartdate"), (Date) documentSnapshot.getData().get("offerEndDate"), Boolean.parseBoolean(documentSnapshot.getData().get("sunday").toString()), Boolean.parseBoolean(documentSnapshot.getData().get("monday").toString()), Boolean.parseBoolean(documentSnapshot.getData().get("tuesday").toString()), Boolean.parseBoolean(documentSnapshot.getData().get("wednesday").toString()), Boolean.parseBoolean(documentSnapshot.getData().get("thursday").toString()), Boolean.parseBoolean(documentSnapshot.getData().get("friday").toString()), Boolean.parseBoolean(documentSnapshot.getData().get("saturday").toString())));

        }
        Log.d("offer_cardlist", offerCardsList.toString());
        return offerCardsList;
    }
}


