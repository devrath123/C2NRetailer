package com.example.c2n.core.mappers;

import com.example.c2n.core.models.ProductDataModel;
import com.example.c2n.core.models.ProductEntity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class ProductEntityToDataModelMapper {

    private OfferEntityToDataModelMapper offerEntityToDataModelMapper;

    @Inject
    ProductEntityToDataModelMapper(OfferEntityToDataModelMapper offerEntityToDataModelMapper) {
        this.offerEntityToDataModelMapper = offerEntityToDataModelMapper;
    }

    List<ProductDataModel> mapEntityToData(List<ProductEntity> productEntities) {
        List<ProductDataModel> productDataModels = new ArrayList<>();
        for (ProductEntity productEntity : productEntities) {
            ProductDataModel productDataModel = new ProductDataModel();

            productDataModel.setProductID(productEntity.getProductID());
            productDataModel.setProductName(productEntity.getProductName());
            productDataModel.setProductImageURL(productEntity.getProductImageURL());
            productDataModel.setProductMRP((double) (productEntity.getProductMRP()));
            productDataModel.setProductCategory(productEntity.getProductCategory());
            productDataModel.setProductDescription(productEntity.getProductDescription());
            productDataModel.setProductOfferStatus(productEntity.getProductOfferStatus());
            productDataModel.setProductStockStatus(productEntity.getProductStockStatus());
            if (productEntity.getProductOffer() != null) {
                productDataModel.setProductOffer(offerEntityToDataModelMapper.mapEntityToData(productEntity.getProductOffer()));
            } else {
                productDataModel.setProductOffer(null);
            }
            productDataModel.setShopDataModels(null);
            productDataModels.add(productDataModel);
        }


        return productDataModels;

    }
}


