package com.example.c2n.core.model;

import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by roshan.nimje on 18-05-2018.
 */

public class CategoriesQueryDocumentSnapshotToCategoriesDataModel {

    List<CategoryDataModel> dataModels = new ArrayList<>();

    @Inject
    CategoriesQueryDocumentSnapshotToCategoriesDataModel() {

    }

    public List<CategoryDataModel> queryDocumentSnapshotToCategoriesDataModel(List<QueryDocumentSnapshot> queryDocumentSnapshots) {
        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
            dataModels.add(new CategoryDataModel(documentSnapshot.get("category").toString(), documentSnapshot.get("picURL").toString()));
        }
        return dataModels;
    }
}
