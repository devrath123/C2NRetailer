package com.example.c2n.retailer_deal.presenter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import com.example.c2n.core.models.DealResponseDataModel;
import com.example.c2n.core.models.RetailerDealDataModel;
import com.example.c2n.core.models.ShopDealDataModel;
import com.example.c2n.retailer_deal.domain.LoadDealsUseCase;
import com.example.c2n.retailer_deal.domain.RetailerDealSendNotificationUseCase;
import com.example.c2n.retailer_deal.domain.UpdateCustomerAcceptedUseCase;
import com.example.c2n.retailer_deal.domain.UpdateCustomerDeclinedUseCase;
import com.example.c2n.retailer_deal.domain.UpdateRetailerAcceptedUseCase;
import com.example.c2n.retailer_deal.domain.UpdateRetailerDeclinedUseCase;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

import javax.inject.Inject;

public class RetailerDealPresenter {

    private static final String TAG = RetailerDealPresenter.class.getSimpleName();

    private RetailerDealView retailerDealView;
    private Context context;
    private LoadDealsUseCase loadDealsUseCase;
    private UpdateCustomerAcceptedUseCase updateCustomerAcceptedUseCase;
    private UpdateRetailerAcceptedUseCase updateRetailerAcceptedUseCase;
    private UpdateCustomerDeclinedUseCase updateCustomerDeclinedUseCase;
    private UpdateRetailerDeclinedUseCase updateRetailerDeclinedUseCase;
    private RetailerDealSendNotificationUseCase retailerDealSendNotificationUseCase;

    @Inject
    public RetailerDealPresenter(LoadDealsUseCase loadDealsUseCase, UpdateCustomerAcceptedUseCase updateCustomerAcceptedUseCase, UpdateRetailerAcceptedUseCase updateRetailerAcceptedUseCase, UpdateCustomerDeclinedUseCase updateCustomerDeclinedUseCase, UpdateRetailerDeclinedUseCase updateRetailerDeclinedUseCase, RetailerDealSendNotificationUseCase retailerDealSendNotificationUseCase) {
        this.loadDealsUseCase = loadDealsUseCase;
        this.updateCustomerAcceptedUseCase = updateCustomerAcceptedUseCase;
        this.updateRetailerAcceptedUseCase = updateRetailerAcceptedUseCase;
        this.updateCustomerDeclinedUseCase = updateCustomerDeclinedUseCase;
        this.updateRetailerDeclinedUseCase = updateRetailerDeclinedUseCase;
        this.retailerDealSendNotificationUseCase = retailerDealSendNotificationUseCase;
    }

    public void bind(RetailerDealView retailerDealView, Context context) {
        this.retailerDealView = retailerDealView;
        this.context = context;
    }

    @SuppressLint("RxLeakedSubscription")
    public void loadDeals() {
        loadDealsUseCase.execute(null, context)
                .doOnSubscribe(disposable -> retailerDealView.showProgress(true))
                .subscribe(retailerDealDataModels -> {
                    handleResponse(retailerDealDataModels);
                }, throwable -> {
                    handleError(throwable);
                });
    }

    private void handleResponse(List<RetailerDealDataModel> retailerDealDataModels) {
        Log.d(TAG, "handleResponse: " + retailerDealDataModels.toString());
        retailerDealView.showProgress(false);
        retailerDealView.loadDeals(retailerDealDataModels);
    }

    @SuppressLint("RxLeakedSubscription")
    public void updateCustomerAccepted(ShopDealDataModel shopDealDataModel) {
        updateCustomerAcceptedUseCase.execute(new String[]{shopDealDataModel.getUserID(), shopDealDataModel.getKey()}, context)
                .doOnSubscribe(disposable -> retailerDealView.showProgress(true))
                .subscribe(databaseReference -> handleUpdateCustomerResponseAccepted(databaseReference), throwable -> handleError(throwable));
    }

    private void handleUpdateCustomerResponseAccepted(DatabaseReference databaseReference) {
        retailerDealView.showProgress(false);
        retailerDealView.updateRetailerAccepted();
    }

    @SuppressLint("RxLeakedSubscription")
    public void updateRetailerAccepted(ShopDealDataModel shopDealDataModel) {
        updateRetailerAcceptedUseCase.execute(shopDealDataModel.getKey(), context)
                .doOnSubscribe(disposable -> retailerDealView.showProgress(true))
                .subscribe(databaseReference -> handleUpdateRetailerResponseAccepted(databaseReference), throwable -> handleError(throwable));
    }

    private void handleUpdateRetailerResponseAccepted(DatabaseReference databaseReference) {
        retailerDealView.showProgress(false);
    }

    @SuppressLint("RxLeakedSubscription")
    public void updateCustomerDeclined(ShopDealDataModel shopDealDataModel) {
        updateCustomerDeclinedUseCase.execute(new String[]{shopDealDataModel.getUserID(), shopDealDataModel.getKey()}, context)
                .doOnSubscribe(disposable -> retailerDealView.showProgress(true))
                .subscribe(databaseReference -> handleUpdateCustomerResponseDeclined(databaseReference), throwable -> handleError(throwable));
    }

    private void handleUpdateCustomerResponseDeclined(DatabaseReference databaseReference) {
        retailerDealView.showProgress(false);
        retailerDealView.updateRetailerDeclined();
    }

    @SuppressLint("RxLeakedSubscription")
    public void updateRetailerDeclined(ShopDealDataModel shopDealDataModel) {
        updateRetailerDeclinedUseCase.execute(shopDealDataModel.getKey(), context)
                .doOnSubscribe(disposable -> retailerDealView.showProgress(true))
                .subscribe(databaseReference -> handleUpdateRetailerResponseDeclined(databaseReference), throwable -> handleError(throwable));
    }

    private void handleUpdateRetailerResponseDeclined(DatabaseReference databaseReference) {
        retailerDealView.showProgress(false);
    }

    @SuppressLint("RxLeakedSubscription")
    public void sendNotification(ShopDealDataModel shopDealDataModel, String status) {
        retailerDealSendNotificationUseCase.execute(new String[]{shopDealDataModel.getUserID(),
                shopDealDataModel.getShopDataModel().getShopName(), shopDealDataModel.getDiscount(),
                status, "customer"}, context)
                .subscribe(dealResponseDataModel -> handleSendNotificationResponse(dealResponseDataModel), throwable -> handleError(throwable));
    }

    private void handleSendNotificationResponse(DealResponseDataModel dealResponseDataModel) {
        Log.d(TAG, "handleSendNotificationResponse: " + dealResponseDataModel.toString());
    }

    private void handleError(Throwable throwable) {
        retailerDealView.showProgress(false);
        Log.d(TAG, "handleError: " + throwable.getMessage());
    }
}