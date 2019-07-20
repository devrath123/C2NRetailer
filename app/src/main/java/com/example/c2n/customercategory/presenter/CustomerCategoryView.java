package com.example.c2n.customercategory.presenter;

import com.example.c2n.core.models.CategoryDataModel;

import java.util.List;

/**
 * Created by shivani.singh on 17-08-2018.
 */

public interface CustomerCategoryView {

    void loadCategories(List<CategoryDataModel> categoryDataModels, String error);

    void getCategories();

    void getCategory();

    void showProgress(Boolean flag);


}
