package com.example.c2n.signUp1.presenter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import com.example.c2n.core.mapper.CustomerDataModelToBooleanViewModelMapper;
import com.example.c2n.core.mapper.RetailerDataModelToBooleanViewModelMapper;
import com.example.c2n.core.model.UserDataModelListToUsernameViewModelMapper;
import com.example.c2n.core.model1.CustomerDataModel;
import com.example.c2n.core.model1.RetailerDataModel;
import com.example.c2n.signUp1.domain.ValidateCustomerEmailUseCase;
import com.example.c2n.signUp1.domain.ValidateRetailerEmailUseCase;
import com.example.c2n.signUp1.util.SignUpValidator;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by roshan.nimje on 05-06-2018.
 */

public class SignUp1Presenter {

    SignUpValidator signUpValidator;
    RetailerDataModelToBooleanViewModelMapper retailerDataModelToBooleanViewModelMapper;
    CustomerDataModelToBooleanViewModelMapper customerDataModelToBooleanViewModelMapper;
    ValidateRetailerEmailUseCase validateRetailerEmailUseCase;
    ValidateCustomerEmailUseCase validateCustomerEmailUseCase;
    Context context;
    SignUp1View signUp1View;

    @Inject
    public SignUp1Presenter(RetailerDataModelToBooleanViewModelMapper retailerDataModelToBooleanViewModelMapper, CustomerDataModelToBooleanViewModelMapper customerDataModelToBooleanViewModelMapper, ValidateRetailerEmailUseCase validateRetailerEmailUseCase, ValidateCustomerEmailUseCase validateCustomerEmailUseCase, UserDataModelListToUsernameViewModelMapper userDataModelListToUsernameViewModelMapper, SignUpValidator signUpValidator) {
        this.retailerDataModelToBooleanViewModelMapper = retailerDataModelToBooleanViewModelMapper;
        this.customerDataModelToBooleanViewModelMapper = customerDataModelToBooleanViewModelMapper;
        this.validateRetailerEmailUseCase = validateRetailerEmailUseCase;
        this.validateCustomerEmailUseCase = validateCustomerEmailUseCase;
        this.signUpValidator = signUpValidator;
    }

    public void bind(SignUp1View signUp1View, Context context) {
        this.signUp1View = signUp1View;
        this.context = context;
    }

    public void validateEmail() {
        if (signUpValidator.isValidEmail(signUp1View.getUserEmail()))
            isUserEmailUnique();
        else
            signUp1View.showEmailError("Enter correct Email Id");
    }

    @SuppressLint("RxLeakedSubscription")
    public void isUserEmailUnique() {
        if (signUp1View.getUserType().equals("R")) {
            validateRetailerEmailUseCase.execute(null, context)
                    .doOnSubscribe(disposable -> {
                        signUp1View.showEmailProgress(true);
                    })
                    .subscribe(this::handleRetailerResponse, throwable -> handleRetailerResponseError(throwable));
        } else if (signUp1View.getUserType().equals("C")) {
            validateCustomerEmailUseCase.execute(null, context)
                    .doOnSubscribe(disposable -> {
                        signUp1View.showEmailProgress(true);
                    })
                    .subscribe(this::handleCustomerResponse, throwable -> handleCustomerResponseError(throwable));
        }
    }

    private void handleCustomerResponseError(Throwable throwable) {
        signUp1View.showEmailProgress(false);
        Log.d("SignUp1Presenter", "Error : " + throwable.getMessage());
    }

    private void handleCustomerResponse(List<CustomerDataModel> customerDataModels) {
        signUp1View.showEmailProgress(false);
        Log.d("SignUp1Presenter", "Success : " + customerDataModels.size());
        signUp1View.isUserExist(customerDataModelToBooleanViewModelMapper.mapCustomerDataModelListToEmailBoolean(customerDataModels, signUp1View.getUserEmail()));
    }

    private void handleRetailerResponseError(Throwable throwable) {
        signUp1View.showEmailProgress(false);
        Log.d("SignUp1Presenter", "Error : " + throwable.getMessage());
    }

    private void handleRetailerResponse(List<RetailerDataModel> retailerDataModels) {
        signUp1View.showEmailProgress(false);
        Log.d("SignUp1Presenter", "Success : " + retailerDataModels.size());
        signUp1View.isUserExist(retailerDataModelToBooleanViewModelMapper.mapRetailerDataModelListToEmailBoolean(retailerDataModels, signUp1View.getUserEmail()));
    }
}
