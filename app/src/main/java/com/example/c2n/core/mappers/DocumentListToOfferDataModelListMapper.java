package com.example.c2n.core.mappers;

import android.util.Log;

import com.example.c2n.core.mapper.DocumentQuerySnapshotToShopOfferDataModelMapper;
import com.example.c2n.core.models.OfferDataModel;
import com.example.c2n.core.models.ProductDataModel;
import com.example.c2n.core.models.ShopDataModel;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by shivani.singh on 19-09-2018.
 */

public class DocumentListToOfferDataModelListMapper {

    private final String TAG = "DocumentListToOfferData";

    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

    DocumentQuerySnapshotToShopOfferDataModelMapper documentQuerySnapshotToShopOfferDataModelMapper;

    @Inject
    DocumentListToOfferDataModelListMapper(DocumentQuerySnapshotToShopOfferDataModelMapper documentQuerySnapshotToShopOfferDataModelMapper) {
        this.documentQuerySnapshotToShopOfferDataModelMapper = documentQuerySnapshotToShopOfferDataModelMapper;
    }

    public List<OfferDataModel> mapDocumentListToOfferCardsList(DocumentSnapshot documentSnapshot) {
        List<OfferDataModel> offerCardsList = new ArrayList<>();
//        if (documentSnapshot != null) {
//            if (documentSnapshot.get("offersList") != null) {
//                ArrayList arrayList = (ArrayList) documentSnapshot.get("offersList");
//                for (int i = 0; i < arrayList.size(); i++) {
//                    HashMap offerCard = (HashMap) arrayList.get(i);
//                    OfferDataModel offerDataModel = new OfferDataModel();
//                    if (offerCard.get("offerID") != null) {
//                        Log.d("DocumentListToOfferDa__", offerCard.get("offerID").toString());
//                        offerDataModel.setOfferID(offerCard.get("offerID").toString());
//                    }
//                    if (offerCard.get("offerDiscount") != null)
//                        offerDataModel.setOfferDiscount(Integer.parseInt(offerCard.get("offerDiscount").toString()));
//                    if (offerCard.get("offerName") != null)
//                        offerDataModel.setOfferName(offerCard.get("offerName").toString());
//                    if (offerCard.get("offerStartDate") != null)
//                        offerDataModel.setOfferStartDate((Date) offerCard.get("offerStartDate"));
//                    if (offerCard.get("offerEndDate") != null)
//                        offerDataModel.setOfferEndDate((Date) offerCard.get("offerEndDate"));
//                    if (offerCard.get("offerStatus") != null)
//                        offerDataModel.setOfferStatus(Boolean.parseBoolean(offerCard.get("offerStatus").toString()));
//                    if (offerCard.get("mon") != null)
//                        offerDataModel.setMon(Boolean.parseBoolean(offerCard.get("mon").toString()));
//                    if (offerCard.get("tue") != null)
//                        offerDataModel.setTue(Boolean.parseBoolean(offerCard.get("tue").toString()));
//                    if (offerCard.get("wed") != null)
//                        offerDataModel.setWed(Boolean.parseBoolean(offerCard.get("wed").toString()));
//                    if (offerCard.get("thu") != null)
//                        offerDataModel.setThu(Boolean.parseBoolean(offerCard.get("thu").toString()));
//                    if (offerCard.get("fri") != null)
//                        offerDataModel.setFri(Boolean.parseBoolean(offerCard.get("fri").toString()));
//                    if (offerCard.get("sat") != null)
//                        offerDataModel.setSat(Boolean.parseBoolean(offerCard.get("sat").toString()));
//                    if (offerCard.get("sun") != null)
//                        offerDataModel.setSun(Boolean.parseBoolean(offerCard.get("sun").toString()));
//                    if (offerCard.get("shopDataModels") != null)
//                        offerDataModel.setShopDataModels((List<ShopDataModel>) offerCard.get("shopDataModels"));
////            offerDataModel.setOfferProducts((ArrayList) offerCard.get("offerProducts"));
////            List<HashMap<String, List<String>>> hashMaps = new ArrayList<>();
////            ArrayList arrayList = (ArrayList) offerCard.get("offerProducts");
////            for (int i = 0; i < arrayList.size(); i++) {
////                HashMap<String, List<String>> o = (HashMap<String, List<String>>) arrayList.get(i);
////                hashMaps.add(i, o);
////            }
////            offerDataModel.setOfferProducts(hashMaps);
//
////            offerCard.get("offerProducts");
////            if (offerCard.get("offerProducts") != null)
////            offerDataModel.setShopOfferDataModel(documentQuerySnapshotToShopOfferDataModelMapper.mapDocumentQuerySnapshotToOfferDataModelMapper((ArrayList) offerCard.get("offerProducts")));
////            offerDataModel.setOfferProducts((String[]) offerCard.getData().get("productID"));
////            Object list = offerCard.getData().get("offerProducts");
////            offerDataModel.setOfferProducts(offerCard.getData().get("offerProducts"));
//                    Log.d("offer_data_model", offerDataModel.toString());
//                    offerCardsList.add(offerDataModel);
//                }
//            }
//        }
//        Log.d("offer_cardlist", offerCardsList.toString());
//----------------------------------------------------------------------------------
        if (documentSnapshot != null) {
            List<HashMap<String, Object>> offers = (List<HashMap<String, Object>>) documentSnapshot.get("offersList");
            if (offers != null) {
                for (int i = 0; i < offers.size(); i++) {
                    HashMap<String, Object> offer = offers.get(i);
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

                    if (offer.get("shopDataModels") != null) {
                        List<ShopDataModel> shopDataModels = new ArrayList<>();
                        List<HashMap<String, Object>> shops = (List<HashMap<String, Object>>) offer.get("shopDataModels");
                        for (int j = 0; j < shops.size(); j++) {
                            HashMap<String, Object> shop = shops.get(j);
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
                                List<ProductDataModel> productDataModels = new ArrayList<>();
                                List<HashMap<String, Object>> products = (List<HashMap<String, Object>>) shop.get("productsList");
                                for (int k = 0; k < products.size(); k++) {
                                    HashMap<String, Object> product = products.get(k);
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
                                    productDataModel.setProductOffer(null);
                                    productDataModels.add(productDataModel);
                                }
                                shopDataModel.setProductsList(productDataModels);
                            } else {
                                shopDataModel.setProductsList(null);
                            }
                            shopDataModels.add(shopDataModel);
                        }
                        offerDataModel.setShopDataModels(shopDataModels);
                    } else {
                        offerDataModel.setShopDataModels(null);
                    }
                    offerCardsList.add(offerDataModel);
                }
            }
        }
        Log.d(TAG, "OfferDataModels : " + offerCardsList);
        return offerCardsList;
    }
}
