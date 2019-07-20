package com.example.c2n.core.mapper;

import com.example.c2n.core.models.OfferDataModel;

import java.util.Date;
import java.util.HashMap;

import javax.inject.Inject;

/**
 * Created by vipul.singhal on 21-06-2018.
 */

public class DocumentQuerySnapshotToOfferDataModelMapper {


    @Inject
    DocumentQuerySnapshotToOfferDataModelMapper() {
    }

    public OfferDataModel mapOfferHashmapToOfferDataModelMapper(HashMap<String,Object> offerHashmap) {
//        if (productsDataModels.size() != 0)
//            productsDataModels.clear();

        OfferDataModel offerDataModel = new OfferDataModel();
//        Log.d("DocumentQuerySnapshot_", String.valueOf(offerHashmap.get("offerID")) + " - " + offerHashmap.get("offerID").toString());
        if (offerHashmap.get("offerID") != null)
            offerDataModel.setOfferID(offerHashmap.get("offerID").toString());
        if (offerHashmap.get("offerDiscount") != null)
            offerDataModel.setOfferDiscount(Integer.parseInt(offerHashmap.get("offerDiscount").toString()));
        if (offerHashmap.get("mon") != null)
            offerDataModel.setMon((boolean) offerHashmap.get("mon"));
        if (offerHashmap.get("tue") != null)
            offerDataModel.setTue((boolean) offerHashmap.get("tue"));
        if (offerHashmap.get("wed") != null)
            offerDataModel.setWed((boolean) offerHashmap.get("wed"));
        if (offerHashmap.get("thu") != null)
            offerDataModel.setThu((boolean) offerHashmap.get("thu"));
        if (offerHashmap.get("fri") != null)
            offerDataModel.setFri((boolean) offerHashmap.get("fri"));
        if (offerHashmap.get("sat") != null)
            offerDataModel.setSat((boolean) offerHashmap.get("sat"));
        if (offerHashmap.get("sun") != null)
            offerDataModel.setSun((boolean) offerHashmap.get("sun"));
        if (offerHashmap.get("offerStartDate") != null)
            offerDataModel.setOfferStartDate((Date) offerHashmap.get("offerStartDate"));
        if (offerHashmap.get("offerEndDate") != null)
            offerDataModel.setOfferEndDate((Date) offerHashmap.get("offerEndDate"));
        if (offerHashmap.get("offerName") != null)
            offerDataModel.setOfferName(offerHashmap.get("offerName").toString());
        if (offerHashmap.get("offerStatus") != null)
            offerDataModel.setOfferStatus((boolean) offerHashmap.get("offerStatus"));
//        if (offerHashmap.getBoolean("productStockStatus") != null)
        offerDataModel.setShopDataModels(null);

//            if (offerHashmap.get("productScheme") != null && (offerHashmap.get("productDescription") != null))
//                productsDataModels.add(new ProductDataModel(offerHashmap.get("productOfferID").toString(), offerHashmap.getId(), offerHashmap.get("productCategory").toString(), offerHashmap.get("productName").toString(), offerHashmap.get("productPhotoUrl").toString(), offerHashmap.get("productMRP").toString(), offerHashmap.get("productScheme").toString(), offerHashmap.get("shopEmail").toString(), offerHashmap.get("productDescription").toString()));
//            else if (offerHashmap.get("productScheme") == null)
//                productsDataModels.add(new ProductDataModel(offerHashmap.get("productOfferID").toString(), offerHashmap.getId(), offerHashmap.get("productCategory").toString(), offerHashmap.get("productName").toString(), offerHashmap.get("productPhotoUrl").toString(), offerHashmap.get("productMRP").toString(), "", offerHashmap.get("shopEmail").toString(), offerHashmap.get("productDescription").toString()));
//            else if ((offerHashmap.get("productDescription") == null))
//                productsDataModels.add(new ProductDataModel(offerHashmap.get("productOfferID").toString(), offerHashmap.getId(), offerHashmap.get("productCategory").toString(), offerHashmap.get("productName").toString(), offerHashmap.get("productPhotoUrl").toString(), offerHashmap.get("productMRP").toString(), offerHashmap.get("productScheme").toString(), offerHashmap.get("shopEmail").toString(), ""));
//            else
//                productsDataModels.add(new ProductDataModel(offerHashmap.get("productOfferID").toString(), offerHashmap.getId(), offerHashmap.get("productCategory").toString(), offerHashmap.get("productName").toString(), offerHashmap.get("productPhotoUrl").toString(), offerHashmap.get("productMRP").toString(), "", offerHashmap.get("shopEmail").toString(), ""));

        return offerDataModel;
    }
}
