package com.example.c2n.core.mapper;

import android.util.Log;

import com.example.c2n.core.models.OfferDataModel;
import com.example.c2n.core.models.ShopDataModel;
import com.google.firebase.firestore.DocumentSnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by vipul.singhal on 02-07-2018.
 */

public class DocumentListToOfferDataModelListMapper {
    List<OfferDataModel> offerCardsList = new ArrayList<>();
    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

    DocumentQuerySnapshotToShopOfferDataModelMapper documentQuerySnapshotToShopOfferDataModelMapper;

    @Inject
    DocumentListToOfferDataModelListMapper(DocumentQuerySnapshotToShopOfferDataModelMapper documentQuerySnapshotToShopOfferDataModelMapper) {
        this.documentQuerySnapshotToShopOfferDataModelMapper = documentQuerySnapshotToShopOfferDataModelMapper;
    }

    public List<OfferDataModel> mapDocumentListToOfferCardsList(DocumentSnapshot documentSnapshot) {
        if (offerCardsList.size() == 0)
            offerCardsList.clear();
        if (documentSnapshot != null) {
            if (documentSnapshot.get("offersList") != null) {
                ArrayList arrayList = (ArrayList) documentSnapshot.get("offersList");
                for (int i = 0; i < arrayList.size(); i++) {
                    HashMap offerCard = (HashMap) arrayList.get(i);
                    OfferDataModel offerDataModel = new OfferDataModel();
                    offerDataModel.setOfferID(offerCard.get("offerID").toString());
                    offerDataModel.setOfferDiscount(Integer.parseInt(offerCard.get("offerDiscount").toString()));
                    offerDataModel.setOfferName(offerCard.get("offerName").toString());
                    offerDataModel.setOfferStartDate((Date) offerCard.get("offerStartDate"));
                    offerDataModel.setOfferEndDate((Date) offerCard.get("offerEndDate"));
                    offerDataModel.setOfferStatus(Boolean.parseBoolean(offerCard.get("offerStatus").toString()));
                    offerDataModel.setMon(Boolean.parseBoolean(offerCard.get("mon").toString()));
                    offerDataModel.setTue(Boolean.parseBoolean(offerCard.get("tue").toString()));
                    offerDataModel.setWed(Boolean.parseBoolean(offerCard.get("wed").toString()));
                    offerDataModel.setThu(Boolean.parseBoolean(offerCard.get("thu").toString()));
                    offerDataModel.setFri(Boolean.parseBoolean(offerCard.get("fri").toString()));
                    offerDataModel.setSat(Boolean.parseBoolean(offerCard.get("sat").toString()));
                    offerDataModel.setSun(Boolean.parseBoolean(offerCard.get("sun").toString()));
                    offerDataModel.setShopDataModels((List<ShopDataModel>) offerCard.get("shopDataModels"));
//            offerDataModel.setOfferProducts((ArrayList) offerCard.get("offerProducts"));
//            List<HashMap<String, List<String>>> hashMaps = new ArrayList<>();
//            ArrayList arrayList = (ArrayList) offerCard.get("offerProducts");
//            for (int i = 0; i < arrayList.size(); i++) {
//                HashMap<String, List<String>> o = (HashMap<String, List<String>>) arrayList.get(i);
//                hashMaps.add(i, o);
//            }
//            offerDataModel.setOfferProducts(hashMaps);

//            offerCard.get("offerProducts");
//            if (offerCard.get("offerProducts") != null)
//            offerDataModel.setShopOfferDataModel(documentQuerySnapshotToShopOfferDataModelMapper.mapDocumentQuerySnapshotToOfferDataModelMapper((ArrayList) offerCard.get("offerProducts")));
//            offerDataModel.setOfferProducts((String[]) offerCard.getData().get("productID"));
//            Object list = offerCard.getData().get("offerProducts");
//            offerDataModel.setOfferProducts(offerCard.getData().get("offerProducts"));
                    Log.d("offer_data_model", offerDataModel.toString());
                    offerCardsList.add(offerDataModel);
                }
            }
        }
        Log.d("offer_cardlist", offerCardsList.toString());
        return offerCardsList;
    }
}
