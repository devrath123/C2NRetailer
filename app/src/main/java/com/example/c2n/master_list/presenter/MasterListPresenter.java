package com.example.c2n.master_list.presenter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import com.example.c2n.core.models.MasterProductDataModel;
import com.example.c2n.master_list.domain.MasterListUseCase;

import java.util.List;

import javax.inject.Inject;

public class MasterListPresenter {

    private String TAG = "MasterListPresenter";
    private MasterListView masterListView;
    private Context context;

    private MasterListUseCase masterListUseCase;

    @Inject
    MasterListPresenter(MasterListUseCase masterListUseCase) {
        this.masterListUseCase = masterListUseCase;
    }

    public void bind(MasterListView masterListView, Context context) {
        this.masterListView = masterListView;
        this.context = context;
    }

    @SuppressLint("RxLeakedSubscription")
    public void getAllProducts() {
        masterListUseCase.execute(null, context)
                .doOnSubscribe(disposable -> masterListView.showProgress(true))
                .subscribe(this::handleResponse, throwable -> handleError(throwable));
    }

    private void handleResponse(List<MasterProductDataModel> masterProducts) {
        Log.d(TAG, "handleResponse Count : " + masterProducts);
        masterListView.showProgress(false);
        masterListView.loadAllProducts(masterProducts);
    }

    private void handleError(Throwable throwable) {
        Log.d(TAG, "Error : " + throwable.getMessage());
    }
}
