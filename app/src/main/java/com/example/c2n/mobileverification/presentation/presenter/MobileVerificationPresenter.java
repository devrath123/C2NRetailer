package com.example.c2n.mobileverification.presentation.presenter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import com.example.c2n.core.model1.CustomerDataModel;
import com.example.c2n.core.model1.RetailerDataModel;
import com.example.c2n.mobileverification.presentation.domain.CustomerMobileVerificationUseCase;
import com.example.c2n.mobileverification.presentation.domain.RetailerMobileVerificationUseCase;
import com.google.firebase.firestore.DocumentReference;

import javax.inject.Inject;

/**
 * Created by roshan.nimje on 10-05-2018.
 */

public class MobileVerificationPresenter {

    RetailerMobileVerificationUseCase retailerMobileVerificationUseCase;
    CustomerMobileVerificationUseCase customerMobileVerificationUseCase;
    MobileVerificationView mobileVerificationView;
    Context context;

    @Inject
    MobileVerificationPresenter(RetailerMobileVerificationUseCase retailerMobileVerificationUseCase, CustomerMobileVerificationUseCase customerMobileVerificationUseCase) {
        this.retailerMobileVerificationUseCase = retailerMobileVerificationUseCase;
        this.customerMobileVerificationUseCase = customerMobileVerificationUseCase;
    }

    public void bind(MobileVerificationView mobileVerificationView, Context context) {
        this.mobileVerificationView = mobileVerificationView;
        this.context = context;
    }

    @SuppressLint("RxLeakedSubscription")
    public void updateMobileNo() {
        if (mobileVerificationView.getUserType().equals("R")) {
            RetailerDataModel retailerDataModel = new RetailerDataModel();
            retailerDataModel.setRetailerID(mobileVerificationView.getDocumentID());
            retailerDataModel.setRetailerMobileNo(mobileVerificationView.getMobileNo());
            Log.d("MobileVerificationPr", "RetailerDataModel : " + retailerDataModel.toString());
            retailerMobileVerificationUseCase.execute(retailerDataModel, context)
                    .doOnSubscribe(disposable -> mobileVerificationView.showProgressDialog("Please wait..."))
                    .subscribe(this::handleRetailerResponse, throwable -> handleRetailerError(throwable));
        } else if (mobileVerificationView.getUserType().equals("C")) {
            CustomerDataModel customerDataModel = new CustomerDataModel();
            customerDataModel.setCustomerID(mobileVerificationView.getDocumentID());
            customerDataModel.setCustomerMobileNo(mobileVerificationView.getMobileNo());
            customerMobileVerificationUseCase.execute(customerDataModel, context)
                    .doOnSubscribe(disposable -> mobileVerificationView.showProgressDialog("Please wait..."))
                    .subscribe(this::handleCustomerResponse, throwable -> handleCustomerError(throwable));
        }
    }

    private void handleCustomerResponse(DocumentReference documentReference) {
        mobileVerificationView.hideProgressDialog();
        mobileVerificationView.openEditProfile();
        Log.d("handleResponse", "success");
    }

    private void handleCustomerError(Throwable throwable) {
        mobileVerificationView.hideProgressDialog();
        Log.d("MobileVerificationPr", "" + throwable.getMessage());
    }

    private void handleRetailerResponse(DocumentReference documentReference) {
        mobileVerificationView.hideProgressDialog();
        mobileVerificationView.openEditProfile();
        Log.d("handleResponse", "success");
    }

    private void handleRetailerError(Throwable throwable) {
        mobileVerificationView.hideProgressDialog();
        Log.d("MobileVerificationPr", "" + throwable.getMessage());
    }

}
