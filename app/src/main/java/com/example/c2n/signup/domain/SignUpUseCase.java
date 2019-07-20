package com.example.c2n.signup.domain;

import android.content.Context;

import com.example.c2n.core.model.UserDataModel;
import com.example.c2n.core.usecase.UseCase;
import com.example.c2n.core.usecase.UseCaseComposer;
import com.example.c2n.signup.data.SignUpRepository;
import com.google.firebase.firestore.DocumentReference;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by vipul.singhal on 10-05-2018.
 */

public class SignUpUseCase extends UseCase<UserDataModel, DocumentReference> {

    SignUpRepository signUpRepository;

    @Inject
    protected SignUpUseCase(UseCaseComposer useCaseComposer, SignUpRepository signUpRepository) {
        super(useCaseComposer);
        this.signUpRepository = signUpRepository;
    }

    @Override
    protected Observable<DocumentReference> createUseCaseObservable(UserDataModel user, Context context) {
//        return signUpRepository.addUser(user);
        return null;
    }
}
