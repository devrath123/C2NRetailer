package com.example.c2n.core.mappers;

import android.content.Context;
import android.util.Log;

import com.example.c2n.core.models.OfferDataModel;
import com.example.c2n.core.models.ProductDataModel;
import com.example.c2n.core.models.ShopDataModel;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import bolts.Bolts;

/**
 * Created by shivani.singh on 20-07-2018.
 */

public class ListDocumentSnapshotToListShopDataModel {

    private final String TAG = "ListDocumentSnapshotToL";

    @Inject
    ListDocumentSnapshotToListShopDataModel() {

    }

    public List<ShopDataModel> mapListQueryDocumentSnapshopToListShopDataModel(Context context, DocumentSnapshot documentSnapshot) {
        List<ShopDataModel> shopsDataModel = new ArrayList<>();
        Log.d(TAG, "mapListQueryDocumentSnapshopToListShopDataModel : " + documentSnapshot);
//        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
//            ShopDataModel shopDataModel = new ShopDataModel();
//            if (documentSnapshot.get("retailerID") != null)
//                shopDataModel.setRetailerID(documentSnapshot.get("retailerID").toString());
//            if (documentSnapshot.get("shopID") != null)
//                shopDataModel.setShopEmail(documentSnapshot.get("shopID").toString());
//            if (documentSnapshot.get("shopAddress") != null)
//                shopDataModel.setShopAddress(documentSnapshot.get("shopAddress").toString());
//            if (documentSnapshot.get("shopCellNo") != null)
//                shopDataModel.setShopCellNo(documentSnapshot.get("shopCellNo").toString());
//            if (documentSnapshot.getLong("shopLongitude") != null)
//                shopDataModel.setShopLongitude(documentSnapshot.getDouble("shopLongitude"));
//            if (documentSnapshot.getLong("shopLatitude") != null)
//                shopDataModel.setShopLatitude(documentSnapshot.getDouble("shopLatitude"));
//            if (documentSnapshot.get("shopName") != null)
//                shopDataModel.setShopName(documentSnapshot.get("shopName").toString());
//            if (documentSnapshot.get("shopImageURL") != null)
//                shopDataModel.setShopImageURL(documentSnapshot.get("shopImageURL").toString());
//            shopDataModels.add(shopDataModel);

////        if (documentSnapshot != null) {
////            if (documentSnapshot.get("retailerShops") != null) {
//////                shopListDataModel.setRetailerShops((List<ShopDataModel>) documentSnapshot.get("retailerShops"));
////                Log.d("shops_document_list_", "" + documentSnapshot.get("retailerShops"));
////                ArrayList arrayList = (ArrayList) documentSnapshot.get("retailerShops");
////                for (int i = 0; i < arrayList.size(); i++) {
////                    HashMap shop = (HashMap) arrayList.get(i);
////                    ShopDataModel shopDataModel = new ShopDataModel();
////
////                    if (shop.get("shopID") != null)
////                        shopDataModel.setShopID(shop.get("shopID").toString());
////                    if (shop.get("retailerID") != null)
////                        shopDataModel.setRetailerID(shop.get("retailerID").toString());
////                    if (shop.get("shopEmail") != null)
////                        shopDataModel.setShopEmail(shop.get("shopEmail").toString());
////                    if (shop.get("shopAddress") != null)
////                        shopDataModel.setShopAddress(shop.get("shopAddress").toString());
////                    if (shop.get("shopCellNo") != null)
////                        shopDataModel.setShopCellNo(shop.get("shopCellNo").toString());
////                    if (shop.get("shopLongitude") != null)
////                        shopDataModel.setShopLongitude(Double.parseDouble(shop.get("shopLongitude").toString()));
////                    if (shop.get("shopLatitude") != null)
////                        shopDataModel.setShopLatitude(Double.parseDouble(shop.get("shopLatitude").toString()));
////                    if (shop.get("shopName") != null)
////                        shopDataModel.setShopName(shop.get("shopName").toString());
////                    if (shop.get("shopImageURL") != null)
////                        shopDataModel.setShopImageURL(shop.get("shopImageURL").toString());
////                    if (shop.get("productsList") != null)
////                        shopDataModel.setProductsList((List<ProductDataModel>) shop.get("productsList"));
////                    if (shop.get("offerDataModel") != null)
////                        shopDataModel.setOfferDataModel((OfferDataModel) shop.get("offerDataModel"));
////                    Log.d("shop-> ", shopDataModel.toString());
////                    shopDataModels.add(shopDataModel);
////
////                }
////            }
//
////            for (ShopDataModel shopDataModel : shopListDataModel.getRetailerShops()) {
////                shopDataModels.add(shopDataModel);
////            }
//        }
        if (documentSnapshot != null) {
            List<HashMap<String, Object>> shops = (List<HashMap<String, Object>>) documentSnapshot.get("retailerShops");
            Log.d(TAG, "mapListQueryDocumentSnapshopToListShopDataModel Shops : " + shops);
//            List<ShopDataModel> shopsDataModel = new ArrayList<>();
            for (int i = 0; i < shops.size(); i++) {
                HashMap<String, Object> shop = shops.get(i);
                ShopDataModel shopDataModel = new ShopDataModel();
                shopDataModel.setShopID((String) shop.get("shopID"));
                shopDataModel.setRetailerID((String) shop.get("retailerID"));
                shopDataModel.setShopName((String) shop.get("shopName"));
                shopDataModel.setShopAddress((String) shop.get("shopAddress"));
                shopDataModel.setShopCellNo((String) shop.get("shopCellNo"));
                shopDataModel.setShopEmail((String) shop.get("shopEmail"));
                shopDataModel.setShopImageURL((String) shop.get("shopImageURL"));
                shopDataModel.setShopLatitude((Double) shop.get("shopLatitude"));
                shopDataModel.setShopLongitude((Double) shop.get("shopLongitude"));
                shopDataModel.setOfferDataModel(null);

                if (shop.get("productsList") != null) {
                    List<ProductDataModel> productsDataModel = new ArrayList<>();
                    List<HashMap<String, Object>> products = (List<HashMap<String, Object>>) shop.get("productsList");
                    for (int j = 0; j < products.size(); j++) {
                        HashMap<String, Object> product = products.get(j);
                        ProductDataModel productDataModel = new ProductDataModel();
                        productDataModel.setProductID((String) product.get("productID"));
                        productDataModel.setProductName((String) product.get("productName"));
                        productDataModel.setProductImageURL((String) product.get("productImageURL"));
                        productDataModel.setProductMRP(Double.parseDouble(product.get("productMRP").toString()));
                        productDataModel.setProductCategory((String) product.get("productCategory"));
                        productDataModel.setProductDescription((String) product.get("productDescription"));
                        productDataModel.setProductStockStatus((boolean) product.get("productStockStatus"));
                        productDataModel.setProductOfferStatus((boolean) product.get("productOfferStatus"));
                        productDataModel.setShopDataModels(null);

                        if (product.get("productOffer") != null) {
                            HashMap<String, Object> offer = (HashMap<String, Object>) product.get("productOffer");
                            Log.d(TAG, "mapListQueryDocumentSnapshopToListShopDataModel OfferDataModel : " + offer);
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

                            productDataModel.setProductOffer(offerDataModel);
                        } else {
                            productDataModel.setProductOffer(null);
                        }
                        productsDataModel.add(productDataModel);
                    }
                    shopDataModel.setProductsList(productsDataModel);
                } else {
                    shopDataModel.setProductsList(null);
                }
                Log.d(TAG, "mapListQueryDocumentSnapshopToListShopDataModel ShopDatModel : " + shopDataModel);
                shopsDataModel.add(shopDataModel);
            }
        }
//        }
        Log.d("shops_list", shopsDataModel.toString());
        return shopsDataModel;
    }
}
