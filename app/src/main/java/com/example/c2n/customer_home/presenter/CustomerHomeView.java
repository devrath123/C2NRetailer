package com.example.c2n.customer_home.presenter;

import com.example.c2n.core.models.CategoryDataModel;
import com.example.c2n.core.models.MasterProductDataModel;
import com.example.c2n.core.models.MasterProductDetailsDataModel;

import java.util.List;

public interface CustomerHomeView {
    void getAllMasterProducts();

    void loadMasterProducts(List<MasterProductDetailsDataModel> masterProductDetailsDataModels);

    void getMylist();

    void loadMylist(List<MasterProductDataModel> productIDs);

    void loadAllProducts(List<MasterProductDetailsDataModel> masterProductDetailsDataModels);

    void showProducts();

    void loadCategories(List<CategoryDataModel> categoryDataModels);

    void getCategories();

    void showProgressDialog(String msg);

    void dismissProgressDialog();
}
