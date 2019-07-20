package com.example.c2n.customer_products_list.presenter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import com.example.c2n.core.model.ProductDataModel;
import com.example.c2n.core.model.ProductDataModelToViewModelMapper;
import com.example.c2n.customer_products_list.domain.ProductsListUseCase;
import com.example.c2n.customer_products_list.presenter.view.ProductsListView;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by vipul.singhal on 23-05-2018.
 */

public class ProductsListPresenter {

    private ProductsListUseCase productsListUseCase;
    private ProductsListView productsListView;
    private ProductDataModelToViewModelMapper productDataModelToViewModelMapper;
    private Context context;

    @Inject
    public ProductsListPresenter(ProductsListUseCase productsListUseCase, ProductDataModelToViewModelMapper productDataModelToViewModelMapper) {
        this.productsListUseCase = productsListUseCase;
        this.productDataModelToViewModelMapper = productDataModelToViewModelMapper;
    }

    public void bind(ProductsListView productsListView, Context context) {
        this.productsListView = productsListView;
        this.context = context;
    }

    @SuppressLint("RxLeakedSubscription")
    public void getProducts() {
        productsListUseCase.execute(null, context)
                .doOnSubscribe(disposable -> productsListView.showProgress(true))
                .subscribe(this::handleResponse, throwable -> handleError(throwable));
    }

    public void handleResponse(List<ProductDataModel> productsList) {
        Log.d("products_list", productsList.toString());
        productsListView.postProductsList(productDataModelToViewModelMapper.mapProductsToViewModelMapper(productsList, productsListView.getSelectedProductCategory()), null);
    }

    public void handleError(Throwable e) {
        Log.d("error...", "..." + e.getMessage());
        productsListView.postProductsList(null, e.getMessage());
    }

}
