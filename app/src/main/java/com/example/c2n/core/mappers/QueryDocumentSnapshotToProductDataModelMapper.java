package com.example.c2n.core.mappers;

import com.example.c2n.core.models.OfferDataModel;
import com.example.c2n.core.models.ProductDataModel;
import com.example.c2n.core.models.ShopDataModel;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by vipul.singhal on 23-05-2018.
 */

public class QueryDocumentSnapshotToProductDataModelMapper {

    @Inject
    QueryDocumentSnapshotToProductDataModelMapper() {

    }

    public ProductDataModel mapQueryDocumentSnapshotToProductDataModelMapper(DocumentSnapshot documentSnapshot) {

        ProductDataModel productDataModel = new ProductDataModel();
        productDataModel.setProductID(documentSnapshot.getId());
        productDataModel.setProductName((String) documentSnapshot.get("productName"));
        productDataModel.setProductImageURL((String) documentSnapshot.get("productImageURL"));
        productDataModel.setProductMRP(Double.parseDouble(documentSnapshot.get("productMRP").toString()));
        productDataModel.setProductCategory((String) documentSnapshot.get("productCategory"));
        productDataModel.setProductDescription((String) documentSnapshot.get("productDescription"));
        productDataModel.setProductStockStatus((boolean) documentSnapshot.get("productStockStatus"));
        productDataModel.setProductOfferStatus((boolean) documentSnapshot.get("productOfferStatus"));

        if (documentSnapshot.get("shopDataModels") != null) {
            List<ShopDataModel> shopDataModels = new ArrayList<>();
            List<HashMap<String, Object>> shops = (List<HashMap<String, Object>>) documentSnapshot.get("shopDataModels");
            for (int i = 0; i < shops.size(); i++) {
                HashMap<String, Object> shop = shops.get(i);
                ShopDataModel shopDataModel = new ShopDataModel();
                shopDataModel.setRetailerID((String) shop.get("retailerID"));
                shopDataModel.setShopName((String) shop.get("shopName"));
                shopDataModel.setShopAddress((String) shop.get("shopAddress"));
                shopDataModel.setShopCellNo((String) shop.get("shopCellNo"));
                shopDataModel.setShopID((String) shop.get("shopID"));
                shopDataModel.setShopImageURL((String) shop.get("shopImageURL"));
                shopDataModel.setShopEmail((String) shop.get("shopEmail"));
                shopDataModel.setShopLatitude((Double) shop.get("shopLatitude"));
                shopDataModel.setShopLongitude((Double) shop.get("shopLongitude"));
                shopDataModel.setProductMRP(Double.parseDouble(documentSnapshot.get("productMRP").toString()));
                shopDataModel.setProductsList(null);
                if (shop.get("offerDataModel") != null) {
                    HashMap<String, Object> offer = (HashMap<String, Object>) shop.get("offerDataModel");
                    OfferDataModel offerDataModel = new OfferDataModel();
                    offerDataModel.setOfferID((String) offer.get("offerID"));
                    offerDataModel.setOfferDiscount(Integer.parseInt(offer.get("offerDiscount").toString()));
                    offerDataModel.setOfferStartDate((Date) offer.get("offerStartDate"));
                    offerDataModel.setOfferEndDate((Date) offer.get("offerEndDate"));
                    offerDataModel.setOfferName((String) offer.get("offerName"));
                    offerDataModel.setSun((boolean) offer.get("sun"));
                    offerDataModel.setMon((boolean) offer.get("mon"));
                    offerDataModel.setTue((boolean) offer.get("tue"));
                    offerDataModel.setWed((boolean) offer.get("wed"));
                    offerDataModel.setThu((boolean) offer.get("thu"));
                    offerDataModel.setFri((boolean) offer.get("fri"));
                    offerDataModel.setSat((boolean) offer.get("sat"));
                    offerDataModel.setOfferStatus((boolean) offer.get("offerStatus"));
                    offerDataModel.setShopDataModels(null);

                    shopDataModel.setOfferDataModel(offerDataModel);
                } else {
                    shopDataModel.setOfferDataModel(null);
                }
                shopDataModels.add(shopDataModel);
            }
            productDataModel.setShopDataModels(shopDataModels);
        } else {
            productDataModel.setShopDataModels(null);
        }
        return productDataModel;
    }
}
