package com.example.c2n.login.domain;

import android.content.Context;

import com.example.c2n.core.mapper.DocumentSnapshotToCustomerDataModelMapper;
import com.example.c2n.core.mapper.DocumentSnapshotToRetailerDataModelMapper;
import com.example.c2n.core.model1.CustomerDataModel;
import com.example.c2n.core.model1.RetailerDataModel;
import com.example.c2n.core.usecase.UseCase;
import com.example.c2n.core.usecase.UseCaseComposer;
import com.example.c2n.login.data.LoginRepository;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by vipul.singhal on 24-07-2018.
 */

public class GetCustomerUseCase extends UseCase<String,CustomerDataModel> {
    LoginRepository loginRepository;
    DocumentSnapshotToCustomerDataModelMapper documentSnapshotToCustomerDataModelMapper;

    @Inject
    protected GetCustomerUseCase(UseCaseComposer useCaseComposer, LoginRepository loginRepository, DocumentSnapshotToCustomerDataModelMapper documentSnapshotToCustomerDataModelMapper) {
        super(useCaseComposer);
        this.loginRepository = loginRepository;
        this.documentSnapshotToCustomerDataModelMapper = documentSnapshotToCustomerDataModelMapper;
    }

    @Override
    protected Observable<CustomerDataModel> createUseCaseObservable(String customerEmail, Context context) {
        return loginRepository.getCustomers(customerEmail).map(documentSnapshot -> documentSnapshotToCustomerDataModelMapper.mapDocumentListToUsernameBoolean(documentSnapshot));
    }
}
