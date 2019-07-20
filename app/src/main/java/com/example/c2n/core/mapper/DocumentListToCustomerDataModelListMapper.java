package com.example.c2n.core.mapper;

import com.example.c2n.core.model1.CustomerDataModel;
import com.example.c2n.core.model1.RetailerDataModel;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by vipul.singhal on 25-07-2018.
 */

public class DocumentListToCustomerDataModelListMapper {
    List<CustomerDataModel> customersList = new ArrayList<>();

    @Inject
    DocumentListToCustomerDataModelListMapper() {

    }

    public List<CustomerDataModel> mapDocumentListToCustomerataModelList(List<QueryDocumentSnapshot> documentSnapshotList) {
        String mobileNo = "";
        String address = "";
        String imageURL = "";

        for (QueryDocumentSnapshot documentSnapshot : documentSnapshotList) {
            //        userList.add(new UserDataModel(documentSnapshot.getCategory().get("userType").toString(),documentSnapshot.getCategory().get("userName").toString(),documentSnapshot.getCategory().get("userEmail").toString(),documentSnapshot.getCategory().get("userPassword").toString(),documentSnapshot.getCategory().get("userAddress").toString(),documentSnapshot.getCategory().get("userMobileNo").toString(),documentSnapshot.getCategory().get("userProfilePicUrl").toString(),documentSnapshot.getCategory().get("userDOB").toString(),documentSnapshot.getCategory().get("userGender").toString()));
            if (documentSnapshot.getData().get("customerMobileNo") != null) {
                mobileNo = documentSnapshot.getData().get("customerMobileNo").toString();
            }

            if (documentSnapshot.getData().get("customerAddress") != null) {
                address = documentSnapshot.getData().get("customerAddress").toString();
            }

            if (documentSnapshot.getData().get("customerImageURL") != null) {
                imageURL = documentSnapshot.getData().get("customerImageURL").toString();
            }
            CustomerDataModel customerDataModel = new CustomerDataModel();
            customerDataModel.setCustomerID(documentSnapshot.getData().get("customerID").toString());
            customerDataModel.setCustomerName(documentSnapshot.getData().get("customerName").toString());
            customerDataModel.setCustomerMobileNo(mobileNo);
            customerDataModel.setCustomerImageURL(imageURL);
            customerDataModel.setCustomerAddress(address);
//            userList.add(new UserDataModel(documentSnapshot.getCategory().get("userType").toString(), documentSnapshot.getCategory().get("userName").toString(), documentSnapshot.getCategory().get("userEmail").toString(), documentSnapshot.getCategory().get("userPassword").toString(), mobileNo, address, documentSnapshot.getCategory().get("userProfilePicUrl").toString(), "", ""));
            customersList.add(customerDataModel);

        }
        return customersList;
    }
}
