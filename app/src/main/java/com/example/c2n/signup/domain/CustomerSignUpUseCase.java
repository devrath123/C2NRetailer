package com.example.c2n.signup.domain;

import android.content.Context;

import com.example.c2n.core.model.RetailerDataModel;
import com.example.c2n.core.model1.CustomerDataModel;
import com.example.c2n.core.usecase.UseCase;
import com.example.c2n.core.usecase.UseCaseComposer;
import com.example.c2n.signup.data.SignUpRepository;
import com.google.firebase.firestore.DocumentReference;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by vipul.singhal on 24-07-2018.
 */

public class CustomerSignUpUseCase extends UseCase<CustomerDataModel,DocumentReference> {
    SignUpRepository signUpRepository;

    @Inject
    protected CustomerSignUpUseCase(UseCaseComposer useCaseComposer, SignUpRepository signUpRepository) {
        super(useCaseComposer);
        this.signUpRepository = signUpRepository;
    }

    @Override
    protected Observable<DocumentReference> createUseCaseObservable(CustomerDataModel user, Context context) {
        return signUpRepository.addCustomer(user);
    }
}
