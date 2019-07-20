package com.example.c2n.customer_single_product.presenter;

import com.example.c2n.core.models.MasterProductDataModel;
import com.example.c2n.core.models.ProductDataModel;

import java.util.List;

public interface CustomerSingleProductView {
    void loadProductDetails(ProductDataModel productDataModel);

    String getProductID();

    void showProgress(boolean flag, String msg);

    void getMylist();

    void loadMylist(List<MasterProductDataModel> productIDs);
}
