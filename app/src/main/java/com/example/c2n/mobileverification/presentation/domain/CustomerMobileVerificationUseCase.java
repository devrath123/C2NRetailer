package com.example.c2n.mobileverification.presentation.domain;

import android.content.Context;

import com.example.c2n.core.model1.CustomerDataModel;
import com.example.c2n.core.model1.RetailerDataModel;
import com.example.c2n.core.usecase.UseCase;
import com.example.c2n.core.usecase.UseCaseComposer;
import com.example.c2n.mobileverification.presentation.data.MobileVerificationRepository;
import com.google.firebase.firestore.DocumentReference;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by vipul.singhal on 25-07-2018.
 */

public class CustomerMobileVerificationUseCase extends UseCase<CustomerDataModel, DocumentReference> {

    MobileVerificationRepository mobileVerificationRepository;

    @Inject
    protected CustomerMobileVerificationUseCase(UseCaseComposer useCaseComposer, MobileVerificationRepository mobileVerificationRepository) {
        super(useCaseComposer);
        this.mobileVerificationRepository = mobileVerificationRepository;
    }


    @Override
    protected Observable<DocumentReference> createUseCaseObservable(CustomerDataModel param, Context context) {
        return mobileVerificationRepository.updateCustomerMobileNo(param);
    }
}
