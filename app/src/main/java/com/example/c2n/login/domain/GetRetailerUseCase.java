package com.example.c2n.login.domain;

import android.content.Context;

import com.example.c2n.core.mapper.DocumentSnapshotToRetailerDataModelMapper;
import com.example.c2n.core.model1.RetailerDataModel;
import com.example.c2n.core.usecase.UseCase;
import com.example.c2n.core.usecase.UseCaseComposer;
import com.example.c2n.login.data.LoginRepository;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by vipul.singhal on 24-07-2018.
 */

public class GetRetailerUseCase extends UseCase<String, RetailerDataModel> {
    LoginRepository loginRepository;
    DocumentSnapshotToRetailerDataModelMapper documentSnapshotToRetailerDataModelMapper;

    @Inject
    protected GetRetailerUseCase(UseCaseComposer useCaseComposer, LoginRepository loginRepository, DocumentSnapshotToRetailerDataModelMapper documentSnapshotToRetailerDataModelMapper) {
        super(useCaseComposer);
        this.loginRepository = loginRepository;
        this.documentSnapshotToRetailerDataModelMapper = documentSnapshotToRetailerDataModelMapper;
    }

    @Override
    protected Observable<RetailerDataModel> createUseCaseObservable(String retailerEmail, Context context) {
        return loginRepository.getRetailers(retailerEmail).map(documentSnapshot -> documentSnapshotToRetailerDataModelMapper.mapDocumentListToUsernameBoolean(documentSnapshot));
    }
}
