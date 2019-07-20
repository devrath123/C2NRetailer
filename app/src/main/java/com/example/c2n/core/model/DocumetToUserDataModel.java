package com.example.c2n.core.model;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import android.util.Log;

import javax.inject.Inject;

/**
 * Created by roshan.nimje on 21-05-2018.
 */

public class DocumetToUserDataModel {

//    UserDataModel userDataModel;

    @Inject
    DocumetToUserDataModel() {

    }

    public UserDataModel mapDocumentToUserDataModel(DocumentSnapshot documentSnapshot) {
//        Log.d("mapDocumentToUserDataMo", "" + documentReference.get().getResult().get("userMobileNo"));
//        return null;
        return new UserDataModel(documentSnapshot.get("userType").toString(), documentSnapshot.get("userName").toString(), documentSnapshot.get("userDOB").toString(), documentSnapshot.get("userMobileNo").toString(), documentSnapshot.get("userEmail").toString(), documentSnapshot.get("userGender").toString(), documentSnapshot.get("userAddress").toString(), documentSnapshot.getLong("userLatitude"), documentSnapshot.getLong("userLongitude"), documentSnapshot.get("userProfilePicUrl").toString());
    }
}
