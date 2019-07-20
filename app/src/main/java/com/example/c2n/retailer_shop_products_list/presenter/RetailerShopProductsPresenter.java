package com.example.c2n.retailer_shop_products_list.presenter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import com.example.c2n.core.mappers.ShopDataModelToViewMapper;
import com.example.c2n.core.models.ShopDataModel;
import com.example.c2n.retailer_shop_products_list.domain.RetailerShopProductsUseCase;
import com.example.c2n.retailer_shop_products_list.domain.UpdateProductAvailableUseCase;
import com.example.c2n.retailer_shop_products_list.domain.UpdateProductUnavailableUseCase;
import com.example.c2n.viewshops.domain.ViewShopsUseCase;
import com.google.firebase.firestore.DocumentReference;

import javax.inject.Inject;

/**
 * Created by vipul.singhal on 21-06-2018.
 */

public class RetailerShopProductsPresenter {

    private static final String TAG = RetailerShopProductsPresenter.class.getSimpleName();

    UpdateProductUnavailableUseCase updateProductUnavailableUseCase;
    UpdateProductAvailableUseCase updateProductAvailableUseCase;
    RetailerShopProductsUseCase retailerShopProductsUseCase;
    RetailerShopProductsView retailerShopProductsView;
    ViewShopsUseCase viewShopsUseCase;
    ShopDataModelToViewMapper shopDataModelToViewMapper;

    Context context;

    @Inject
    RetailerShopProductsPresenter(RetailerShopProductsUseCase retailerShopProductsUseCase, UpdateProductAvailableUseCase updateProductAvailableUseCase, UpdateProductUnavailableUseCase updateProductUnavailableUseCase, ViewShopsUseCase viewShopsUseCase, ShopDataModelToViewMapper shopDataModelToViewMapper) {
        this.retailerShopProductsUseCase = retailerShopProductsUseCase;
        this.updateProductAvailableUseCase = updateProductAvailableUseCase;
        this.updateProductUnavailableUseCase = updateProductUnavailableUseCase;
        this.viewShopsUseCase = viewShopsUseCase;
        this.shopDataModelToViewMapper = shopDataModelToViewMapper;
    }

    public void bind(RetailerShopProductsView retailerShopProductsView, Context context) {
        this.retailerShopProductsView = retailerShopProductsView;
        this.context = context;
    }



    @SuppressLint("RxLeakedSubscription")
    public void getProductCategory() {
        viewShopsUseCase.execute(null, context)
                .doOnSubscribe(disposable -> retailerShopProductsView.showProgress(true))
                .subscribe(shopDataModels -> handleProductCategoryResponse(shopDataModelToViewMapper.mapShopDataModelToView(shopDataModels, retailerShopProductsView.getShopID())), throwable -> handleError(throwable));
    }

    private void handleProductCategoryResponse(ShopDataModel shopDataModel) {
        retailerShopProductsView.showProgress(false);
        retailerShopProductsView.showProductsList(shopDataModel.getProductsList());
//        retailerCategoryView.loadProductCategory(shopDataModel);
        Log.d(TAG, "handleProductCategoryResponse: " + shopDataModel.toString());
    }

//    @SuppressLint("RxLeakedSubscription")
//    public void loadProducts(String shopName, String categorySelected) {
//
//        retailerShopProductsUseCase.execute(new String[]{shopName, categorySelected}, context)
//                .doOnSubscribe(disposable -> {
//                    retailerShopProductsView.showProgress(true);
//                })
//                .subscribe(this::handleResponse, throwable -> handleError(throwable));
//    }


    @SuppressLint("RxLeakedSubscription")
    public void markProductUnavailable() {

        updateProductUnavailableUseCase.execute(new String[]{retailerShopProductsView.getShopId(), retailerShopProductsView.getProductId()}, context)
                .doOnSubscribe(disposable -> {
                    retailerShopProductsView.showProgress(true);
                })
                .subscribe(this::handleResponse, throwable -> handleError(throwable));
    }

    private void handleResponse(DocumentReference documentReference) {
        retailerShopProductsView.showProgress(false);
        Log.d("product_unavailable", "success");
        retailerShopProductsView.isProductUnavailabilitySuccess(true);
//        if (products != null || products.size() != 0) {
//            retailerShopProductsView.showProgress(false);
//            retailerShopProductsView.showProductsList(products);
//            return;
//        }
    }

    private void handleError(Throwable throwable) {
        retailerShopProductsView.showProgress(false);
        retailerShopProductsView.isProductUnavailabilitySuccess(false);
        Log.d("ViewShopsPresenter", "handleError : " + throwable.getMessage());
    }


    @SuppressLint("RxLeakedSubscription")
    public void markProductAvailable() {

        updateProductAvailableUseCase.execute(new String[]{retailerShopProductsView.getShopId(), retailerShopProductsView.getProductId()}, context)
                .doOnSubscribe(disposable -> {
                    retailerShopProductsView.showProgress(true);
                })
                .subscribe(this::handleAvailableResponse, throwable -> handleAvailableError(throwable));
    }

    private void handleAvailableResponse(DocumentReference documentReference) {
        retailerShopProductsView.showProgress(false);
        Log.d("product_unavailable", "success");
        retailerShopProductsView.isProductAvailabilitySuccess(true);
//        if (products != null || products.size() != 0) {
//            retailerShopProductsView.showProgress(false);
//            retailerShopProductsView.showProductsList(products);
//            return;
//        }
    }

    private void handleAvailableError(Throwable throwable) {
        retailerShopProductsView.showProgress(false);
        retailerShopProductsView.isProductAvailabilitySuccess(false);
        Log.d("ViewShopsPresenter", "handleError : " + throwable.getMessage());
    }

}
