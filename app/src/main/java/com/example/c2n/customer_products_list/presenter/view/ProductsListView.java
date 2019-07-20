package com.example.c2n.customer_products_list.presenter.view;

import com.example.c2n.core.model.ProductDataModel;

import java.util.List;

/**
 * Created by vipul.singhal on 23-05-2018.
 */

public interface ProductsListView {
    void showProgress(Boolean processing);

    void postProductsList(List<ProductDataModel> productsList, String error);

     String getSelectedProductCategory();
}
