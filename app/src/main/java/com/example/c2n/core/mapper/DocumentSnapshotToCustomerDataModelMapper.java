package com.example.c2n.core.mapper;

import android.util.Log;

import com.example.c2n.core.model1.CustomerDataModel;
import com.example.c2n.core.model1.RetailerDataModel;
import com.google.firebase.firestore.DocumentSnapshot;

import javax.inject.Inject;

/**
 * Created by shivani.singh on 20-07-2018.
 */

public class DocumentSnapshotToCustomerDataModelMapper {

    @Inject
    public DocumentSnapshotToCustomerDataModelMapper() {
    }

    public CustomerDataModel mapDocumentListToUsernameBoolean(DocumentSnapshot documentSnapshot) {

        // <For customer
        if (documentSnapshot == null || documentSnapshot.getData() == null)
            return new CustomerDataModel();
        // For customer>


        Log.d("mapDocumentToUserDataMo", "" + documentSnapshot.getData().get("customerName").toString());
        CustomerDataModel customerDataModel = new CustomerDataModel();

        customerDataModel.setCustomerID(documentSnapshot.getData().get("customerID").toString());
        customerDataModel.setCustomerName(documentSnapshot.getData().get("customerName").toString());
        if (documentSnapshot.getData().get("customerAddress") != null)
            customerDataModel.setCustomerAddress(documentSnapshot.getData().get("customerAddress").toString());
        else
            customerDataModel.setCustomerAddress("");

        if (documentSnapshot.getData().get("customerMobileNo") != null)
            customerDataModel.setCustomerMobileNo(documentSnapshot.getData().get("customerMobileNo").toString());
        else
            customerDataModel.setCustomerMobileNo("");

        if (documentSnapshot.getData().get("customerDOB") != null)
            customerDataModel.setCustomerDOB(documentSnapshot.getData().get("customerDOB").toString());
        else
            customerDataModel.setCustomerDOB("");
//        retailerDataModel.setRetailerLatitude(documentSnapshot.getLong("retailerLatitude"));
//        retailerDataModel.setRetailerLongitude(documentSnapshot.getLong("retailerLongitude"));
        if (documentSnapshot.getData().get("customerImageURL") != null)
            customerDataModel.setCustomerImageURL(documentSnapshot.getData().get("customerImageURL").toString());
        else
            customerDataModel.setCustomerImageURL("");
        return customerDataModel;
    }
}
