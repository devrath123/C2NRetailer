package com.example.c2n.core.mapper;

import android.util.Log;

import com.example.c2n.core.model1.ProductDataModel;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by vipul.singhal on 21-06-2018.
 */

public class DocumentSnapshotListToProductDataModelListMapper {
    List<ProductDataModel> productsDataModels = new ArrayList<>();

    @Inject
    DocumentSnapshotListToProductDataModelListMapper() {
    }

    public ProductDataModel mapDocumentListToProductsList(DocumentSnapshot documentSnapshot) {
//        Log.d("offered_pros_snaps", "" + queryDocumentSnapshots.size());
        if (productsDataModels.size() != 0)
            productsDataModels.clear();
//        for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
        Log.d("offered_pros_snap", documentSnapshot.toString());
        ProductDataModel productDataModel = new ProductDataModel();
        if (documentSnapshot.get("productID") != null)
            productDataModel.setProductID(documentSnapshot.get("productID").toString());
        if (documentSnapshot.get("productCategory") != null)
            productDataModel.setProductCategory(documentSnapshot.get("productCategory").toString());
        if (documentSnapshot.get("productImageURL") != null)
            productDataModel.setProductImageURL(documentSnapshot.get("productImageURL").toString());
        if (documentSnapshot.getLong("productMRP") != null)
            productDataModel.setProductMRP(documentSnapshot.getLong("productMRP"));
        if (documentSnapshot.get("productDescription") != null)
            productDataModel.setProductDescription(documentSnapshot.get("productDescription").toString());
        if (documentSnapshot.get("productName") != null)
            productDataModel.setProductName(documentSnapshot.get("productName").toString());
        if (documentSnapshot.getBoolean("productOfferStatus") != null)
            productDataModel.setProductOfferStatus(documentSnapshot.getBoolean("productOfferStatus"));
        if (documentSnapshot.getBoolean("productStockStatus") != null)
            productDataModel.setProductStockStatus(documentSnapshot.getBoolean("productStockStatus"));
//            if (documentSnapshot.get("productOffer") != null)
//                productDataModel.setProductOffer(documentQuerySnapshotToOfferDataModelMapper.mapDocumentQuerySnapshotToOfferDataModelMapper((HashMap) documentSnapshot.get("productOffer")));
//            else
//                productDataModel.setProductOffer(null);
////                productDataModel.setProductOffer(documentQuerySnapshotToOfferDataModelMapper.mapDocumentQuerySnapshotToOfferDataModelMapper(documentSnapshot));

        Log.d("offered_product_details", productDataModel.toString());
//            productsDataModels.add(productDataModel);

//            if (documentSnapshot.get("productScheme") != null && (documentSnapshot.get("productDescription") != null))
//                productsDataModels.add(new ProductDataModel(documentSnapshot.get("productOfferID").toString(), documentSnapshot.getId(), documentSnapshot.get("productCategory").toString(), documentSnapshot.get("productName").toString(), documentSnapshot.get("productPhotoUrl").toString(), documentSnapshot.get("productMRP").toString(), documentSnapshot.get("productScheme").toString(), documentSnapshot.get("shopEmail").toString(), documentSnapshot.get("productDescription").toString()));
//            else if (documentSnapshot.get("productScheme") == null)
//                productsDataModels.add(new ProductDataModel(documentSnapshot.get("productOfferID").toString(), documentSnapshot.getId(), documentSnapshot.get("productCategory").toString(), documentSnapshot.get("productName").toString(), documentSnapshot.get("productPhotoUrl").toString(), documentSnapshot.get("productMRP").toString(), "", documentSnapshot.get("shopEmail").toString(), documentSnapshot.get("productDescription").toString()));
//            else if ((documentSnapshot.get("productDescription") == null))
//                productsDataModels.add(new ProductDataModel(documentSnapshot.get("productOfferID").toString(), documentSnapshot.getId(), documentSnapshot.get("productCategory").toString(), documentSnapshot.get("productName").toString(), documentSnapshot.get("productPhotoUrl").toString(), documentSnapshot.get("productMRP").toString(), documentSnapshot.get("productScheme").toString(), documentSnapshot.get("shopEmail").toString(), ""));
//            else
//                productsDataModels.add(new ProductDataModel(documentSnapshot.get("productOfferID").toString(), documentSnapshot.getId(), documentSnapshot.get("productCategory").toString(), documentSnapshot.get("productName").toString(), documentSnapshot.get("productPhotoUrl").toString(), documentSnapshot.get("productMRP").toString(), "", documentSnapshot.get("shopEmail").toString(), ""));

//        Log.d("offered_product_list", productsDataModels.toString());

        return productDataModel;
    }
}
