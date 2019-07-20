package com.example.c2n.core.mapper;

import android.util.Log;

import com.example.c2n.core.models.CategoryDataModel;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by vipul.singhal on 21-06-2018.
 */

public class ListDocumentQuerySnapshotToListCategoryDataModelMapper {

    private static final String TAG = ListDocumentQuerySnapshotToListCategoryDataModelMapper.class.getSimpleName();

    @Inject
    ListDocumentQuerySnapshotToListCategoryDataModelMapper() {
    }

    public List<CategoryDataModel> mapListDocumentQuerySnapshotToListCategoryDataModelMapper(List<QueryDocumentSnapshot> queryDocumentSnapshots) {
        Log.d(TAG, "mapListDocumentQuerySnapshotToListCategoryDataModelMapper: queryDocumentSnapshots: " + queryDocumentSnapshots.size());
        List<CategoryDataModel> categoryDataModels = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            CategoryDataModel categoryDataModel = new CategoryDataModel();
            if (queryDocumentSnapshots.get(i).get("categoryName") != null)
                categoryDataModel.setCategoryName(queryDocumentSnapshots.get(i).get("categoryName").toString());
            if (queryDocumentSnapshots.get(i).get("categoryImageURL") != null)
                categoryDataModel.setCategoryImageURL(queryDocumentSnapshots.get(i).get("categoryImageURL").toString());
            Log.d("categoryimage", categoryDataModel.getCategoryImageURL());

            categoryDataModels.add(categoryDataModel);
        }
//        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
//            CategoryDataModel categoryDataModel = new CategoryDataModel();
//            if (documentSnapshot.get("categoryName") != null)
//                categoryDataModel.setCategoryName(documentSnapshot.get("categoryName").toString());
//            if (documentSnapshot.get("categoryImageURL") != null)
//                categoryDataModel.setCategoryImageURL(documentSnapshot.get("categoryImageURL").toString());
//            Log.d("categoryimage", categoryDataModel.getCategoryImageURL());
//
//            categoryDataModels.add(categoryDataModel);
////            Log.d(TAG, "mapListDocumentQuerySnapshotToListCategoryDataModelMapper: Size : " + categoryDataModels.size());
//
//        }
        return categoryDataModels;
    }
}