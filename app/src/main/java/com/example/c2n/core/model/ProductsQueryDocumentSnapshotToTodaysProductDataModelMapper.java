package com.example.c2n.core.model;

import android.util.Log;

import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by vipul.singhal on 21-06-2018.
 */

public class ProductsQueryDocumentSnapshotToTodaysProductDataModelMapper {
    List<ProductDataModel> productsDataModels = new ArrayList<>();

    @Inject
    ProductsQueryDocumentSnapshotToTodaysProductDataModelMapper() {
    }

    public List<ProductDataModel> mapQueryDocumentSnapShotToProductDataModel(List<QueryDocumentSnapshot> queryDocumentSnapshots, String todaysOfferID) {
        if (productsDataModels.size() != 0)
            productsDataModels.clear();
//        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
//            if (documentSnapshot.get("productScheme") != null && (documentSnapshot.get("productDescription") != null))
//                productsDataModels.add(new ProductDataModel("", "", documentSnapshot.get("productName").toString(), documentSnapshot.get("productPhotoUrl").toString(), documentSnapshot.get("productMRP").toString(), documentSnapshot.get("productScheme").toString(), "", documentSnapshot.get("productDescription").toString()));
//            else if (documentSnapshot.get("productScheme") == null)
//                productsDataModels.add(new ProductDataModel("", "", documentSnapshot.get("productName").toString(), documentSnapshot.get("productPhotoUrl").toString(), documentSnapshot.get("productMRP").toString(), "", "", documentSnapshot.get("productDescription").toString()));
//            else if ((documentSnapshot.get("productDescription") == null))
//                productsDataModels.add(new ProductDataModel("", "", documentSnapshot.get("productName").toString(), documentSnapshot.get("productPhotoUrl").toString(), documentSnapshot.get("productMRP").toString(), documentSnapshot.get("productScheme").toString(), "", ""));
//            else
//                productsDataModels.add(new ProductDataModel("", "", documentSnapshot.get("productName").toString(), documentSnapshot.get("productPhotoUrl").toString(), documentSnapshot.get("productMRP").toString(), "", "", ""));
//        }
        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
            Log.d("Mapper_", "" + documentSnapshot.get("productOfferID").toString() + " - " + todaysOfferID);
            if (documentSnapshot.get("productOfferID").toString().equals(todaysOfferID)) {
                if (documentSnapshot.get("productScheme") != null && (documentSnapshot.get("productDescription") != null))
                    productsDataModels.add(new ProductDataModel(documentSnapshot.get("productOfferID").toString(), documentSnapshot.getId(), documentSnapshot.get("productCategory").toString(), documentSnapshot.get("productName").toString(), documentSnapshot.get("productPhotoUrl").toString(), documentSnapshot.get("productMRP").toString(), documentSnapshot.get("productScheme").toString(), documentSnapshot.get("shopEmail").toString(), documentSnapshot.get("productDescription").toString()));
                else if (documentSnapshot.get("productScheme") == null)
                    productsDataModels.add(new ProductDataModel(documentSnapshot.get("productOfferID").toString(), documentSnapshot.getId(), documentSnapshot.get("productCategory").toString(), documentSnapshot.get("productName").toString(), documentSnapshot.get("productPhotoUrl").toString(), documentSnapshot.get("productMRP").toString(), "", documentSnapshot.get("shopEmail").toString(), documentSnapshot.get("productDescription").toString()));
                else if ((documentSnapshot.get("productDescription") == null))
                    productsDataModels.add(new ProductDataModel(documentSnapshot.get("productOfferID").toString(), documentSnapshot.getId(), documentSnapshot.get("productCategory").toString(), documentSnapshot.get("productName").toString(), documentSnapshot.get("productPhotoUrl").toString(), documentSnapshot.get("productMRP").toString(), documentSnapshot.get("productScheme").toString(), documentSnapshot.get("shopEmail").toString(), ""));
                else
                    productsDataModels.add(new ProductDataModel(documentSnapshot.get("productOfferID").toString(), documentSnapshot.getId(), documentSnapshot.get("productCategory").toString(), documentSnapshot.get("productName").toString(), documentSnapshot.get("productPhotoUrl").toString(), documentSnapshot.get("productMRP").toString(), "", documentSnapshot.get("shopEmail").toString(), ""));
            }
        }
        return productsDataModels;
    }
}
