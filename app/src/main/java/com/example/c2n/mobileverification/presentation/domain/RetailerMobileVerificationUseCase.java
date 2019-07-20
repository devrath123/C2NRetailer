package com.example.c2n.mobileverification.presentation.domain;

import android.content.Context;

import com.example.c2n.core.model1.RetailerDataModel;
import com.example.c2n.core.usecase.UseCase;
import com.example.c2n.core.usecase.UseCaseComposer;
import com.example.c2n.mobileverification.presentation.data.MobileVerificationRepository;
import com.google.firebase.firestore.DocumentReference;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by roshan.nimje on 16-05-2018.
 */

public class RetailerMobileVerificationUseCase extends UseCase<RetailerDataModel, DocumentReference> {

    MobileVerificationRepository mobileVerificationRepository;

    @Inject
    protected RetailerMobileVerificationUseCase(UseCaseComposer useCaseComposer, MobileVerificationRepository mobileVerificationRepository) {
        super(useCaseComposer);
        this.mobileVerificationRepository = mobileVerificationRepository;
    }


    @Override
    protected Observable<DocumentReference> createUseCaseObservable(RetailerDataModel param, Context context) {
        return mobileVerificationRepository.updateRetailerMobileNo(param);
    }
}
