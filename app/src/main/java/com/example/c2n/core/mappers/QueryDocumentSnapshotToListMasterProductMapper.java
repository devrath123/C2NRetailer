package com.example.c2n.core.mappers;

import com.example.c2n.core.models.MasterProductDataModel;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by vipul.singhal on 23-05-2018.
 */

public class QueryDocumentSnapshotToListMasterProductMapper {

    @Inject
    QueryDocumentSnapshotToListMasterProductMapper() {

    }

    public List<MasterProductDataModel> mapQueryDocumentSnapshotToListMasterProductMapper(List<QueryDocumentSnapshot> documentSnapshotList) {

        List<MasterProductDataModel> masterProducts = new ArrayList<>();

        for (QueryDocumentSnapshot documentSnapshot : documentSnapshotList) {
            MasterProductDataModel masterProduct = new MasterProductDataModel();
            if (documentSnapshot.getId() != null)
                masterProduct.setProductID(documentSnapshot.getId());
            if (documentSnapshot.get("productName") != null)
                masterProduct.setProductName(documentSnapshot.get("productName").toString());
            if (documentSnapshot.get("productImageURL") != null)
                masterProduct.setProductImageURL(documentSnapshot.get("productImageURL").toString());
            if (documentSnapshot.get("productCategory") != null)
                masterProduct.setProductCategory(documentSnapshot.get("productCategory").toString());
//            if (documentSnapshot.getLong("productMRP") != null)
//                masterProduct.setProductMRP(documentSnapshot.getLong("productMRP"));
            if (documentSnapshot.get("productDescription") != null)
                masterProduct.setProductDescription(documentSnapshot.get("productDescription").toString());
            masterProducts.add(masterProduct);
        }
        return masterProducts;
    }
}
