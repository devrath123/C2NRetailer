package com.example.c2n.core.mapper;

import android.util.Log;

import com.example.c2n.core.model1.OfferDataModel;
import com.example.c2n.core.model1.ShopOfferDataModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by vipul.singhal on 21-06-2018.
 */

public class DocumentQuerySnapshotToShopOfferDataModelMapper {

    ShopOfferDataModel shopOfferDataModel = new ShopOfferDataModel();
    List<HashMap<String, List<String>>> hashMaps = new ArrayList<>();

    @Inject
    DocumentQuerySnapshotToShopOfferDataModelMapper() {
    }

    public ShopOfferDataModel mapDocumentQuerySnapshotToOfferDataModelMapper(ArrayList documentSnapshot) {

        if (hashMaps.size() != 0)
            hashMaps.clear();
        for (int i = 0; i < documentSnapshot.size(); i++) {
            HashMap<String, List<String>> o = (HashMap<String, List<String>>) documentSnapshot.get(i);
            hashMaps.add(i, o);
        }
        shopOfferDataModel.setProductsId(hashMaps);
        Log.d("DocumentQuerySnapshot", "----" + shopOfferDataModel.toString());
        return shopOfferDataModel;

//        ShopOfferDataModel shopOfferDataModel = new ShopOfferDataModel();
//        if (documentSnapshot.get("") != null)
//            if (documentSnapshot.get("offerID") != null)
//                offerDataModel.setOfferID(documentSnapshot.get("offerID").toString());
//        if (documentSnapshot.get("offerDiscount") != null)
//            offerDataModel.setOfferDiscount(Integer.parseInt(documentSnapshot.get("offerDiscount").toString()));
//        if (documentSnapshot.get("mon") != null)
//            offerDataModel.setMon((boolean) documentSnapshot.get("mon"));
//        if (documentSnapshot.get("tue") != null)
//            offerDataModel.setTue((boolean) documentSnapshot.get("tue"));
//        if (documentSnapshot.get("wed") != null)
//            offerDataModel.setWed((boolean) documentSnapshot.get("wed"));
//        if (documentSnapshot.get("thu") != null)
//            offerDataModel.setThu((boolean) documentSnapshot.get("thu"));
//        if (documentSnapshot.get("fri") != null)
//            offerDataModel.setFri((boolean) documentSnapshot.get("fri"));
//        if (documentSnapshot.get("sat") != null)
//            offerDataModel.setSat((boolean) documentSnapshot.get("sat"));
//        if (documentSnapshot.get("sun") != null)
//            offerDataModel.setSun((boolean) documentSnapshot.get("sun"));
//        if (documentSnapshot.get("offerStartDate") != null)
//            offerDataModel.setOfferStartDate((Date) documentSnapshot.get("offerStartDate"));
//        if (documentSnapshot.get("offerEndDate") != null)
//            offerDataModel.setOfferEndDate((Date) documentSnapshot.get("offerEndDate"));
//        if (documentSnapshot.get("offerName") != null)
//            offerDataModel.setOfferName(documentSnapshot.get("offerName").toString());
//        if (documentSnapshot.get("offerStatus") != null)
//            offerDataModel.setOfferStatus((boolean) documentSnapshot.get("offerStatus"));
////        if (documentSnapshot.getBoolean("productStockStatus") != null)
//        offerDataModel.setOfferID(null);

//            if (documentSnapshot.get("productScheme") != null && (documentSnapshot.get("productDescription") != null))
//                productsDataModels.add(new ProductDataModel(documentSnapshot.get("productOfferID").toString(), documentSnapshot.getId(), documentSnapshot.get("productCategory").toString(), documentSnapshot.get("productName").toString(), documentSnapshot.get("productPhotoUrl").toString(), documentSnapshot.get("productMRP").toString(), documentSnapshot.get("productScheme").toString(), documentSnapshot.get("shopEmail").toString(), documentSnapshot.get("productDescription").toString()));
//            else if (documentSnapshot.get("productScheme") == null)
//                productsDataModels.add(new ProductDataModel(documentSnapshot.get("productOfferID").toString(), documentSnapshot.getId(), documentSnapshot.get("productCategory").toString(), documentSnapshot.get("productName").toString(), documentSnapshot.get("productPhotoUrl").toString(), documentSnapshot.get("productMRP").toString(), "", documentSnapshot.get("shopEmail").toString(), documentSnapshot.get("productDescription").toString()));
//            else if ((documentSnapshot.get("productDescription") == null))
//                productsDataModels.add(new ProductDataModel(documentSnapshot.get("productOfferID").toString(), documentSnapshot.getId(), documentSnapshot.get("productCategory").toString(), documentSnapshot.get("productName").toString(), documentSnapshot.get("productPhotoUrl").toString(), documentSnapshot.get("productMRP").toString(), documentSnapshot.get("productScheme").toString(), documentSnapshot.get("shopEmail").toString(), ""));
//            else
//                productsDataModels.add(new ProductDataModel(documentSnapshot.get("productOfferID").toString(), documentSnapshot.getId(), documentSnapshot.get("productCategory").toString(), documentSnapshot.get("productName").toString(), documentSnapshot.get("productPhotoUrl").toString(), documentSnapshot.get("productMRP").toString(), "", documentSnapshot.get("shopEmail").toString(), ""));

//        return new ShopOfferDataModel();
    }
}
