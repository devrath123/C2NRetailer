package com.example.c2n.core.mapper;

import android.util.Log;

import com.example.c2n.core.model1.OfferDataModel;
import com.example.c2n.core.model1.OfferDetailsDataModel;
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

public class DocumentListToOfferDetailsDataModelListMapper {

    private DocumentSnapshopToOfferDataModelMapper documentSnapshopToOfferDataModelMapper;

    @Inject
    DocumentListToOfferDetailsDataModelListMapper(DocumentSnapshopToOfferDataModelMapper documentSnapshopToOfferDataModelMapper) {
        this.documentSnapshopToOfferDataModelMapper = documentSnapshopToOfferDataModelMapper;
    }

    public List<OfferDetailsDataModel> mapDocumentListToOfferCardsList(List<QueryDocumentSnapshot> documentSnapshotList, String retailerID) {
        List<OfferDetailsDataModel> offerDetailsDataModels = new ArrayList<>();
        for (QueryDocumentSnapshot documentSnapshot : documentSnapshotList) {
            OfferDetailsDataModel offerDetailsDataModel = new OfferDetailsDataModel();
            offerDetailsDataModel.setRetailerID(retailerID);
            offerDetailsDataModel.setOfferDataModel(documentSnapshopToOfferDataModelMapper.mapDocumentListToOfferCardsList(documentSnapshot));
            offerDetailsDataModels.add(offerDetailsDataModel);
        }
        return offerDetailsDataModels;
    }
}
