package com.example.c2n.signup.domain;

import android.content.Context;

import com.example.c2n.core.model1.RetailerDataModel;
import com.example.c2n.core.usecase.UseCase;
import com.example.c2n.core.usecase.UseCaseComposer;
import com.example.c2n.signup.data.SignUpRepository;
import com.google.firebase.firestore.DocumentReference;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by vipul.singhal on 24-07-2018.
 */

public class RetailerSignUpUseCase extends UseCase<RetailerDataModel, DocumentReference> {
    SignUpRepository signUpRepository;

    @Inject
    protected RetailerSignUpUseCase(UseCaseComposer useCaseComposer, SignUpRepository signUpRepository) {
        super(useCaseComposer);
        this.signUpRepository = signUpRepository;
    }

    @Override
    protected Observable<DocumentReference> createUseCaseObservable(RetailerDataModel user, Context context) {
        return signUpRepository.addRetailer(user);
    }
}
