package com.example.c2n.customer_single_shop_products.presenter;

import com.example.c2n.core.models.CategoryDataModel;
import com.example.c2n.core.models.MasterProductDataModel;
import com.example.c2n.core.models.ProductDataModel;
import com.example.c2n.core.models.ProductDetailsDataModel;

import java.util.List;

public interface CustomerSingleShopProductsView {
    String getUserID();

    String getShopID();

    void loadAllProducts(List<ProductDataModel> productDataModels);

    void loadCategories(List<CategoryDataModel> categoryDataModels);

    void getMylist();

    void loadMylist(List<MasterProductDataModel> productIDs);

    void showProgress(boolean flag, String msg);
}
