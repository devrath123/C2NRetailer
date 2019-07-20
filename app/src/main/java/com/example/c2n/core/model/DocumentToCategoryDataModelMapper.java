package com.example.c2n.core.model;

import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by vipul.singhal on 20-05-2018.
 */

public class DocumentToCategoryDataModelMapper {
    List<CategoryDataModel> categoriesList = new ArrayList<>();

    @Inject
    DocumentToCategoryDataModelMapper() {

    }

    public List<CategoryDataModel> mapDocumentToCategorieList(List<QueryDocumentSnapshot> documentSnapshotList) {

        for (QueryDocumentSnapshot documentSnapshot : documentSnapshotList) {

            categoriesList.add(new CategoryDataModel(documentSnapshot.getData().get("category").toString(), documentSnapshot.getData().get("picURL").toString()));
        }
        return categoriesList;
    }
}
