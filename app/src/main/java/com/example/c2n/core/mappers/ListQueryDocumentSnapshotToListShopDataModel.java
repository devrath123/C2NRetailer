package com.example.c2n.core.mappers;

import android.content.Context;
import android.util.Log;

import com.example.c2n.core.models.OfferDataModel;
import com.example.c2n.core.models.ProductDataModel;
import com.example.c2n.core.models.ShopDataModel;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by shivani.singh on 20-07-2018.
 */

public class ListQueryDocumentSnapshotToListShopDataModel {

    private static final String TAG = ListQueryDocumentSnapshotToListShopDataModel.class.getSimpleName();

    @Inject
    ListQueryDocumentSnapshotToListShopDataModel() {

    }

    public List<ShopDataModel> mapListQueryDocumentSnapshopToListShopDataModel(Context context, List<QueryDocumentSnapshot> queryDocumentSnapshots) {
        Log.d(TAG, "mapListQueryDocumentSnapshopToListShopDataModel: " + queryDocumentSnapshots.size());
        List<ShopDataModel> allShopsList = new ArrayList<>();
        for (QueryDocumentSnapshot queryDocumentSnapshot : queryDocumentSnapshots) {
            List<ShopDataModel> shopDataModels = new ArrayList<>();
//            ShopDataModel shopDataModel = new ShopDataModel();
//            if (queryDocumentSnapshot.get("retailerID") != null)
//                shopDataModel.setRetailerID(queryDocumentSnapshot.get("retailerID").toString());
//            if (queryDocumentSnapshot.get("shopID") != null)
//                shopDataModel.setShopEmail(queryDocumentSnapshot.get("shopID").toString());
//            if (queryDocumentSnapshot.get("shopAddress") != null)
//                shopDataModel.setShopAddress(queryDocumentSnapshot.get("shopAddress").toString());
//            if (queryDocumentSnapshot.get("shopCellNo") != null)
//                shopDataModel.setShopCellNo(queryDocumentSnapshot.get("shopCellNo").toString());
//            if (queryDocumentSnapshot.getLong("shopLongitude") != null)
//                shopDataModel.setShopLongitude(queryDocumentSnapshot.getDouble("shopLongitude"));
//            if (queryDocumentSnapshot.getLong("shopLatitude") != null)
//                shopDataModel.setShopLatitude(queryDocumentSnapshot.getDouble("shopLatitude"));
//            if (queryDocumentSnapshot.get("shopName") != null)
//                shopDataModel.setShopName(queryDocumentSnapshot.get("shopName").toString());
//            if (queryDocumentSnapshot.get("shopImageURL") != null)
//                shopDataModel.setShopImageURL(queryDocumentSnapshot.get("shopImageURL").toString());
//            shopDataModels.add(shopDataModel);

            if (queryDocumentSnapshot != null) {
                if (queryDocumentSnapshot.get("retailerShops") != null) {
                    Log.d("shops_document_list_", "" + queryDocumentSnapshot.get("retailerShops"));
                    ArrayList arrayList = (ArrayList) queryDocumentSnapshot.get("retailerShops");
                    for (int i = 0; i < arrayList.size(); i++) {
                        HashMap shop = (HashMap) arrayList.get(i);
                        ShopDataModel shopDataModel = new ShopDataModel();

                        if (shop.get("shopID") != null)
                            shopDataModel.setShopID(shop.get("shopID").toString());
                        if (shop.get("retailerID") != null)
                            shopDataModel.setRetailerID(shop.get("retailerID").toString());
                        if (shop.get("shopEmail") != null)
                            shopDataModel.setShopEmail(shop.get("shopEmail").toString());
                        if (shop.get("shopAddress") != null)
                            shopDataModel.setShopAddress(shop.get("shopAddress").toString());
                        if (shop.get("shopCellNo") != null)
                            shopDataModel.setShopCellNo(shop.get("shopCellNo").toString());
                        if (shop.get("shopLongitude") != null)
                            shopDataModel.setShopLongitude(Double.parseDouble(shop.get("shopLongitude").toString()));
                        if (shop.get("shopLatitude") != null)
                            shopDataModel.setShopLatitude(Double.parseDouble(shop.get("shopLatitude").toString()));
                        if (shop.get("shopName") != null)
                            shopDataModel.setShopName(shop.get("shopName").toString());
                        if (shop.get("shopImageURL") != null)
                            shopDataModel.setShopImageURL(shop.get("shopImageURL").toString());
//                        if (shop.get("productsList") != null)
//                            shopDataModel.setProductsList((List<ProductDataModel>) shop.get("productsList"));

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
//                                    Log.d(TAG, "mapListQueryDocumentSnapshopToListShopDataModel OfferDataModel : " + offer);
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
//                        if (shop.get("offerDataModel") != null)
                        shopDataModel.setOfferDataModel(null);
                        Log.d("shop-> ", shopDataModel.toString());
                        shopDataModels.add(shopDataModel);

                    }
                }
            }
            allShopsList.addAll(shopDataModels);
        }
        Log.d("shops_all", allShopsList.toString());
        return allShopsList;
    }
}
