package com.example.c2n.core.model;

import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by shivani.singh on 21-06-2018.
 */

public class RetailerDataModelMapper {

    List<RetailerDataModel> retailerDataModels = new ArrayList<>();

    @Inject
    public RetailerDataModelMapper() {

    }

    public List<RetailerDataModel> mapDocumentToRetailerList(List<QueryDocumentSnapshot> documentSnapshotList) {

        for (QueryDocumentSnapshot documentSnapshot : documentSnapshotList) {

            retailerDataModels.add(new RetailerDataModel(documentSnapshot.getData().get("userPhotoUrl").toString(), documentSnapshot.getData().get("userName").toString(), documentSnapshot.getData().get("userSuccessStory").toString()));
        }
        return retailerDataModels;
    }

}
