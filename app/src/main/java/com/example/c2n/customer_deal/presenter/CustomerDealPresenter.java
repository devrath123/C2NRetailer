package com.example.c2n.customer_deal.presenter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import com.example.c2n.core.models.CustomerDealDataModel;
import com.example.c2n.customer_deal.domain.LoadDealsUseCase;

import java.util.List;

import javax.inject.Inject;

public class CustomerDealPresenter {

    private static final String TAG = CustomerDealPresenter.class.getSimpleName();

    private Context context;
    private CustomerDealView customerDealView;
    private LoadDealsUseCase loadDealsUseCase;

    @Inject
    public CustomerDealPresenter(LoadDealsUseCase loadDealsUseCase) {
        this.loadDealsUseCase = loadDealsUseCase;
    }

    public void bind(CustomerDealView customerDealView, Context context) {
        this.customerDealView = customerDealView;
        this.context = context;
    }

    @SuppressLint("RxLeakedSubscription")
    public void loadDeals() {
        loadDealsUseCase.execute(null, context)
                .doOnSubscribe(disposable -> customerDealView.showProgress(true))
                .subscribe(customerDealDataModels -> {
                    handleResponse(customerDealDataModels);
                }, throwable -> {
                    handleError(throwable);
                });
    }

    private void handleResponse(List<CustomerDealDataModel> customerDealDataModels) {
        customerDealView.showProgress(false);
        customerDealView.loadDeals(customerDealDataModels);
        Log.d(TAG, "handleResponse: " + customerDealDataModels.size());
    }

    private void handleError(Throwable throwable) {
        Log.d(TAG, "handleError: " + throwable.getMessage());
    }
}
