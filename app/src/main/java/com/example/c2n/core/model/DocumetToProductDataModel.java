package com.example.c2n.core.model;

import android.util.Log;

import com.google.firebase.firestore.DocumentSnapshot;

import javax.inject.Inject;

/**
 * Created by roshan.nimje on 21-05-2018.
 */

public class DocumetToProductDataModel {

//    UserDataModel userDataModel;

    @Inject
    DocumetToProductDataModel() {

    }

    public ProductDataModel mapDocumentToProductDataModel(DocumentSnapshot documentSnapshot) {
//        Log.d("mapDocumentToUserDataMo", "" + documentReference.get().getResult().get("userMobileNo"));
//        return null;
//        Log.d("DocumetToProductDataMo_", "" + documentSnapshot.get("offerDiscount").toString());
//        return new ProductDataModel(documentSnapshot.get("offerDiscount").toString());
        return new ProductDataModel("1");
    }
}
