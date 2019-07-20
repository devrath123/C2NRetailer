package com.example.c2n.core.mapper;

import android.util.Log;

import com.example.c2n.core.model1.RetailerDataModel;
import com.google.firebase.firestore.DocumentSnapshot;

import javax.inject.Inject;

/**
 * Created by shivani.singh on 20-07-2018.
 */

public class DocumentToRetailerDataModel {

    @Inject
    public DocumentToRetailerDataModel() {
    }

    public RetailerDataModel mapDocumentToUserDataModel(DocumentSnapshot documentSnapshot) {
        Log.d("mapDocumentToUserDataMo", "" + documentSnapshot.getData().get("retailerName").toString());
        RetailerDataModel retailerDataModel = new RetailerDataModel();
        if (documentSnapshot.get("retailerID") != null)
            retailerDataModel.setRetailerID(documentSnapshot.get("retailerID").toString());
        if (documentSnapshot.get("retailerName") != null)
            retailerDataModel.setRetailerName(documentSnapshot.get("retailerName").toString());
        if (documentSnapshot.get("retailerAddress") != null)
            retailerDataModel.setRetailerAddress(documentSnapshot.get("retailerAddress").toString());
        if (documentSnapshot.get("retailerMobileNo") != null)
            retailerDataModel.setRetailerMobileNo(documentSnapshot.get("retailerMobileNo").toString());
        if (documentSnapshot.get("retailerDOB") != null)
            retailerDataModel.setRetailerDOB(documentSnapshot.get("retailerDOB").toString());
        if (documentSnapshot.getLong("retailerLatitude") != null)
            retailerDataModel.setRetailerLatitude(documentSnapshot.getLong("retailerLatitude"));
        if (documentSnapshot.getLong("retailerLongitude") != null)
            retailerDataModel.setRetailerLongitude(documentSnapshot.getLong("retailerLongitude"));
        if (documentSnapshot.get("retailerImageURL") != null)
            retailerDataModel.setRetailerImageURL(documentSnapshot.get("retailerImageURL").toString());

        return retailerDataModel;
    }
}
