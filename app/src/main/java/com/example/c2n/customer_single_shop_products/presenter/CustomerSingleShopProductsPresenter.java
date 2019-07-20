package com.example.c2n.customer_single_shop_products.presenter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import com.example.c2n.core.models.CategoryDataModel;
import com.example.c2n.core.models.MasterProductDataModel;
import com.example.c2n.core.models.ProductDetailsDataModel;
import com.example.c2n.customer_home.domain.CustomerHomeAddToMylistUseCase;
import com.example.c2n.customer_home.domain.CustomerHomeLoadMylistUseCase;
import com.example.c2n.customer_home.domain.CustomerHomeRemoveFromMylistUseCase;
import com.example.c2n.customer_single_shop_products.domain.CustomerSingleShopProductsUseCase;
import com.example.c2n.retailercategory.domain.RetailerAllCategoryUseCase;

import java.util.List;

import javax.inject.Inject;

public class CustomerSingleShopProductsPresenter {

    private static final String TAG = CustomerSingleShopProductsPresenter.class.getSimpleName();
    private Context context;
    private CustomerSingleShopProductsView customerSingleShopProductsView;

    private CustomerSingleShopProductsUseCase customerSingleShopProductsUseCase;
    private RetailerAllCategoryUseCase retailerAllCategoryUseCase;
    private CustomerHomeAddToMylistUseCase customerHomeAddToMylistUseCase;
    private CustomerHomeRemoveFromMylistUseCase customerHomeRemoveFromMylistUseCase;
    private CustomerHomeLoadMylistUseCase customerHomeLoadMylistUseCase;

    @Inject
    CustomerSingleShopProductsPresenter(CustomerSingleShopProductsUseCase customerSingleShopProductsUseCase, RetailerAllCategoryUseCase retailerAllCategoryUseCase, CustomerHomeAddToMylistUseCase customerHomeAddToMylistUseCase, CustomerHomeRemoveFromMylistUseCase customerHomeRemoveFromMylistUseCase, CustomerHomeLoadMylistUseCase customerHomeLoadMylistUseCase) {
        this.customerSingleShopProductsUseCase = customerSingleShopProductsUseCase;
        this.retailerAllCategoryUseCase = retailerAllCategoryUseCase;
        this.customerHomeAddToMylistUseCase = customerHomeAddToMylistUseCase;
        this.customerHomeRemoveFromMylistUseCase = customerHomeRemoveFromMylistUseCase;
        this.customerHomeLoadMylistUseCase = customerHomeLoadMylistUseCase;
    }

    public void bind(CustomerSingleShopProductsView customerSingleShopProductsView, Context context) {
        this.customerSingleShopProductsView = customerSingleShopProductsView;
        this.context = context;
    }

    @SuppressLint("RxLeakedSubscription")
    public void getMylist() {
        customerHomeLoadMylistUseCase.execute(null, context)
                .doOnSubscribe(disposable -> customerSingleShopProductsView.showProgress(true, "Loading..."))
                .subscribe(this::handleMylistResponse, throwable -> handleError(throwable));
    }

    private void handleMylistResponse(List<MasterProductDataModel> products) {
        customerSingleShopProductsView.showProgress(false, null);
        Log.d(TAG, "handleMylistResponse: " + products.toString());
        customerSingleShopProductsView.loadMylist(products);
    }

    @SuppressLint("RxLeakedSubscription")
    public void addToMyList(MasterProductDataModel product) {
        customerHomeAddToMylistUseCase.execute(product, context)
                .doOnSubscribe(disposable -> customerSingleShopProductsView.showProgress(true, "Adding to Mylist..."))
                .subscribe(this::handleAddToMylistResponse, throwable -> handleError(throwable));
    }

    private void handleAddToMylistResponse(Boolean aBoolean) {
        customerSingleShopProductsView.showProgress(false, null);
        Log.d(TAG, "handleAddToMylistResponse: " + aBoolean);
    }

    @SuppressLint("RxLeakedSubscription")
    public void removeFromMylist(String productID) {
        customerHomeRemoveFromMylistUseCase.execute(productID, context)
                .doOnSubscribe(disposable -> customerSingleShopProductsView.showProgress(true, "Removing from Mylist..."))
                .subscribe(this::handleRemoveFromMylistResponse, throwable -> handleError(throwable));
    }

    private void handleRemoveFromMylistResponse(Boolean aBoolean) {
        customerSingleShopProductsView.showProgress(false, null);
        Log.d(TAG, "handleRemoveFromMylistResponse: " + aBoolean);
    }

    @SuppressLint("RxLeakedSubscription")
    public void getCategories() {
        retailerAllCategoryUseCase.execute(null, context)
                .subscribe(this::handleCategoryResponse, throwable -> handleError(throwable));
    }

    private void handleCategoryResponse(List<CategoryDataModel> categoryDataModels) {
        Log.d(TAG, "handleCategoryResponse: " + categoryDataModels.size());
        customerSingleShopProductsView.loadCategories(categoryDataModels);
    }

    @SuppressLint("RxLeakedSubscription")
    public void getAllProducts() {
        customerSingleShopProductsUseCase.execute(new String[]{customerSingleShopProductsView.getUserID(), customerSingleShopProductsView.getShopID()}, context)
                .subscribe(this::handleLoadAllProductsResponse, throwable -> handleError(throwable));
    }

    private void handleLoadAllProductsResponse(List<ProductDetailsDataModel> productDetailsDataModels) {
//        customerSingleShopProductsView.loadAllProducts(productDetailsDataModels);
        Log.d(TAG, "handleLoadAllProductsResponse: " + productDetailsDataModels.size());

    }

    private void handleError(Throwable throwable) {
        customerSingleShopProductsView.showProgress(false, null);
        Log.d(TAG, "handleError: " + throwable.getMessage());
    }
}

