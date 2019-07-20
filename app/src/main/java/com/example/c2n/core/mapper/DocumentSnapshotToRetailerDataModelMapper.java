package com.example.c2n.core.mapper;

import android.util.Log;

import com.example.c2n.core.model1.RetailerDataModel;
import com.google.firebase.firestore.DocumentSnapshot;

import javax.inject.Inject;

/**
 * Created by shivani.singh on 20-07-2018.
 */

public class DocumentSnapshotToRetailerDataModelMapper {

    @Inject
    public DocumentSnapshotToRetailerDataModelMapper() {
    }

    public RetailerDataModel mapDocumentListToUsernameBoolean(DocumentSnapshot documentSnapshot) {

        // <For customer
        if (documentSnapshot == null || documentSnapshot.getData() == null)
            return new RetailerDataModel();
        // For customer>


        Log.d("mapDocumentToUserDataMo", "" + documentSnapshot.getData().get("retailerName").toString());
        RetailerDataModel retailerDataModel = new RetailerDataModel();

        retailerDataModel.setRetailerID(documentSnapshot.getData().get("retailerID").toString());
        retailerDataModel.setRetailerName(documentSnapshot.getData().get("retailerName").toString());
        if (documentSnapshot.getData().get("retailerAddress") != null)
            retailerDataModel.setRetailerAddress(documentSnapshot.getData().get("retailerAddress").toString());
        else
            retailerDataModel.setRetailerAddress("");

        if (documentSnapshot.getData().get("retailerMobileNo") != null)
            retailerDataModel.setRetailerMobileNo(documentSnapshot.getData().get("retailerMobileNo").toString());
        else
            retailerDataModel.setRetailerMobileNo("");

        if (documentSnapshot.getData().get("retailerDOB") != null)
            retailerDataModel.setRetailerDOB(documentSnapshot.getData().get("retailerDOB").toString());
        else
            retailerDataModel.setRetailerDOB("");
//        retailerDataModel.setRetailerLatitude(documentSnapshot.getLong("retailerLatitude"));
//        retailerDataModel.setRetailerLongitude(documentSnapshot.getLong("retailerLongitude"));
        if (documentSnapshot.getData().get("retailerImageURL") != null)
            retailerDataModel.setRetailerImageURL(documentSnapshot.getData().get("retailerImageURL").toString());
        else
            retailerDataModel.setRetailerImageURL("");
        return retailerDataModel;
    }
}
