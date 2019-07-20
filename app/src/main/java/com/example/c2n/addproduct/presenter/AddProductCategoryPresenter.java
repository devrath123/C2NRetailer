package com.example.c2n.addproduct.presenter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import com.example.c2n.core.models.CategoryDataModel;
import com.example.c2n.retailercategory.domain.RetailerAllCategoryUseCase;

import java.util.List;

import javax.inject.Inject;

public class AddProductCategoryPresenter {

    private final String TAG = "AddProductCategoryPrese";
    private Context context;
    private AddProductCategoryView addProductInfoView;

    private RetailerAllCategoryUseCase retailerAllCategoryUseCase;

    @Inject
    public AddProductCategoryPresenter(RetailerAllCategoryUseCase retailerAllCategoryUseCase) {
        this.retailerAllCategoryUseCase = retailerAllCategoryUseCase;
    }

    public void bind(Context context, AddProductCategoryView addProductInfoView) {
        this.context = context;
        this.addProductInfoView = addProductInfoView;
    }

    @SuppressLint("RxLeakedSubscription")
    public void getAllCategory() {
        retailerAllCategoryUseCase.execute(null, context)
                .doOnSubscribe(disposable -> addProductInfoView.showLoadingOffersProgress(true, "Loading categories..."))
                .subscribe(this::handleCategoryResponse, throwable -> handleError(throwable));
    }

    private void handleCategoryResponse(List<CategoryDataModel> categoryDataModels) {
        addProductInfoView.showLoadingOffersProgress(false, null);
        addProductInfoView.loadAallCategory(categoryDataModels);
    }

    private void handleError(Throwable throwable) {
        Log.d(TAG, "Error : " + throwable.getMessage());
    }
}
