package com.example.c2n.core.mapper;

import com.example.c2n.core.model1.TestimonialDataModel;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by shivani.singh on 20-07-2018.
 */

public class TestimonialDataModelMapper {

    List<TestimonialDataModel> testimonialDataModels = new ArrayList<>();

    @Inject
    public TestimonialDataModelMapper() {

    }

    public List<TestimonialDataModel> mapDocumentTotestimonialsList(List<QueryDocumentSnapshot> documentSnapshotList) {

        for (QueryDocumentSnapshot documentSnapshot : documentSnapshotList) {
            TestimonialDataModel testimonialDataModel = new TestimonialDataModel();
            testimonialDataModel.setUserPhotoUrl(documentSnapshot.get("userPhotoUrl").toString());
            testimonialDataModel.setUserName(documentSnapshot.get("userName").toString());
            testimonialDataModel.setUserSuccessStory(documentSnapshot.get("userSuccessStory").toString());
            testimonialDataModels.add(testimonialDataModel);
        }
        return testimonialDataModels;
    }

}
