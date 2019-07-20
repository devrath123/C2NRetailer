package com.example.c2n.retailer_shop_products_list.presenter;

import com.example.c2n.core.models.ProductDataModel;

import java.util.List;

/**
 * Created by vipul.singhal on 21-06-2018.
 */

public interface RetailerShopProductsView {

    void showProgress(Boolean progress);

    void showProductsList(List<ProductDataModel> products);

    void addProduct();

    String getShopId();

    String getProductId();

    void isProductUnavailabilitySuccess(Boolean bool);

    void isProductAvailabilitySuccess(Boolean bool);

    String getShopID();
}
