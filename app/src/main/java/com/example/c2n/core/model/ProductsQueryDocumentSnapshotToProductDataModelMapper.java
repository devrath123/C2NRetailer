package com.example.c2n.core.model;

import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by vipul.singhal on 21-06-2018.
 */

public class ProductsQueryDocumentSnapshotToProductDataModelMapper {
    List<com.example.c2n.core.models.ProductDataModel> productsDataModels = new ArrayList<>();

    @Inject
    ProductsQueryDocumentSnapshotToProductDataModelMapper() {
    }

    public List<com.example.c2n.core.models.ProductDataModel> mapQueryDocumentSnapShotToProductDataModel(List<QueryDocumentSnapshot> queryDocumentSnapshots) {
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



//        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
//            if (documentSnapshot.get("productScheme") != null && (documentSnapshot.get("productDescription") != null))
//                productsDataModels.add(new com.example.c2n.core.models.ProductDataModel(documentSnapshot.get("productOfferID").toString(), documentSnapshot.getId(), documentSnapshot.get("productCategory").toString(), documentSnapshot.get("productName").toString(), documentSnapshot.get("productPhotoUrl").toString(), documentSnapshot.get("productMRP").toString(), documentSnapshot.get("productScheme").toString(), documentSnapshot.get("shopEmail").toString(), documentSnapshot.get("productDescription").toString()));
//            else if (documentSnapshot.get("productScheme") == null)
//                productsDataModels.add(new com.example.c2n.core.models.ProductDataModel(documentSnapshot.get("productOfferID").toString(), documentSnapshot.getId(), documentSnapshot.get("productCategory").toString(), documentSnapshot.get("productName").toString(), documentSnapshot.get("productPhotoUrl").toString(), documentSnapshot.get("productMRP").toString(), "", documentSnapshot.get("shopEmail").toString(), documentSnapshot.get("productDescription").toString()));
//            else if ((documentSnapshot.get("productDescription") == null))
//                productsDataModels.add(new com.example.c2n.core.models.ProductDataModel(documentSnapshot.get("productOfferID").toString(), documentSnapshot.getId(), documentSnapshot.get("productCategory").toString(), documentSnapshot.get("productName").toString(), documentSnapshot.get("productPhotoUrl").toString(), documentSnapshot.get("productMRP").toString(), documentSnapshot.get("productScheme").toString(), documentSnapshot.get("shopEmail").toString(), ""));
//            else
//                productsDataModels.add(new com.example.c2n.core.models.ProductDataModel(documentSnapshot.get("productOfferID").toString(), documentSnapshot.getId(), documentSnapshot.get("productCategory").toString(), documentSnapshot.get("productName").toString(), documentSnapshot.get("productPhotoUrl").toString(), documentSnapshot.get("productMRP").toString(), "", documentSnapshot.get("shopEmail").toString(), ""));
//        }
        return productsDataModels;
    }
}
