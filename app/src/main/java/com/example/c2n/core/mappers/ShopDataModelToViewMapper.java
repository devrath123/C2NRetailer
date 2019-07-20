package com.example.c2n.core.mappers;

import com.example.c2n.core.models.ShopDataModel;

import java.util.List;

import javax.inject.Inject;

public class ShopDataModelToViewMapper {

    @Inject
    public ShopDataModelToViewMapper() {
    }

    public ShopDataModel mapShopDataModelToView(List<ShopDataModel> shopDataModelList, String shopID) {
        ShopDataModel shopDataModel1 = null;
        for (ShopDataModel shopDataModel : shopDataModelList) {
            if (shopDataModel.getShopID().equals(shopID)) {
                shopDataModel1 = shopDataModel;
            }
        }
        return shopDataModel1;
    }
}
