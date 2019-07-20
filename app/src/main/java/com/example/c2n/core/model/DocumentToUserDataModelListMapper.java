package com.example.c2n.core.model;

import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by vipul.singhal on 11-05-2018.
 */

public class DocumentToUserDataModelListMapper {
    List<UserDataModel> userList = new ArrayList<>();

    @Inject
    DocumentToUserDataModelListMapper() {

    }

    public List<UserDataModel> mapDocumentListToUsernameBoolean(List<QueryDocumentSnapshot> documentSnapshotList) {
        String mobileNo = "";
        String address = "";

        for (QueryDocumentSnapshot documentSnapshot : documentSnapshotList) {
            //        userList.add(new UserDataModel(documentSnapshot.getCategory().get("userType").toString(),documentSnapshot.getCategory().get("userName").toString(),documentSnapshot.getCategory().get("userEmail").toString(),documentSnapshot.getCategory().get("userPassword").toString(),documentSnapshot.getCategory().get("userAddress").toString(),documentSnapshot.getCategory().get("userMobileNo").toString(),documentSnapshot.getCategory().get("userProfilePicUrl").toString(),documentSnapshot.getCategory().get("userDOB").toString(),documentSnapshot.getCategory().get("userGender").toString()));
            if (documentSnapshot.getData().get("userMobileNo") != null) {
                mobileNo = documentSnapshot.getData().get("userMobileNo").toString();
            }

            if (documentSnapshot.getData().get("userAddress") != null) {
                address = documentSnapshot.getData().get("userAddress").toString();
            }

//            userList.add(new UserDataModel(documentSnapshot.getCategory().get("userType").toString(), documentSnapshot.getCategory().get("userName").toString(), documentSnapshot.getCategory().get("userEmail").toString(), documentSnapshot.getCategory().get("userPassword").toString(), mobileNo, address, documentSnapshot.getCategory().get("userProfilePicUrl").toString(), "", ""));
            userList.add(new UserDataModel(documentSnapshot.get("userType").toString(), documentSnapshot.get("userName").toString(), documentSnapshot.get("userEmail").toString(), documentSnapshot.get("userPassword").toString(), mobileNo, address, documentSnapshot.get("userProfilePicUrl").toString(), "", ""));

        }
        return userList;
    }
}
