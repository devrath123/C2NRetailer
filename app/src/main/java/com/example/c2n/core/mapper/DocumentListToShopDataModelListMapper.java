package com.example.c2n.core.mapper;

import android.util.Log;

import com.example.c2n.core.model1.ShopDataModel;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by vipul.singhal on 02-07-2018.
 */

public class DocumentListToShopDataModelListMapper {

    List<ShopDataModel> shopDataModels = new ArrayList<>();

    DocumentQuerySnapshotToShopOfferDataModelMapper documentQuerySnapshotToShopOfferDataModelMapper;

    @Inject
    DocumentListToShopDataModelListMapper(DocumentQuerySnapshotToShopOfferDataModelMapper documentQuerySnapshotToShopOfferDataModelMapper) {
        this.documentQuerySnapshotToShopOfferDataModelMapper = documentQuerySnapshotToShopOfferDataModelMapper;
    }

    public List<ShopDataModel> mapDocumentListToShopsList(List<QueryDocumentSnapshot> documentSnapshotList) {

        if (shopDataModels.size() != 0) {
            shopDataModels.clear();
            shopDataModels = new ArrayList<>();
        }
        for (QueryDocumentSnapshot queryDocumentSnapshot : documentSnapshotList) {
            Log.d("shop_document", queryDocumentSnapshot.toString());
            ShopDataModel shopDataModel = new ShopDataModel();
            if (queryDocumentSnapshot.get("retailerID") != null)
                shopDataModel.setRetailerID(queryDocumentSnapshot.get("retailerID").toString());
            if (queryDocumentSnapshot.get("shopID") != null)
                shopDataModel.setShopID(queryDocumentSnapshot.get("shopID").toString());
            if (queryDocumentSnapshot.get("shopAddress") != null)
                shopDataModel.setShopAddress(queryDocumentSnapshot.get("shopAddress").toString());
            if (queryDocumentSnapshot.get("shopCellNo") != null)
                shopDataModel.setShopCellNo(queryDocumentSnapshot.get("shopCellNo").toString());
            if (queryDocumentSnapshot.getDouble("shopLongitude") != null)
                shopDataModel.setShopLongitude(queryDocumentSnapshot.getDouble("shopLongitude"));
            if (queryDocumentSnapshot.getDouble("shopLatitude") != null)
                shopDataModel.setShopLatitude(queryDocumentSnapshot.getDouble("shopLatitude"));
            if (queryDocumentSnapshot.get("shopName") != null)
                shopDataModel.setShopName(queryDocumentSnapshot.get("shopName").toString());
            if (queryDocumentSnapshot.get("shopImageURL") != null)
                shopDataModel.setShopImageURL(queryDocumentSnapshot.get("shopImageURL").toString());
            shopDataModels.add(shopDataModel);
        }

        Log.d("shops_list", shopDataModels.toString());
        return shopDataModels;
    }
}
