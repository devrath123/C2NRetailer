package com.example.c2n.core.mapper;

import android.util.Log;

import com.example.c2n.core.mappers.ListShopsHashmapToShopDataModelListMapper;
import com.example.c2n.core.models.ProductDataModel;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by vipul.singhal on 21-06-2018.
 */

public class DocumentQuerySnapshotToProductDataModelMapper {

    DocumentQuerySnapshotToOfferDataModelMapper documentQuerySnapshotToOfferDataModelMapper;
    ListShopsHashmapToShopDataModelListMapper listShopsHashmapToShopDataModelListMapper;

    @Inject
    DocumentQuerySnapshotToProductDataModelMapper(DocumentQuerySnapshotToOfferDataModelMapper documentQuerySnapshotToOfferDataModelMapper, ListShopsHashmapToShopDataModelListMapper listShopsHashmapToShopDataModelListMapper) {
        this.documentQuerySnapshotToOfferDataModelMapper = documentQuerySnapshotToOfferDataModelMapper;
        this.listShopsHashmapToShopDataModelListMapper = listShopsHashmapToShopDataModelListMapper;
    }

    public ProductDataModel mapProductHashmapToProductDataModel(HashMap<String, Object> productHashmap) {
        ProductDataModel productDataModel = new ProductDataModel();
        if (productHashmap.get("productID") != null)
            productDataModel.setProductID(productHashmap.get("productID").toString());
        if (productHashmap.get("productCategory") != null)
            productDataModel.setProductCategory(productHashmap.get("productCategory").toString());
        if (productHashmap.get("productImageURL") != null)
            productDataModel.setProductImageURL(productHashmap.get("productImageURL").toString());
        if (productHashmap.get("productMRP") != null)
            productDataModel.setProductMRP(Double.parseDouble(productHashmap.get("productMRP").toString()));
        if (productHashmap.get("productDescription") != null)
            productDataModel.setProductDescription(productHashmap.get("productDescription").toString());
        if (productHashmap.get("productName") != null)
            productDataModel.setProductName(productHashmap.get("productName").toString());
        if (productHashmap.get("productOfferStatus") != null)
            productDataModel.setProductOfferStatus(Boolean.parseBoolean(productHashmap.get("productOfferStatus").toString()));
        if (productHashmap.get("productStockStatus") != null)
            productDataModel.setProductStockStatus(Boolean.parseBoolean(productHashmap.get("productStockStatus").toString()));
        if (productHashmap.get("productOffer") != null)
//            productDataModel.setProductOffer(documentQuerySnapshotToOfferDataModelMapper.mapDocumentQuerySnapshotToOfferDataModelMapper((HashMap) productHashmap.get("productOffer")));
//        else
//            productDataModel.setProductOffer(null);
            productDataModel.setProductOffer(documentQuerySnapshotToOfferDataModelMapper.mapOfferHashmapToOfferDataModelMapper((HashMap<String, Object>) productHashmap.get("productOffer")));
        if (productHashmap.get("shopDataModels") != null)
            productDataModel.setShopDataModels(listShopsHashmapToShopDataModelListMapper.mapShopsHashmapListToShopsList((List<HashMap<String, Object>>) productHashmap.get("shopDataModels")));
        Log.d("product_Details", productDataModel.toString());

//            if (productHashmap.get("productScheme") != null && (productHashmap.get("productDescription") != null))
//                productsDataModels.add(new ProductDataModel(productHashmap.get("productOfferID").toString(), productHashmap.getId(), productHashmap.get("productCategory").toString(), productHashmap.get("productName").toString(), productHashmap.get("productPhotoUrl").toString(), productHashmap.get("productMRP").toString(), productHashmap.get("productScheme").toString(), productHashmap.get("shopEmail").toString(), productHashmap.get("productDescription").toString()));
//            else if (productHashmap.get("productScheme") == null)
//                productsDataModels.add(new ProductDataModel(productHashmap.get("productOfferID").toString(), productHashmap.getId(), productHashmap.get("productCategory").toString(), productHashmap.get("productName").toString(), productHashmap.get("productPhotoUrl").toString(), productHashmap.get("productMRP").toString(), "", productHashmap.get("shopEmail").toString(), productHashmap.get("productDescription").toString()));
//            else if ((productHashmap.get("productDescription") == null))
//                productsDataModels.add(new ProductDataModel(productHashmap.get("productOfferID").toString(), productHashmap.getId(), productHashmap.get("productCategory").toString(), productHashmap.get("productName").toString(), productHashmap.get("productPhotoUrl").toString(), productHashmap.get("productMRP").toString(), productHashmap.get("productScheme").toString(), productHashmap.get("shopEmail").toString(), ""));
//            else
//                productsDataModels.add(new ProductDataModel(productHashmap.get("productOfferID").toString(), productHashmap.getId(), productHashmap.get("productCategory").toString(), productHashmap.get("productName").toString(), productHashmap.get("productPhotoUrl").toString(), productHashmap.get("productMRP").toString(), "", productHashmap.get("shopEmail").toString(), ""));

        return productDataModel;
    }
}
