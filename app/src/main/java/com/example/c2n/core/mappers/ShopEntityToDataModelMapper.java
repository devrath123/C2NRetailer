package com.example.c2n.core.mappers;

import com.example.c2n.core.models.ShopDataModel;
import com.example.c2n.core.models.ShopEntity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class ShopEntityToDataModelMapper {

    private ProductEntityToDataModelMapper productEntityToDataModelMapper;

    @Inject
    ShopEntityToDataModelMapper(ProductEntityToDataModelMapper productEntityToDataModelMapper) {
        this.productEntityToDataModelMapper = productEntityToDataModelMapper;
    }

    public List<ShopDataModel> mapEntityToData(List<ShopEntity> shopEntityList) {
        List<ShopDataModel> shopDataModels = new ArrayList<>();
        for (ShopEntity shopEntity : shopEntityList) {
            ShopDataModel shopDataModel = new ShopDataModel();
            shopDataModel.setShopID(shopEntity.getShopID());
            shopDataModel.setShopAddress(shopEntity.getShopAddress());
            shopDataModel.setRetailerID(shopEntity.getRetailerID());
            shopDataModel.setShopCellNo(shopEntity.getShopCellNo());
            shopDataModel.setShopEmail(shopEntity.getShopEmail());
            shopDataModel.setShopImageURL(shopEntity.getShopImageURL());
            shopDataModel.setShopLatitude(shopEntity.getShopLatitude());
            shopDataModel.setShopLongitude(shopEntity.getShopLongitude());
            shopDataModel.setShopName(shopEntity.getShopName());
            if (shopEntity.getProductsList() != null) {
                shopDataModel.setProductsList(productEntityToDataModelMapper.mapEntityToData(shopEntity.getProductsList()));
            } else {
                shopDataModel.setProductsList(null);
            }
            shopDataModel.setOfferDataModel(null);
            shopDataModels.add(shopDataModel);
        }
        return shopDataModels;
    }
}
