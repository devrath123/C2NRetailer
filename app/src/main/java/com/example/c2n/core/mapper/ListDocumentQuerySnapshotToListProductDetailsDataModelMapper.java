package com.example.c2n.core.mapper;

import android.util.Log;

import com.example.c2n.core.models.ProductDetailsDataModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by vipul.singhal on 21-06-2018.
 */

public class ListDocumentQuerySnapshotToListProductDetailsDataModelMapper {

    private static final String TAG = ListDocumentQuerySnapshotToListProductDetailsDataModelMapper.class.getSimpleName();
    DocumentQuerySnapshotToProductDataModelMapper documentQuerySnapshotToProductDataModelMapper;

    @Inject
    ListDocumentQuerySnapshotToListProductDetailsDataModelMapper(DocumentQuerySnapshotToProductDataModelMapper documentQuerySnapshotToProductDataModelMapper) {
        this.documentQuerySnapshotToProductDataModelMapper = documentQuerySnapshotToProductDataModelMapper;
    }

    public List<ProductDetailsDataModel> mapListProductsHashmapToListProductDetailsDataModelMapper(List<HashMap<String, Object>> productsListHashmap, String userKey, String shopKey) {
        List<ProductDetailsDataModel> productDetailsDataModels = new ArrayList<>();
        Log.d(TAG, "mapListProductsHashmapToListProductDetailsDataModelMapper: " + productsListHashmap.size());
//        for (int i = 0; i < productsListHashmap.size(); i++) {
//            ProductDetailsQueryDocumentSnapshotDataModel productDetailsQueryDocumentSnapshotDataModel = productDetailsQueryDocumentSnapshotDataModels.get(i);
//            ProductDetailsDataModel productDetailsDataModel = new ProductDetailsDataModel();
//            productDetailsDataModel.setRetailerID(userKey);
//            productDetailsDataModel.setShopID(productDetailsQueryDocumentSnapshotDataModel.getShopID());
//            productDetailsDataModel.setMylisted(false);
//            productDetailsDataModel.setProductDataModel(documentQuerySnapshotToProductDataModelMapper.mapQueryDocumentSnapShotToProductDataModel(productDetailsQueryDocumentSnapshotDataModel.getQueryDocumentSnapshot()));
//            productDetailsDataModels.add(productDetailsDataModel);
//        }

        for (HashMap<String, Object> productHashmap : productsListHashmap) {
//            ProductDetailsQueryDocumentSnapshotDataModel productDetailsQueryDocumentSnapshotDataModel = productDetailsQueryDocumentSnapshotDataModels.get(i);
            ProductDetailsDataModel productDetailsDataModel = new ProductDetailsDataModel();
            productDetailsDataModel.setRetailerID(userKey);
            productDetailsDataModel.setShopID(shopKey);
            productDetailsDataModel.setWhishlisted(false);
            productDetailsDataModel.setProductDataModel(documentQuerySnapshotToProductDataModelMapper.mapProductHashmapToProductDataModel(productHashmap));
            productDetailsDataModels.add(productDetailsDataModel);
        }
        return productDetailsDataModels;
    }
}
