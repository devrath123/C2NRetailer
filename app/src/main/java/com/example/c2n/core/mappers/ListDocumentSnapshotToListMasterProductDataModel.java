package com.example.c2n.core.mappers;

import android.content.Context;
import android.util.Log;

import com.example.c2n.core.models.MasterProductDataModel;
import com.example.c2n.core.models.OfferDataModel;
import com.example.c2n.core.models.ProductDataModel;
import com.example.c2n.core.models.ShopDataModel;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by shivani.singh on 20-07-2018.
 */

public class ListDocumentSnapshotToListMasterProductDataModel {

    private final String TAG = "ListDocumentSnapshotToL";

    @Inject
    ListDocumentSnapshotToListMasterProductDataModel() {

    }

    public List<MasterProductDataModel> mapListDocumentSnapshotToListMasterProductDataModel(Context context, QuerySnapshot queryDocumentSnapshots) {
        List<MasterProductDataModel> masterProductDataModels = new ArrayList<>();
        for (QueryDocumentSnapshot queryDocumentSnapshot : queryDocumentSnapshots) {
            MasterProductDataModel masterProductDataModel = new MasterProductDataModel();
            masterProductDataModel.setProductID(queryDocumentSnapshot.getId());
            masterProductDataModel.setProductName((String) queryDocumentSnapshot.get("productName"));
//            masterProductDataModel.setProductMRP(Double.parseDouble(String.valueOf(queryDocumentSnapshot.get("productMRP"))));
            masterProductDataModel.setProductCategory((String) queryDocumentSnapshot.get("productCategory"));
            masterProductDataModel.setProductImageURL((String) queryDocumentSnapshot.get("productImageURL"));
            masterProductDataModel.setProductDescription((String) queryDocumentSnapshot.get("productDescription"));
            masterProductDataModels.add(masterProductDataModel);
        }
        return masterProductDataModels;
    }
}
