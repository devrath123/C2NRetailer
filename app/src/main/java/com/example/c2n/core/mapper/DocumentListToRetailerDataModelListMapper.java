package com.example.c2n.core.mapper;

import com.example.c2n.core.model1.RetailerDataModel;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by vipul.singhal on 25-07-2018.
 */

public class DocumentListToRetailerDataModelListMapper {
    List<RetailerDataModel> retailersList = new ArrayList<>();

    @Inject
    DocumentListToRetailerDataModelListMapper() {

    }

    public List<RetailerDataModel> mapDocumentListToRetailerDataModelList(List<QueryDocumentSnapshot> documentSnapshotList) {
        String mobileNo = "";
        String address = "";
        String imageURL = "";

        for (QueryDocumentSnapshot documentSnapshot : documentSnapshotList) {
            //        userList.add(new UserDataModel(documentSnapshot.getCategory().get("userType").toString(),documentSnapshot.getCategory().get("userName").toString(),documentSnapshot.getCategory().get("userEmail").toString(),documentSnapshot.getCategory().get("userPassword").toString(),documentSnapshot.getCategory().get("userAddress").toString(),documentSnapshot.getCategory().get("userMobileNo").toString(),documentSnapshot.getCategory().get("userProfilePicUrl").toString(),documentSnapshot.getCategory().get("userDOB").toString(),documentSnapshot.getCategory().get("userGender").toString()));
            if (documentSnapshot.getData().get("retailerMobileNo") != null) {
                mobileNo = documentSnapshot.getData().get("retailerMobileNo").toString();
            }

            if (documentSnapshot.getData().get("retailerAddress") != null) {
                address = documentSnapshot.getData().get("retailerAddress").toString();
            }

            if (documentSnapshot.getData().get("retailerImageURL") != null) {
                imageURL = documentSnapshot.getData().get("retailerImageURL").toString();
            }
            RetailerDataModel retailerDataModel = new RetailerDataModel();
            retailerDataModel.setRetailerID(documentSnapshot.getData().get("retailerID").toString());
            retailerDataModel.setRetailerName(documentSnapshot.getData().get("retailerName").toString());
            retailerDataModel.setRetailerMobileNo(mobileNo);
            retailerDataModel.setRetailerImageURL(imageURL);
            retailerDataModel.setRetailerAddress(address);
//            userList.add(new UserDataModel(documentSnapshot.getCategory().get("userType").toString(), documentSnapshot.getCategory().get("userName").toString(), documentSnapshot.getCategory().get("userEmail").toString(), documentSnapshot.getCategory().get("userPassword").toString(), mobileNo, address, documentSnapshot.getCategory().get("userProfilePicUrl").toString(), "", ""));
            retailersList.add(retailerDataModel);

        }
        return retailersList;
    }
}
