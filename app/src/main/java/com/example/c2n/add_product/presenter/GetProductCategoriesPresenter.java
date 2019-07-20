package com.example.c2n.add_product.presenter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import com.example.c2n.add_product.domain.GetProductCategoriesUseCase;
import com.example.c2n.add_product.presenter.view.AddProductView;
import com.example.c2n.core.model1.CategoryDataModel;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by vipul.singhal on 29-05-2018.
 */

public class GetProductCategoriesPresenter {

    GetProductCategoriesUseCase getProductCategoriesUseCase;
    AddProductView addProductView;
    Context context;

    @Inject
    GetProductCategoriesPresenter(GetProductCategoriesUseCase getProductCategoriesUseCase) {
        this.getProductCategoriesUseCase = getProductCategoriesUseCase;
    }

    public void bind(AddProductView addProductView, Context context) {
        this.addProductView = addProductView;

        this.context = context;
    }

    @SuppressLint("RxLeakedSubscription")
    public void getCategoryList() {
        getProductCategoriesUseCase.execute(null, context)
                .subscribe(this::handleResponse, throwable -> handleError(throwable));
    }


    public void handleResponse(List<CategoryDataModel> categoriesList) {
        Log.d("category_list", categoriesList.toString());
        addProductView.postCategoryList(categoriesList, null);
    }

    public void handleError(Throwable e) {
        Log.d("error...", "..." + e.getMessage());
        addProductView.postCategoryList(null, e.getMessage());
    }
}
