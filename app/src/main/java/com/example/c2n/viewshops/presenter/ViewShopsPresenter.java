package com.example.c2n.viewshops.presenter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import com.example.c2n.core.models.ShopDataModel;
import com.example.c2n.viewshops.domain.ViewShopsUseCase;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by roshan.nimje on 21-05-2018.
 */

public class ViewShopsPresenter {

    ViewShopsView viewShopsView;
    ViewShopsUseCase viewShopsUseCase;
    Context context;

    @Inject
    ViewShopsPresenter(ViewShopsUseCase viewShopsUseCase) {
        this.viewShopsUseCase = viewShopsUseCase;
    }

    public void bind(ViewShopsView viewShopsView, Context context) {
        this.viewShopsView = viewShopsView;
        this.context = context;
    }

    @SuppressLint("RxLeakedSubscription")
    public void loadShops() {
        Log.d("ViewShopsPresenter", "" + viewShopsView.getUserID());
        viewShopsUseCase.execute(viewShopsView.getUserID(), context)
                .doOnSubscribe(disposable -> viewShopsView.showViewShopProgress(true))
                .subscribe(this::handleResponse, throwable -> handleError(throwable));
    }

    private void handleResponse(List<ShopDataModel> shopDataModels) {
        viewShopsView.showViewShopProgress(false);
        viewShopsView.showShopsList(shopDataModels);
        Log.d("ViewShopsPresenter", "handleResponse : " + shopDataModels.size());
    }

    private void handleError(Throwable throwable) {
        viewShopsView.showViewShopProgress(false);
        viewShopsView.showNoShopScreen();
        Log.d("ViewShopsPresenter", "handleError : " + throwable.getMessage());
    }

//    @SuppressLint("RxLeakedSubscription")
//    public void loadShops() {
//        viewShopsUseCase.execute(null, context)
//                .doOnSubscribe(disposable -> viewShopsView.showViewShopProgress(true))
//                .subscribe(this::handleResponse, throwable -> handleError(throwable));
//    }
//
//    private void handleResponse(List<QueryDocumentSnapshot> documentSnapshotList) {
//        Log.d("ViewShopsPresenter__", "" + documentSnapshotList.size());
//    }
//
//    private void handleError(Throwable throwable) {
//
//    }
}
