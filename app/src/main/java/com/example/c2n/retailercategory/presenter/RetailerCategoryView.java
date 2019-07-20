package com.example.c2n.retailercategory.presenter;

import com.example.c2n.core.models.CategoryDataModel;
import com.example.c2n.core.models.ProductDataModel;
import com.example.c2n.core.models.ShopDataModel;

import java.util.List;

/**
 * Created by roshan.nimje on 18-06-2018.
 */

public interface RetailerCategoryView {
    void loadCategories(List<CategoryDataModel> categoryDataModels);

    void loadProductCategory(ShopDataModel shopDataModel);

    void showCategory(List<ProductDataModel> productDataModels);

    String getUserID();

    String getShopID();

    void getCategory();

    void showProgress(boolean flag);
}
