package com.example.c2n.core.model;

import android.content.Context;
import android.util.Log;

import com.example.c2n.core.SharedPrefManager;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by roshan.nimje on 21-05-2018.
 */

public class ShopsQueryDocumentSnapShopToShopDataModel {

    List<ShopDataModel> shopDataModels = new ArrayList<>();
    String userEmail;

    @Inject
    ShopsQueryDocumentSnapShopToShopDataModel() {
    }

    public List<ShopDataModel> queryDocumentSnapShotToShopDatModel(Context context, List<QueryDocumentSnapshot> queryDocumentSnapshots) {
        SharedPrefManager.Init(context);
        SharedPrefManager.LoadFromPref();
        userEmail = SharedPrefManager.get_userEmail();
        Log.d("QWERTYUIOP", "" + userEmail);
        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
            if (documentSnapshot.get("retailerID").toString().equals(userEmail)) {
                if (documentSnapshot.get("shopImageURL") != null && documentSnapshot.get("shopLandmark") != null)
                    shopDataModels.add(new ShopDataModel(documentSnapshot.getId(), documentSnapshot.get("shopName").toString(), documentSnapshot.get("shopAddress").toString(), documentSnapshot.get("shopLandmark").toString(), documentSnapshot.get("shopCellNo1").toString(), documentSnapshot.get("shopEmail").toString(), documentSnapshot.get("shopImageURL").toString(), documentSnapshot.getLong("shopLatitude"), documentSnapshot.getLong("shopLongitude")));
                else if (documentSnapshot.get("shopImageURL") != null)
                    shopDataModels.add(new ShopDataModel(documentSnapshot.getId(), documentSnapshot.get("shopName").toString(), documentSnapshot.get("shopAddress").toString(), "", documentSnapshot.get("shopCellNo1").toString(), documentSnapshot.get("shopEmail").toString(), documentSnapshot.get("shopImageURL").toString(), documentSnapshot.getLong("shopLatitude"), documentSnapshot.getLong("shopLongitude")));
                else if (documentSnapshot.get("shopLandmark") != null)
                    shopDataModels.add(new ShopDataModel(documentSnapshot.getId(), documentSnapshot.get("shopName").toString(), documentSnapshot.get("shopAddress").toString(), documentSnapshot.get("shopLandmark").toString(), documentSnapshot.get("shopCellNo1").toString(), documentSnapshot.get("shopEmail").toString(), "", documentSnapshot.getLong("shopLatitude"), documentSnapshot.getLong("shopLongitude")));
                else
                    shopDataModels.add(new ShopDataModel(documentSnapshot.getId(), documentSnapshot.get("shopName").toString(), documentSnapshot.get("shopAddress").toString(), "", documentSnapshot.get("shopCellNo1").toString(), documentSnapshot.get("shopEmail").toString(), "", documentSnapshot.getLong("shopLatitude"), documentSnapshot.getLong("shopLongitude")));
            }
        }
        return shopDataModels;
    }

}
