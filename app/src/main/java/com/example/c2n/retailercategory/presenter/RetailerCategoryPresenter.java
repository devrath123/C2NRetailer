package com.example.c2n.retailercategory.presenter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import com.example.c2n.core.mappers.ShopDataModelToViewMapper;
import com.example.c2n.core.models.CategoryDataModel;
import com.example.c2n.core.models.ProductDataModel;
import com.example.c2n.core.models.ShopDataModel;
import com.example.c2n.retailercategory.domain.RetailerAllCategoryUseCase;
import com.example.c2n.retailercategory.domain.RetailerCategoryUseCase;
import com.example.c2n.viewshops.domain.ViewShopsUseCase;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by roshan.nimje on 18-06-2018.
 */

public class RetailerCategoryPresenter {

    private static final String TAG = RetailerCategoryPresenter.class.getSimpleName();

    RetailerCategoryUseCase retailerCategoryUseCase;
    RetailerAllCategoryUseCase retailerAllCategoryUseCase;
    RetailerCategoryView retailerCategoryView;
    ViewShopsUseCase viewShopsUseCase;
    List<CategoryDataModel> categoryList;
    ShopDataModelToViewMapper shopDataModelToViewMapper;
    Context context;

    @Inject
    RetailerCategoryPresenter(RetailerCategoryUseCase retailerCategoryUseCase, RetailerAllCategoryUseCase retailerAllCategoryUseCase, ViewShopsUseCase viewShopsUseCase, ShopDataModelToViewMapper shopDataModelToViewMapper) {
        this.retailerCategoryUseCase = retailerCategoryUseCase;
        this.retailerAllCategoryUseCase = retailerAllCategoryUseCase;
        this.viewShopsUseCase = viewShopsUseCase;
        this.shopDataModelToViewMapper = shopDataModelToViewMapper;
    }

    public void bind(RetailerCategoryView retailerCategoryView, Context context) {
        this.retailerCategoryView = retailerCategoryView;
        this.context = context;
    }

    @SuppressLint("RxLeakedSubscription")
    public void getProducts() {
        retailerCategoryUseCase.execute(new String[]{
                retailerCategoryView.getUserID(), retailerCategoryView.getShopID()}, context)
//                .doOnSubscribe(disposable -> homeView.showProgress(true))
                .subscribe(this::handleResponse, throwable -> handleError(throwable));
    }

    @SuppressLint("RxLeakedSubscription")
    public void getCategory() {
        retailerAllCategoryUseCase.execute(null, context)
                .doOnSubscribe(disposable -> retailerCategoryView.showProgress(true))
                .map(categoryDataModels -> new ArrayList(categoryDataModels))
                .subscribe(this::handleCategoryResponse, throwable -> handleError(throwable));
    }

    private void handleCategoryResponse(List<CategoryDataModel> categoryDataModels) {
        retailerCategoryView.showProgress(false);
        retailerCategoryView.loadCategories(categoryDataModels);
        Log.d("RetailerCategoryPrese__", "" + categoryDataModels.size());
    }

    @SuppressLint("RxLeakedSubscription")
    public void getProductCategory() {
        viewShopsUseCase.execute(null, context)
                .doOnSubscribe(disposable -> retailerCategoryView.showProgress(true))
                .subscribe(shopDataModels -> handleProductCategoryResponse(shopDataModelToViewMapper.mapShopDataModelToView(shopDataModels, retailerCategoryView.getShopID())), throwable -> handleError(throwable));
    }

    private void handleProductCategoryResponse(ShopDataModel shopDataModel) {
        retailerCategoryView.showProgress(false);
        retailerCategoryView.loadProductCategory(shopDataModel);
        Log.d(TAG, "handleProductCategoryResponse: " + shopDataModel.toString());
    }

    private void handleError(Throwable throwable) {
        Log.d("RetailerCategoryPresen_", "error : " + throwable.getMessage());

    }

    private void handleResponse(List<ProductDataModel> productDataModels) {
//        retailerCategoryView.showCategory(productDataModels);
        for (ProductDataModel productDataModel : productDataModels) {
            Log.d("RetailerCategoryPresen_", "" + productDataModel.toString());
        }
//        Log.d("RetailerCategoryPresen_", "size : " + productDataModels.size());
    }

    public void getAllPoducts() {

    }
}
