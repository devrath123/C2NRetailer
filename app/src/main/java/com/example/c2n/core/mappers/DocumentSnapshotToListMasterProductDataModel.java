package com.example.c2n.core.mappers;

import android.content.Context;

import com.example.c2n.core.models.MasterProductDataModel;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by shivani.singh on 20-07-2018.
 */

public class DocumentSnapshotToListMasterProductDataModel {

    private static final String TAG = DocumentSnapshotToListMasterProductDataModel.class.getSimpleName();

    @Inject
    DocumentSnapshotToListMasterProductDataModel() {

    }

    public List<MasterProductDataModel> mapDocumentSnapshotToListMasterProductDataModel(Context context, DocumentSnapshot documentSnapshot) {
        List<MasterProductDataModel> masterProductDataModels = new ArrayList<>();
        if (documentSnapshot.get("mylist") != null) {
            List<HashMap<String, Object>> products = (List<HashMap<String, Object>>) documentSnapshot.get("mylist");
            for (int i = 0; i < products.size(); i++) {
                HashMap<String, Object> product = products.get(i);
                MasterProductDataModel masterProductDataModel = new MasterProductDataModel();
                masterProductDataModel.setProductID((String) product.get("productID"));
                masterProductDataModel.setProductName((String) product.get("productName"));
                masterProductDataModel.setProductCategory((String) product.get("productCategory"));
                masterProductDataModel.setProductImageURL((String) product.get("productImageURL"));
                masterProductDataModel.setProductDescription((String) product.get("productDescription"));

                masterProductDataModels.add(masterProductDataModel);
            }
        }
        return masterProductDataModels;
    }
}
