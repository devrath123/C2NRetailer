package com.example.c2n.core.mapper;

import android.util.Log;

import com.example.c2n.core.models.ProductDataModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by vipul.singhal on 21-06-2018.
 */

public class ListDocumentQuerySnapshotToListProductDataModelMapper {
    List<ProductDataModel> productsDataModels = new ArrayList<>();
    DocumentQuerySnapshotToOfferDataModelMapper documentQuerySnapshotToOfferDataModelMapper;

    @Inject
    ListDocumentQuerySnapshotToListProductDataModelMapper(DocumentQuerySnapshotToOfferDataModelMapper documentQuerySnapshotToOfferDataModelMapper) {
        this.documentQuerySnapshotToOfferDataModelMapper = documentQuerySnapshotToOfferDataModelMapper;
    }

    public List<ProductDataModel> mapQueryDocumentSnapshotToProductDataModel(List<HashMap<String, Object>> queryproductsHashmaps) {
        if (productsDataModels.size() != 0)
            productsDataModels.clear();
        for (HashMap<String, Object> productsHashmap : queryproductsHashmaps) {
            ProductDataModel productDataModel = new ProductDataModel();
            if (productsHashmap.get("productID") != null)
                productDataModel.setProductID(productsHashmap.get("productID").toString());
            if (productsHashmap.get("productCategory") != null)
                productDataModel.setProductCategory(productsHashmap.get("productCategory").toString());
            if (productsHashmap.get("productImageURL") != null)
                productDataModel.setProductImageURL(productsHashmap.get("productImageURL").toString());
            if (productsHashmap.get("productMRP") != null)
                productDataModel.setProductMRP(Double.parseDouble(productsHashmap.get("productMRP").toString()));
            if (productsHashmap.get("productDescription") != null)
                productDataModel.setProductDescription(productsHashmap.get("productDescription").toString());
            if (productsHashmap.get("productName") != null)
                productDataModel.setProductName(productsHashmap.get("productName").toString());
            if (productsHashmap.get("productOfferStatus") != null)
                productDataModel.setProductOfferStatus(Boolean.parseBoolean(productsHashmap.get("productOfferStatus").toString()));
            if (productsHashmap.get("productStockStatus") != null)
                productDataModel.setProductStockStatus(Boolean.parseBoolean(productsHashmap.get("productStockStatus").toString()));
            if (productsHashmap.get("productOffer") != null)
                productDataModel.setProductOffer(documentQuerySnapshotToOfferDataModelMapper.mapOfferHashmapToOfferDataModelMapper((HashMap) productsHashmap.get("productOffer")));
            else
                productDataModel.setProductOffer(null);
//                productDataModel.setProductOffer(documentQuerySnapshotToOfferDataModelMapper.mapDocumentQuerySnapshotToOfferDataModelMapper(productsHashmap));
//            if (productsHashmap.get("shopDataModels") != null)
//                productDataModel.setShopDataModels((List<ShopDataModel>) productsHashmap.get("shopDataModels"));

            Log.d("product_Details", productDataModel.toString());
            productsDataModels.add(productDataModel);

//            if (productsHashmap.get("productScheme") != null && (productsHashmap.get("productDescription") != null))
//                productsDataModels.add(new ProductDataModel(productsHashmap.get("productOfferID").toString(), productsHashmap.getId(), productsHashmap.get("productCategory").toString(), productsHashmap.get("productName").toString(), productsHashmap.get("productPhotoUrl").toString(), productsHashmap.get("productMRP").toString(), productsHashmap.get("productScheme").toString(), productsHashmap.get("shopEmail").toString(), productsHashmap.get("productDescription").toString()));
//            else if (productsHashmap.get("productScheme") == null)
//                productsDataModels.add(new ProductDataModel(productsHashmap.get("productOfferID").toString(), productsHashmap.getId(), productsHashmap.get("productCategory").toString(), productsHashmap.get("productName").toString(), productsHashmap.get("productPhotoUrl").toString(), productsHashmap.get("productMRP").toString(), "", productsHashmap.get("shopEmail").toString(), productsHashmap.get("productDescription").toString()));
//            else if ((productsHashmap.get("productDescription") == null))
//                productsDataModels.add(new ProductDataModel(productsHashmap.get("productOfferID").toString(), productsHashmap.getId(), productsHashmap.get("productCategory").toString(), productsHashmap.get("productName").toString(), productsHashmap.get("productPhotoUrl").toString(), productsHashmap.get("productMRP").toString(), productsHashmap.get("productScheme").toString(), productsHashmap.get("shopEmail").toString(), ""));
//            else
//                productsDataModels.add(new ProductDataModel(productsHashmap.get("productOfferID").toString(), productsHashmap.getId(), productsHashmap.get("productCategory").toString(), productsHashmap.get("productName").toString(), productsHashmap.get("productPhotoUrl").toString(), productsHashmap.get("productMRP").toString(), "", productsHashmap.get("shopEmail").toString(), ""));
        }
        return productsDataModels;
    }
}
