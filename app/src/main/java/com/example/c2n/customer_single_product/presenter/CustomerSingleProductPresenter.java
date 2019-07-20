package com.example.c2n.customer_single_product.presenter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import com.example.c2n.core.models.MasterProductDataModel;
import com.example.c2n.core.models.ProductDataModel;
import com.example.c2n.customer_home.domain.CustomerHomeAddToMylistUseCase;
import com.example.c2n.customer_home.domain.CustomerHomeLoadMylistUseCase;
import com.example.c2n.customer_home.domain.CustomerHomeRemoveFromMylistUseCase;
import com.example.c2n.customer_single_product.domain.CustomerSingleProductUseCase;

import java.util.List;

import javax.inject.Inject;

public class CustomerSingleProductPresenter {

    public static final String TAG = CustomerSingleProductPresenter.class.getSimpleName();
    private CustomerSingleProductView customerSingleProductView;
    private CustomerHomeAddToMylistUseCase customerHomeAddToMylistUseCase;
    private CustomerHomeRemoveFromMylistUseCase customerHomeRemoveFromMylistUseCase;
    private CustomerHomeLoadMylistUseCase customerHomeLoadMylistUseCase;
    private Context context;

    private CustomerSingleProductUseCase customerSingleProductUseCase;

    @Inject
    public CustomerSingleProductPresenter(CustomerSingleProductUseCase customerSingleProductUseCase, CustomerHomeAddToMylistUseCase customerHomeAddToMylistUseCase, CustomerHomeRemoveFromMylistUseCase customerHomeRemoveFromMylistUseCase, CustomerHomeLoadMylistUseCase customerHomeLoadMylistUseCase) {
        this.customerSingleProductUseCase = customerSingleProductUseCase;
        this.customerHomeAddToMylistUseCase = customerHomeAddToMylistUseCase;
        this.customerHomeRemoveFromMylistUseCase = customerHomeRemoveFromMylistUseCase;
        this.customerHomeLoadMylistUseCase = customerHomeLoadMylistUseCase;
    }

    public void bind(CustomerSingleProductView customerSingleProductView, Context context) {
        this.customerSingleProductView = customerSingleProductView;
        this.context = context;
    }

    @SuppressLint("RxLeakedSubscription")
    public void getMylist() {
        customerHomeLoadMylistUseCase.execute(null, context)
                .doOnSubscribe(disposable -> customerSingleProductView.showProgress(true, "Loading..."))
                .subscribe(this::handleMylistResponse, throwable -> handleError(throwable));
    }

    private void handleMylistResponse(List<MasterProductDataModel> products) {
        customerSingleProductView.showProgress(false, null);
        customerSingleProductView.loadMylist(products);
    }

    @SuppressLint("RxLeakedSubscription")
    public void addToMyList(MasterProductDataModel products) {
        customerHomeAddToMylistUseCase.execute(products, context)
                .doOnSubscribe(disposable -> customerSingleProductView.showProgress(true, "Adding to Mylist..."))
                .subscribe(this::handleAddToMylistResponse, throwable -> handleError(throwable));
    }

    private void handleAddToMylistResponse(Boolean aBoolean) {
        customerSingleProductView.showProgress(false, null);
        Log.d(TAG, "handleAddToMylistResponse: " + aBoolean);
    }

    @SuppressLint("RxLeakedSubscription")
    public void removeFromMylist(String productID) {
        customerHomeRemoveFromMylistUseCase.execute(productID, context)
                .doOnSubscribe(disposable -> customerSingleProductView.showProgress(true, "Removing from Mylist..."))
                .subscribe(this::handleRemoveFromMylistResponse, throwable -> handleError(throwable));
    }

    private void handleRemoveFromMylistResponse(Boolean aBoolean) {
        customerSingleProductView.showProgress(false, null);
        Log.d(TAG, "handleRemoveFromMylistResponse: " + aBoolean);
    }

    @SuppressLint("RxLeakedSubscription")
    public void getProductOffers() {
        customerSingleProductUseCase.execute(customerSingleProductView.getProductID(), context)
                .doOnSubscribe(disposable -> customerSingleProductView.showProgress(true, "Loading..."))
                .subscribe(productDataModel -> handleResponse(productDataModel), throwable -> handleError(throwable));
    }

    private void handleResponse(ProductDataModel productDataModel) {
        Log.d(TAG, "handleResponse ProductDataModel : " + productDataModel.toString());
        customerSingleProductView.showProgress(false, null);
        customerSingleProductView.loadProductDetails(productDataModel);
    }

    private void handleError(Throwable throwable) {
        Log.d(TAG, "handleError Error : " + throwable.getMessage());
    }
}
