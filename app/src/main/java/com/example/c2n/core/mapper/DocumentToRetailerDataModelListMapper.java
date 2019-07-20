package com.example.c2n.core.mapper;

import com.example.c2n.core.model1.RetailerDataModel;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by shivani.singh on 26-07-2018.
 */

public class DocumentToRetailerDataModelListMapper {

    List<RetailerDataModel> retailersList = new ArrayList<>();

    @Inject
    public DocumentToRetailerDataModelListMapper() {
    }

    public List<RetailerDataModel> mapDocumentToRetailerDataModelList(List<QueryDocumentSnapshot> documentSnapshotList) {

        RetailerDataModel retailerDataModel = new RetailerDataModel();
        for (QueryDocumentSnapshot documentSnapshot : documentSnapshotList) {
            //        userList.add(new UserDataModel(documentSnapshot.getCategory().get("userType").toString(),documentSnapshot.getCategory().get("userName").toString(),documentSnapshot.getCategory().get("userEmail").toString(),documentSnapshot.getCategory().get("userPassword").toString(),documentSnapshot.getCategory().get("userAddress").toString(),documentSnapshot.getCategory().get("userMobileNo").toString(),documentSnapshot.getCategory().get("userProfilePicUrl").toString(),documentSnapshot.getCategory().get("userDOB").toString(),documentSnapshot.getCategory().get("userGender").toString()));
            if (documentSnapshot.getData().get("retailerMobileNo") != null) {
                retailerDataModel.setRetailerMobileNo(documentSnapshot.get("retailerMobileNo").toString());
            }

            if (documentSnapshot.getData().get("retailerAddress") != null) {
                 retailerDataModel.setRetailerAddress(documentSnapshot.get("retailerAddress").toString());
            }

            if (documentSnapshot.getData().get("retailerImageURL") != null) {
                retailerDataModel.setRetailerImageURL(documentSnapshot.get("retailerImageURL").toString());
            }

            retailerDataModel.setRetailerID(documentSnapshot.getData().get("retailerID").toString());
            retailerDataModel.setRetailerName(documentSnapshot.getData().get("retailerName").toString());

            retailersList.add(retailerDataModel);

        }
        return retailersList;
    }
}

