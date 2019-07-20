package com.example.c2n.core.mapper;

import android.util.Log;

import com.example.c2n.core.model1.CustomerDataModel;
import com.google.firebase.firestore.DocumentSnapshot;

import javax.inject.Inject;

/**
 * Created by shivani.singh on 20-07-2018.
 */

public class DocumentToCustomerDataModel {

    @Inject
    public DocumentToCustomerDataModel() {
    }

    public CustomerDataModel mapDocumentToCustomerDataModel(DocumentSnapshot documentSnapshot) {
        Log.d("mapDocumentToCstDataMo", "" + documentSnapshot.getData().get("customerName").toString());
        CustomerDataModel customerDataModel = new CustomerDataModel();
        if (documentSnapshot.get("customerID") != null)
            customerDataModel.setCustomerID(documentSnapshot.get("customerID").toString());
        if (documentSnapshot.get("customerName") != null)
            customerDataModel.setCustomerName(documentSnapshot.get("customerName").toString());
        if (documentSnapshot.get("customerAddress") != null)
            customerDataModel.setCustomerAddress(documentSnapshot.get("customerAddress").toString());
        if (documentSnapshot.get("customerMobileNo") != null)
            customerDataModel.setCustomerMobileNo(documentSnapshot.get("customerMobileNo").toString());
        if (documentSnapshot.get("customerDOB") != null)
            customerDataModel.setCustomerDOB(documentSnapshot.get("customerDOB").toString());
        if (documentSnapshot.getDouble("customerLatitude") != null)
            customerDataModel.setCustomerLatitude(documentSnapshot.getDouble("customerLatitude"));
        if (documentSnapshot.getDouble("customerLongitude") != null)
            customerDataModel.setCustomerLongitude(documentSnapshot.getDouble("customerLongitude"));
        if (documentSnapshot.get("customerImageURL") != null)
            customerDataModel.setCustomerImageURL(documentSnapshot.get("customerImageURL").toString());

        return customerDataModel;
    }
}
