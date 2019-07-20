package com.example.c2n.core.model;

import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by vipul.singhal on 23-05-2018.
 */

public class DocumentToProductDataModelMapper {
    List<ProductDataModel> productsList = new ArrayList<>();

    @Inject
    DocumentToProductDataModelMapper() {

    }

    public List<ProductDataModel> mapDocumentToProductsList(List<QueryDocumentSnapshot> documentSnapshotList) {

        for (QueryDocumentSnapshot documentSnapshot : documentSnapshotList) {

            productsList.add(new ProductDataModel(documentSnapshot.getData().get("productCategory").toString(), documentSnapshot.getData().get("productName").toString(), documentSnapshot.getData().get("productPhotoUrl").toString(), documentSnapshot.getData().get("productMRP").toString()));
        }
        return productsList;
    }
}
