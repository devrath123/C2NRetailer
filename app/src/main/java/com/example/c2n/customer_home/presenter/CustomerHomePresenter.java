package com.example.c2n.customer_home.presenter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import com.example.c2n.core.models.CategoryDataModel;
import com.example.c2n.core.models.MasterProductDataModel;
import com.example.c2n.core.models.MasterProductDetailsDataModel;
import com.example.c2n.core.models.ProductDetailsDataModel;
import com.example.c2n.customer_home.domain.CustomerHomeAddToMylistUseCase;
import com.example.c2n.customer_home.domain.CustomerHomeLoadAllMasterProductsUseCase;
import com.example.c2n.customer_home.domain.CustomerHomeLoadMylistUseCase;
import com.example.c2n.customer_home.domain.CustomerHomeRemoveFromMylistUseCase;
import com.example.c2n.retailercategory.domain.RetailerAllCategoryUseCase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

public class CustomerHomePresenter {

    private static final String TAG = CustomerHomePresenter.class.getSimpleName();

    private Context context;
    private CustomerHomeView customerHomeView;
    private CustomerHomeLoadAllMasterProductsUseCase customerHomeLoadAllMasterProductsUseCase;
    private RetailerAllCategoryUseCase retailerAllCategoryUseCase;
    private CustomerHomeAddToMylistUseCase customerHomeAddToMylistUseCase;
    private CustomerHomeRemoveFromMylistUseCase customerHomeRemoveFromMylistUseCase;
    private CustomerHomeLoadMylistUseCase customerHomeLoadMylistUseCase;

    private List<String> userIDs = new ArrayList<>();
    private List<String> shopIDs = new ArrayList<>();

    private List<HashMap<String, List<String>>> userShops = new ArrayList<>();

    private List<ProductDetailsDataModel> productDetailsDataModels = new ArrayList<>();

    private int currentUserCount = 0;
    private int currentShopCount = 0;

    @Inject
    public CustomerHomePresenter(RetailerAllCategoryUseCase retailerAllCategoryUseCase, CustomerHomeLoadAllMasterProductsUseCase customerHomeLoadAllMasterProductsUseCase, CustomerHomeAddToMylistUseCase customerHomeAddToMylistUseCase, CustomerHomeRemoveFromMylistUseCase customerHomeRemoveFromMylistUseCase, CustomerHomeLoadMylistUseCase customerHomeLoadMylistUseCase) {
        this.retailerAllCategoryUseCase = retailerAllCategoryUseCase;
        this.customerHomeLoadAllMasterProductsUseCase = customerHomeLoadAllMasterProductsUseCase;
        this.customerHomeAddToMylistUseCase = customerHomeAddToMylistUseCase;
        this.customerHomeRemoveFromMylistUseCase = customerHomeRemoveFromMylistUseCase;
        this.customerHomeLoadMylistUseCase = customerHomeLoadMylistUseCase;
    }

    public void bind(CustomerHomeView customerHomeView, Context context) {
        this.customerHomeView = customerHomeView;
        this.context = context;
    }

    @SuppressLint("RxLeakedSubscription")
    public void getCategories() {
        retailerAllCategoryUseCase.execute(null, context)
                .subscribe(this::handleCategoryResponse, throwable -> handleError(throwable));
    }

    private void handleCategoryResponse(List<CategoryDataModel> categoryDataModels) {
        customerHomeView.loadCategories(categoryDataModels);
    }

    @SuppressLint("RxLeakedSubscription")
    public void getMylist() {
        customerHomeLoadMylistUseCase.execute(null, context)
                .subscribe(this::handleMylistResponse, throwable -> handleError(throwable));
    }

    private void handleMylistResponse(List<MasterProductDataModel> products) {
        customerHomeView.loadMylist(products);
    }

    @SuppressLint("RxLeakedSubscription")
    public void addToMyList(MasterProductDataModel masterProductDataModel) {
        customerHomeAddToMylistUseCase.execute(masterProductDataModel, context)
                .doOnSubscribe(disposable -> customerHomeView.showProgressDialog("Adding to Mylist..."))
                .subscribe(this::handleAddToMylistResponse, throwable -> handleError(throwable));
    }

    private void handleAddToMylistResponse(Boolean aBoolean) {
        customerHomeView.dismissProgressDialog();
        Log.d(TAG, "handleAddToMylistResponse: " + aBoolean);
    }

    @SuppressLint("RxLeakedSubscription")
    public void removeFromMylist(String productID) {
        customerHomeRemoveFromMylistUseCase.execute(productID, context)
                .doOnSubscribe(disposable -> customerHomeView.showProgressDialog("Removing from Mylist..."))
                .subscribe(this::handleRemoveFromMylistResponse, throwable -> handleError(throwable));
    }

    private void handleRemoveFromMylistResponse(Boolean aBoolean) {
        customerHomeView.dismissProgressDialog();
        Log.d(TAG, "handleRemoveFromMylistResponse: " + aBoolean);
    }

    @SuppressLint("RxLeakedSubscription")
    public void getAllMasterProducts() {
        customerHomeLoadAllMasterProductsUseCase.execute(null, context)
                .subscribe(this::handleLoadAllMasterProductsResponse, throwable -> handleError(throwable));
    }

    private void handleLoadAllMasterProductsResponse(List<MasterProductDataModel> masterProductDataModels) {
        Log.d(TAG, "handleLoadAllMasterProductsResponse Count : " + masterProductDataModels.size());
        Log.d(TAG, "handleLoadAllMasterProductsResponse: " + masterProductDataModels.toString());
        List<MasterProductDetailsDataModel> masterProductDetailsDataModels = new ArrayList<>();
        for (int i = 0; i < masterProductDataModels.size(); i++) {
            MasterProductDetailsDataModel masterProductDetailsDataModel = new MasterProductDetailsDataModel();
            masterProductDetailsDataModel.setMasterProductDataModel(masterProductDataModels.get(i));
            masterProductDetailsDataModel.setMylisted(false);
            masterProductDetailsDataModels.add(masterProductDetailsDataModel);
        }
        customerHomeView.loadMasterProducts(masterProductDetailsDataModels);
    }

    private void handleError(Throwable throwable) {
        customerHomeView.dismissProgressDialog();
        Log.d(TAG, "Error : " + throwable.getMessage());
    }
}