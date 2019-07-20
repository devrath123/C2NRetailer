package com.example.c2n.addshop.presenter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.example.c2n.addshop.domain.AddshopUseCase;
import com.example.c2n.addshop.util.AddShopValidator;
import com.example.c2n.core.models.OfferDataModel;
import com.example.c2n.core.models.ShopDataModel;
import com.google.firebase.firestore.DocumentReference;

import java.util.ArrayList;

import javax.inject.Inject;

/**
 * Created by roshan.nimje on 16-05-2018.
 */

public class AddshopPresenter {

    AddShopValidator addShopValidator;
    AddshopUseCase addshopUseCase;
    AddshopView addshopView;
    Context context;

    @Inject
    AddshopPresenter(AddshopUseCase addshopUseCase, AddShopValidator addShopValidator) {
        this.addshopUseCase = addshopUseCase;
        this.addShopValidator = addShopValidator;
    }

    public void bind(AddshopView addshopView, Context context) {
        this.addshopView = addshopView;
        this.context = context;
    }

    public void validateShopDetails() {
        if (!TextUtils.isEmpty(addshopView.getShopNo())) {
            if (!TextUtils.isEmpty(addshopView.getShopLandmark())) {
                if (!TextUtils.isEmpty(addshopView.getShopMobileNo())) {
                    if (addshopView.getShopMobileNo().length() == 10)
                        addshopView.addShop();
                    else {
                        addshopView.setMobileNoError("Please enter correct mobile number");
                    }
                } else {
                    addshopView.setMobileNoError("Please enter mobile number");
                }
            } else {
                addshopView.setLandmarkError();
            }
        } else {
            addshopView.setShopNoError();
        }
    }

    @SuppressLint("RxLeakedSubscription")
    public void addShop() {
        ShopDataModel shopDataModel = new ShopDataModel();
        shopDataModel.setRetailerID(addshopView.getUserEmail());
        shopDataModel.setShopName(addshopView.getShopName());
        shopDataModel.setShopAddress(addshopView.getShopNo() + ", " + addshopView.getShopLandmark() + ", " + addshopView.getShopAddress());
        shopDataModel.setShopCellNo(addshopView.getShopMobileNo());
        shopDataModel.setShopEmail(addshopView.getShopEmail());
        shopDataModel.setShopLatitude(addshopView.getShopLatitude());
        shopDataModel.setShopLongitude(addshopView.getShopLongitude());
        shopDataModel.setShopImageURL(addshopView.getShopPicURL());
        shopDataModel.setProductsList(new ArrayList<>());
        shopDataModel.setOfferDataModel(new OfferDataModel());
        addshopUseCase.execute(shopDataModel, context)
                .doOnSubscribe(disposable -> addshopView.showPregressDialog(true))
                .subscribe(this::handleResponse, throwable -> handleError(throwable));
//        addshopUseCase.execute(new ShopDataModel(addshopView.getUserEmail(), addshopView.getShopName(), addshopView.getShopAddress(), addshopView.getShopMobileNo1(), addshopView.getShopMobileNo2(), addshopView.getShopEmail(), addshopView.getShopLatitude(), addshopView.getShopLongitude()), context)
//                .doOnSubscribe(disposable -> addshopView.showPregressDialog(true))
//                .subscribe(this::handleResponse, throwable -> handleError(throwable));
    }

    private void handleError(Throwable throwable) {
        addshopView.showPregressDialog(false);
        Log.d("AddshopPresenter", "handleError : " + throwable.getMessage());
    }

    private void handleResponse(DocumentReference documentReference) {
        addshopView.showPregressDialog(false);
        addshopView.openViewShop();
        Log.d("AddshopPresenter", "handleResponse : " + documentReference.getId());
    }
}
