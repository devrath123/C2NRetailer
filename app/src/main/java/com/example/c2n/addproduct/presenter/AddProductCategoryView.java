package com.example.c2n.addproduct.presenter;

import com.example.c2n.core.models.CategoryDataModel;

import java.util.List;

public interface AddProductCategoryView {
    void getAllCategory();

    void showLoadingOffersProgress(Boolean progress, String msg);

    void loadAallCategory(List<CategoryDataModel> categoryDataModelList);
}
