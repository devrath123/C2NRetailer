package com.example.c2n.core.mapper;

import android.content.Context;
import android.util.Log;

import com.example.c2n.core.model1.OfferDataModel;
import com.example.c2n.core.model1.ShopDataModel;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by shivani.singh on 20-07-2018.
 */

public class ListQueryDocumentSnapshopToListShopDataModel {

    List<ShopDataModel> shopDataModels = new ArrayList<>();

    @Inject
    ListQueryDocumentSnapshopToListShopDataModel() {

    }

    public List<ShopDataModel> mapListQueryDocumentSnapshopToListShopDataModel(Context context, List<QueryDocumentSnapshot> queryDocumentSnapshots) {
        if (shopDataModels.size() != 0) {
            shopDataModels.clear();
        }
        for (QueryDocumentSnapshot queryDocumentSnapshot : queryDocumentSnapshots) {
            ShopDataModel shopDataModel = new ShopDataModel();
            if (queryDocumentSnapshot.get("retailerID") != null)
                shopDataModel.setRetailerID(queryDocumentSnapshot.get("retailerID").toString());
            if (queryDocumentSnapshot.get("shopID") != null)
                shopDataModel.setShopID(queryDocumentSnapshot.get("shopID").toString());
            if (queryDocumentSnapshot.get("shopAddress") != null)
                shopDataModel.setShopAddress(queryDocumentSnapshot.get("shopAddress").toString());
            if (queryDocumentSnapshot.get("shopCellNo") != null)
                shopDataModel.setShopCellNo(queryDocumentSnapshot.get("shopCellNo").toString());
            if (queryDocumentSnapshot.getLong("shopLongitude") != null)
                shopDataModel.setShopLongitude(queryDocumentSnapshot.getDouble("shopLongitude"));
            if (queryDocumentSnapshot.getLong("shopLatitude") != null)
                shopDataModel.setShopLatitude(queryDocumentSnapshot.getDouble("shopLatitude"));
            if (queryDocumentSnapshot.get("shopName") != null)
                shopDataModel.setShopName(queryDocumentSnapshot.get("shopName").toString());
            if (queryDocumentSnapshot.get("shopImageURL") != null)
                shopDataModel.setShopImageURL(queryDocumentSnapshot.get("shopImageURL").toString());
            shopDataModels.add(shopDataModel);
        }
        return shopDataModels;
    }
}
