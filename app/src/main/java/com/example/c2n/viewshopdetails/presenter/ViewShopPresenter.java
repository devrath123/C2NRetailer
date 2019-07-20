package com.example.c2n.viewshopdetails.presenter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import com.example.c2n.core.models.OfferDataModel;
import com.example.c2n.core.models.ShopDataModel;
import com.example.c2n.viewshopdetails.domain.ViewShopAddUseCase;
import com.example.c2n.viewshopdetails.domain.ViewShopDeleteUseCase;
import com.example.c2n.viewshopdetails.domain.ViewShopUseCase;
import com.example.c2n.viewshopdetails.util.ViewShopDetailsValidator;
import com.google.firebase.firestore.DocumentReference;

import javax.inject.Inject;

/**
 * Created by roshan.nimje on 24-05-2018.
 */

public class ViewShopPresenter {

    private static final String TAG = ViewShopPresenter.class.getSimpleName();
    ViewShopDetailsValidator viewShopDetailsValidator;
    ViewShopUseCase viewShopUseCase;
    ViewShopDeleteUseCase viewShopDeleteUseCase;
    ViewShopAddUseCase viewShopAddUseCase;
    ViewShopView viewShopView;
    Context context;

    @Inject
    ViewShopPresenter(ViewShopUseCase viewShopUseCase, ViewShopDeleteUseCase viewShopDeleteUseCase, ViewShopAddUseCase viewShopAddUseCase, ViewShopDetailsValidator viewShopDetailsValidator) {
        this.viewShopUseCase = viewShopUseCase;
        this.viewShopDeleteUseCase = viewShopDeleteUseCase;
        this.viewShopAddUseCase = viewShopAddUseCase;
        this.viewShopDetailsValidator = viewShopDetailsValidator;
    }

    public void bind(ViewShopView viewShopView, Context context) {
        this.viewShopView = viewShopView;
        this.context = context;
    }

    public void validateShopDetails() {
        if (!TextUtils.isEmpty(viewShopView.getShopName())) {
//            if (!TextUtils.isEmpty(viewShopView.getShopLandmark())) {
            if (!TextUtils.isEmpty(viewShopView.getShopContactNo())) {
                if (viewShopView.getShopContactNo().length() == 10) {
                    if (!TextUtils.isEmpty(viewShopView.getShopEmail())) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO) {
                            if (viewShopDetailsValidator.isValidEmail(viewShopView.getShopEmail())) {
                                if (!TextUtils.isEmpty(viewShopView.getShopAddress())) {
                                    viewShopView.uploadImage();
                                } else {
                                    viewShopView.setShopAddressError("Please enter shop address");
                                }
                            } else {
                                viewShopView.setShopEmailError("Please enter correct shop email");
                            }
                        }
                    } else {
                        viewShopView.setShopEmailError("Please enter shop email");
                    }
                } else {
                    viewShopView.setShopContactNoError("Please enter correct mobile number");
                }
            } else {
                viewShopView.setShopContactNoError("Please enter shop mobile number");
            }
//            } else {
//                viewShopView.setShopLandmarkError();
//            }
        } else {
            viewShopView.setShopNameError();
        }
    }

    @SuppressLint("RxLeakedSubscription")
    public void updateShopDetails() {
        ShopDataModel shopDataModel = new ShopDataModel();
//        shopDataModel.setShopID("");
        shopDataModel.setShopID(viewShopView.getShopID());
        shopDataModel.setRetailerID(viewShopView.getRetailerID());
        shopDataModel.setShopEmail(viewShopView.getShopEmail());
        shopDataModel.setShopAddress(viewShopView.getShopAddress());
        shopDataModel.setShopCellNo(viewShopView.getShopContactNo());
        shopDataModel.setShopLongitude(viewShopView.getLongitude());
        shopDataModel.setShopLatitude(viewShopView.getLatitude());
        shopDataModel.setShopName(viewShopView.getShopName());
        shopDataModel.setShopImageURL(viewShopView.getShopImageURL());
        shopDataModel.setOfferDataModel(new OfferDataModel());
        viewShopUseCase.execute(shopDataModel, context)
                .doOnSubscribe(disposable -> viewShopView.isShowShopSuccess(true))
                .subscribe(this::handleUpdateShopResponse, throwable -> handleError(throwable));
    }

    private void handleUpdateShopResponse(Boolean aBoolean) {
        viewShopView.isShowShopSuccess(false);
        viewShopView.openRetailerHome();
    }

    private void handleResponse(DocumentReference documentReference) {
        Log.d(TAG, "handleResponse: " + documentReference.getId());
        viewShopView.isShowShopSuccess(false);
        viewShopView.openRetailerHome();

//        Log.d("ViewShopPresenter", "Success " + shopDataModel.toString());
//        viewShopView.setRetailerID(shopDataModel.getRetailerID());
//        viewShopView.setShopName(shopDataModel.getShopName());
//        viewShopView.setShopAddress(shopDataModel.getShopAddress());
//        viewShopView.setShopEmail(shopDataModel.getShopEmail());
//        viewShopView.setShopMobileNo1(shopDataModel.getShopCellNo1());
//        viewShopView.setShopMobileNo2(shopDataModel.getShopCellNo2());
    }

    private void handleError(Throwable throwable) {
        viewShopView.isShowShopSuccess(false);
        Log.d(TAG, "handleError: " + throwable.getMessage());
    }

    @SuppressLint("RxLeakedSubscription")
    public void deleteShop() {
        ShopDataModel shopDataModel = new ShopDataModel();
        shopDataModel.setRetailerID(viewShopView.getRetailerID());
        shopDataModel.setShopEmail(viewShopView.getPreviousShopID());
        shopDataModel.setShopAddress(viewShopView.getShopAddress());
        shopDataModel.setShopCellNo(viewShopView.getShopContactNo());
        shopDataModel.setShopLongitude(viewShopView.getLongitude());
        shopDataModel.setShopLatitude(viewShopView.getLatitude());
        shopDataModel.setShopName(viewShopView.getShopName());
        shopDataModel.setShopImageURL(viewShopView.getShopImageURL());
        viewShopDeleteUseCase.execute(shopDataModel, context)
                .doOnSubscribe(disposable -> viewShopView.isShowShopSuccess(true))
                .subscribe(this::handleDeleteResponse, throwable -> handleError(throwable));
    }

    private void handleDeleteResponse(DocumentReference documentReference) {
        addShop();
    }

    @SuppressLint("RxLeakedSubscription")
    public void addShop() {
        ShopDataModel shopDataModel = new ShopDataModel();
        shopDataModel.setRetailerID(viewShopView.getRetailerID());
        shopDataModel.setShopEmail(viewShopView.getShopEmail());
        shopDataModel.setShopAddress(viewShopView.getShopAddress());
        shopDataModel.setShopCellNo(viewShopView.getShopContactNo());
        shopDataModel.setShopLongitude(viewShopView.getLongitude());
        shopDataModel.setShopLatitude(viewShopView.getLatitude());
        shopDataModel.setShopName(viewShopView.getShopName());
        shopDataModel.setShopImageURL(viewShopView.getShopImageURL());
        viewShopAddUseCase.execute(shopDataModel, context)
                .doOnSubscribe(disposable -> viewShopView.isShowShopSuccess(true))
                .subscribe(this::handleResponse, throwable -> handleError(throwable));
    }
}
