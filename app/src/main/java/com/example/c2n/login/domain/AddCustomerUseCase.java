package com.example.c2n.login.domain;

import android.content.Context;

import com.example.c2n.core.model1.CustomerDataModel;
import com.example.c2n.core.usecase.UseCase;
import com.example.c2n.core.usecase.UseCaseComposer;
import com.example.c2n.login.data.LoginRepository;
import com.google.firebase.firestore.DocumentReference;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by vipul.singhal on 24-07-2018.
 */

public class AddCustomerUseCase extends UseCase<CustomerDataModel, DocumentReference> {
    LoginRepository loginRepository;

    @Inject
    protected AddCustomerUseCase(UseCaseComposer useCaseComposer, LoginRepository loginRepository) {
        super(useCaseComposer);
        this.loginRepository = loginRepository;
    }

    @Override
    protected Observable<DocumentReference> createUseCaseObservable(CustomerDataModel customer, Context context) {
        return loginRepository.addCustomer(customer);
    }
}
