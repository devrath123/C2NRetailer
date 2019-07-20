package com.example.c2n.login.domain;

import android.content.Context;

import com.example.c2n.core.model1.RetailerDataModel;
import com.example.c2n.core.usecase.UseCase;
import com.example.c2n.core.usecase.UseCaseComposer;
import com.example.c2n.login.data.LoginRepository;
import com.example.c2n.signup.data.SignUpRepository;
import com.google.firebase.firestore.DocumentReference;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by vipul.singhal on 24-07-2018.
 */

public class AddRetailerUseCase extends UseCase<RetailerDataModel, DocumentReference> {
    LoginRepository loginRepository;

    @Inject
    protected AddRetailerUseCase(UseCaseComposer useCaseComposer, LoginRepository loginRepository) {
        super(useCaseComposer);
        this.loginRepository = loginRepository;
    }

    @Override
    protected Observable<DocumentReference> createUseCaseObservable(RetailerDataModel retailer, Context context) {
        return loginRepository.addRetailer(retailer);
    }
}
