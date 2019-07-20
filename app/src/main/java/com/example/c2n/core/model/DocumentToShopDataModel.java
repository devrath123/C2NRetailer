package com.example.c2n.core.model;

import com.google.firebase.firestore.DocumentSnapshot;

import javax.inject.Inject;

/**
 * Created by roshan.nimje on 21-05-2018.
 */

public class DocumentToShopDataModel {

//    UserDataModel userDataModel;

    @Inject
    DocumentToShopDataModel() {

    }

    public ShopDataModel mapDocumentToShopDataModel(DocumentSnapshot documentSnapshot) {
//        Log.d("mapDocumentToUserDataMo", "" + documentReference.get().getResult().get("userMobileNo"));
//        return null;
        return new ShopDataModel(documentSnapshot.get("retailerID").toString(), documentSnapshot.get("shopName").toString(), documentSnapshot.get("shopAddress").toString(), documentSnapshot.get("shopEmail").toString(), documentSnapshot.get("shopCellNo1").toString(), documentSnapshot.get("shopCellNo2").toString());
    }
}
