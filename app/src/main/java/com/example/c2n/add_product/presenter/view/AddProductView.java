package com.example.c2n.add_product.presenter.view;

import com.example.c2n.core.model1.CategoryDataModel;

import java.util.List;

/**
 * Created by vipul.singhal on 28-05-2018.
 */

public interface AddProductView {
    void postCategoryList(List<CategoryDataModel> categoriesList, String error);
}
