package com.example.c2n.customer_cart.presenter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.MainThread;
import android.util.Log;

import com.example.c2n.core.models.DealResponseDataModel;
import com.example.c2n.core.models.MasterProductDataModel;
import com.example.c2n.core.models.ShopDataModel;
import com.example.c2n.customer_cart.domain.CustomerCartGetCartUseCase;
import com.example.c2n.customer_cart.domain.CustomerCartSendNotificationUseCase;
import com.example.c2n.customer_home.domain.CustomerHomeLoadMylistUseCase;

import java.util.List;

import javax.inject.Inject;

public class CustomerCartPresenter {

    private static final String TAG = CustomerCartPresenter.class.getSimpleName();
    private Context context;
    private CustomerCartView customerCartView;

    private CustomerCartGetCartUseCase customerCartGetCartUseCase;
    private CustomerHomeLoadMylistUseCase customerHomeLoadMylistUseCase;
    private CustomerCartSendNotificationUseCase customerCartSendNotificationUseCase;

    @Inject
    CustomerCartPresenter(CustomerCartGetCartUseCase customerCartGetCartUseCase, CustomerHomeLoadMylistUseCase customerHomeLoadMylistUseCase, CustomerCartSendNotificationUseCase customerCartSendNotificationUseCase) {
        this.customerCartGetCartUseCase = customerCartGetCartUseCase;
        this.customerHomeLoadMylistUseCase = customerHomeLoadMylistUseCase;
        this.customerCartSendNotificationUseCase = customerCartSendNotificationUseCase;
    }

    public void bind(CustomerCartView customerCartView, Context context) {
        this.customerCartView = customerCartView;
        this.context = context;
    }

    @SuppressLint("RxLeakedSubscription")
    public void getMylist() {
        customerHomeLoadMylistUseCase.execute(null, context)
                .doOnSubscribe(disposable -> customerCartView.showProgress(true, "Loading..."))
                .subscribe(this::handleMylistResponse, throwable -> handleError(throwable));
    }

    private void handleMylistResponse(List<MasterProductDataModel> products) {
        Log.d(TAG, "handleMylistResponse: " + products.toString());
        customerCartView.showProgress(false, null);
        customerCartView.loadMylist(products);
    }

    @SuppressLint("RxLeakedSubscription")
    public void getCart() {

        double latitude = customerCartView.getLatitude();
        double longitude = customerCartView.getLongitude();
        String productsIDs = customerCartView.getProductsList();
        int radius = customerCartView.getRadious();

        customerCartGetCartUseCase.execute(new Object[]{latitude, longitude, productsIDs, radius}, context)
                .doOnSubscribe(disposable -> customerCartView.showProgress(true, "Loading..."))
                .subscribe(shopEntities -> handleResponse(shopEntities), throwable -> handleError(throwable));
    }

    private void handleResponse(List<ShopDataModel> shopDataModels) {
//        for (int i = 0; i < shopDataModels.size(); i++) {
//            ShopDataModel shopDataModel = shopDataModels.get(i);
//            if (shopDataModel.getProductsList().size() != 0) {
//                List<ProductDataModel> productDataModels = shopDataModel.getProductsList();
//                for (int j = 0; j < productDataModels.size(); j++) {
//                    ProductDataModel productDataModel = productDataModels.get(j);
//                    if (productDataModel.getProductOffer() != null) {
//                        Log.d(TAG, "Date : " + productDataModel.getProductOffer().getOfferStartDate().getSeconds());
//                    }
//                }
//            }
//        }
        customerCartView.loadShops(shopDataModels);
        customerCartView.showProgress(false, null);
        Log.d(TAG, "handleResponse Size : " + shopDataModels.size());
    }

    @SuppressLint("RxLeakedSubscription")
    public void sendNotification() {
        customerCartSendNotificationUseCase.execute(new String[]{customerCartView.getRetailerIDs(),
                customerCartView.getUserName(), customerCartView.getRate(),
                "proposed", "retailer"}, context)
                .subscribe(dealResponseDataModel -> handleSendNotificationResponse(dealResponseDataModel), throwable -> handleError(throwable));

    }

    private void handleSendNotificationResponse(DealResponseDataModel dealResponseDataModel) {
        Log.d(TAG, "handleSendNotificationResponse: " + dealResponseDataModel.toString());
    }

    private void handleError(Throwable throwable) {
        customerCartView.showProgress(false, null);
        customerCartView.showErrorMsg();
        Log.d(TAG, "handleResponse Error : " + throwable.getMessage());
    }
}
