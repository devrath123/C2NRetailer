package com.example.c2n.core.mappers;

import android.util.Log;

import com.example.c2n.core.mapper.DocumentQuerySnapshotToOfferDataModelMapper;
import com.example.c2n.core.mapper.DocumentQuerySnapshotToShopOfferDataModelMapper;
import com.example.c2n.core.models.ShopDataModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by vipul.singhal on 02-07-2018.
 */

public class ListShopsHashmapToShopDataModelListMapper {

    List<ShopDataModel> shopDataModels = new ArrayList<>();

    DocumentQuerySnapshotToShopOfferDataModelMapper documentQuerySnapshotToShopOfferDataModelMapper;
    DocumentQuerySnapshotToOfferDataModelMapper documentQuerySnapshotToOfferDataModelMapper;

    @Inject
    ListShopsHashmapToShopDataModelListMapper(DocumentQuerySnapshotToShopOfferDataModelMapper documentQuerySnapshotToShopOfferDataModelMapper, DocumentQuerySnapshotToOfferDataModelMapper documentQuerySnapshotToOfferDataModelMapper) {
        this.documentQuerySnapshotToShopOfferDataModelMapper = documentQuerySnapshotToShopOfferDataModelMapper;
        this.documentQuerySnapshotToOfferDataModelMapper = documentQuerySnapshotToOfferDataModelMapper;
    }

    public List<ShopDataModel> mapShopsHashmapListToShopsList(List<HashMap<String, Object>> documentSnapshotList) {

        if (shopDataModels.size() != 0) {
            shopDataModels.clear();
            shopDataModels = new ArrayList<>();
        }
        for (HashMap<String, Object> shopHashmap : documentSnapshotList) {
            Log.d("shop_document", shopHashmap.toString());
            ShopDataModel shopDataModel = new ShopDataModel();
            if (shopHashmap.get("retailerID") != null)
                shopDataModel.setRetailerID(shopHashmap.get("retailerID").toString());
            if (shopHashmap.get("shopID") != null)
                shopDataModel.setShopID(shopHashmap.get("shopID").toString());
            if (shopHashmap.get("shopEmail") != null)
                shopDataModel.setShopEmail(shopHashmap.get("shopEmail").toString());
            if (shopHashmap.get("shopAddress") != null)
                shopDataModel.setShopAddress(shopHashmap.get("shopAddress").toString());
            if (shopHashmap.get("shopCellNo") != null)
                shopDataModel.setShopCellNo(shopHashmap.get("shopCellNo").toString());
            if (shopHashmap.get("shopLongitude") != null)
                shopDataModel.setShopLongitude(Double.parseDouble(shopHashmap.get("shopLongitude").toString()));
            if (shopHashmap.get("shopLatitude") != null)
                shopDataModel.setShopLatitude(Double.parseDouble(shopHashmap.get("shopLatitude").toString()));
            if (shopHashmap.get("shopName") != null)
                shopDataModel.setShopName(shopHashmap.get("shopName").toString());
            if (shopHashmap.get("shopImageURL") != null)
                shopDataModel.setShopImageURL(shopHashmap.get("shopImageURL").toString());
            if (shopHashmap.get("offerDataModel") != null)
                shopDataModel.setOfferDataModel(documentQuerySnapshotToOfferDataModelMapper.mapOfferHashmapToOfferDataModelMapper((HashMap<String, Object>) shopHashmap.get("offerDataModel")));
            shopDataModels.add(shopDataModel);
        }

        Log.d("shops_list", shopDataModels.toString());
        return shopDataModels;
    }
}
