package com.example.c2n.customercategory.presenter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import com.example.c2n.core.models.CategoryDataModel;
import com.example.c2n.customercategory.domain.CustomerCategoryUseCase;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by shivani.singh on 17-08-2018.
 */

public class CustomerCategoryPresenter {

    CustomerCategoryUseCase customerCategoryUseCase;
    CustomerCategoryView customerCategoryView;
    List<CategoryDataModel> categoryList;
    Context context;

    @Inject
    public CustomerCategoryPresenter(CustomerCategoryUseCase customerCategoryUseCase) {
        this.customerCategoryUseCase = customerCategoryUseCase;
    }

   public void bind(CustomerCategoryView customerCategoryView,Context context){
        this.customerCategoryView = customerCategoryView;
        this.context = context;
   }



    @SuppressLint("RxLeakedSubscription")
    public void getCategory() {
        customerCategoryUseCase.execute(null, context)
                .doOnSubscribe(disposable -> customerCategoryView.showProgress(true))
                .subscribe(this::handleCategoryResponse, throwable -> handleError(throwable));
    }

    private void handleCategoryResponse(List<CategoryDataModel> categoryDataModels) {
        customerCategoryView.showProgress(false);
        customerCategoryView.loadCategories(categoryDataModels,null);
        Log.d("CustCategoryPresenter", "" + categoryDataModels.size());
    }

    private void handleError(Throwable throwable) {
        Log.d("CustCategoryError", "error : " + throwable.getMessage());
        customerCategoryView.loadCategories(null, throwable.getMessage());

    }

}
