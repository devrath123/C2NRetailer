package com.example.c2n.core.mapper;

import android.util.Log;

import com.example.c2n.core.model1.OfferDataModel;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by vipul.singhal on 02-07-2018.
 */

public class DocumentSnapshopToOfferDataModelMapper {

    DocumentQuerySnapshotToShopOfferDataModelMapper documentQuerySnapshotToShopOfferDataModelMapper;

    @Inject
    DocumentSnapshopToOfferDataModelMapper(DocumentQuerySnapshotToShopOfferDataModelMapper documentQuerySnapshotToShopOfferDataModelMapper) {
        this.documentQuerySnapshotToShopOfferDataModelMapper = documentQuerySnapshotToShopOfferDataModelMapper;
    }

    public OfferDataModel mapDocumentListToOfferCardsList(DocumentSnapshot documentSnapshot) {
        OfferDataModel offerDataModel = new OfferDataModel();
        offerDataModel.setOfferID(documentSnapshot.getId());
        offerDataModel.setOfferDiscount(Integer.parseInt(documentSnapshot.getData().get("offerDiscount").toString()));
        offerDataModel.setOfferName(documentSnapshot.getData().get("offerName").toString());
        offerDataModel.setOfferStartDate((Date) documentSnapshot.getData().get("offerStartDate"));
        offerDataModel.setOfferEndDate((Date) documentSnapshot.getData().get("offerEndDate"));
        offerDataModel.setOfferStatus(Boolean.parseBoolean(documentSnapshot.getData().get("offerStatus").toString()));
        offerDataModel.setMon(Boolean.parseBoolean(documentSnapshot.getData().get("mon").toString()));
        offerDataModel.setTue(Boolean.parseBoolean(documentSnapshot.getData().get("tue").toString()));
        offerDataModel.setWed(Boolean.parseBoolean(documentSnapshot.getData().get("wed").toString()));
        offerDataModel.setThu(Boolean.parseBoolean(documentSnapshot.getData().get("thu").toString()));
        offerDataModel.setFri(Boolean.parseBoolean(documentSnapshot.getData().get("fri").toString()));
        offerDataModel.setSat(Boolean.parseBoolean(documentSnapshot.getData().get("sat").toString()));
        offerDataModel.setSun(Boolean.parseBoolean(documentSnapshot.getData().get("sun").toString()));

//            offerDataModel.setOfferProducts((ArrayList) documentSnapshot.get("offerProducts"));
        List<HashMap<String, List<String>>> hashMaps = new ArrayList<>();
        ArrayList arrayList = (ArrayList) documentSnapshot.get("offerProducts");
        for (int i = 0; i < arrayList.size(); i++) {
            HashMap<String, List<String>> o = (HashMap<String, List<String>>) arrayList.get(i);
            hashMaps.add(i, o);
        }
        offerDataModel.setOfferProducts(hashMaps);

//            documentSnapshot.get("offerProducts");
//            if (documentSnapshot.get("offerProducts") != null)
//            offerDataModel.setShopOfferDataModel(documentQuerySnapshotToShopOfferDataModelMapper.mapDocumentQuerySnapshotToOfferDataModelMapper((ArrayList) documentSnapshot.get("offerProducts")));
//            offerDataModel.setOfferProducts((String[]) documentSnapshot.getData().get("productID"));
//            Object list = documentSnapshot.getData().get("offerProducts");
//            offerDataModel.setOfferProducts(documentSnapshot.getData().get("offerProducts"));
        Log.d("offer_doc.id", documentSnapshot.getId());
        return offerDataModel;
    }
}
