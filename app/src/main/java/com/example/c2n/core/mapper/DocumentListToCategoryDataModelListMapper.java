package com.example.c2n.core.mapper;

import com.example.c2n.core.model1.CategoryDataModel;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by vipul.singhal on 20-05-2018.
 */

public class DocumentListToCategoryDataModelListMapper {
    List<CategoryDataModel> categoriesList = new ArrayList<>();
    CategoryDataModel categoryDataModel;

    @Inject
    DocumentListToCategoryDataModelListMapper() {

    }

    public List<CategoryDataModel> mapDocumentListToCategoriesList(List<QueryDocumentSnapshot> documentSnapshotList) {

        for (QueryDocumentSnapshot documentSnapshot : documentSnapshotList) {

            categoryDataModel = new CategoryDataModel();
            categoryDataModel.setCategoryName(documentSnapshot.getData().get("categoryName").toString());
            categoryDataModel.setCategoryImageURL(documentSnapshot.getData().get("categoryImageURL").toString());
            categoriesList.add(categoryDataModel);
        }
        return categoriesList;
    }
}
