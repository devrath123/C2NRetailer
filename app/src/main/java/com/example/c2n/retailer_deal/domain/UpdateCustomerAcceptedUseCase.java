package com.example.c2n.retailer_deal.domain;

import android.content.Context;

import com.example.c2n.core.usecase.UseCase;
import com.example.c2n.core.usecase.UseCaseComposer;
import com.example.c2n.retailer_deal.data.RetailerDealRepository;
import com.google.firebase.database.DatabaseReference;

import javax.inject.Inject;

import io.reactivex.Observable;

public class UpdateCustomerAcceptedUseCase extends UseCase<String[], DatabaseReference> {

    private static final String TAG = UpdateCustomerAcceptedUseCase.class.getSimpleName();
    private RetailerDealRepository retailerDealRepository;

    @Inject
    protected UpdateCustomerAcceptedUseCase(UseCaseComposer useCaseComposer, RetailerDealRepository retailerDealRepository) {
        super(useCaseComposer);
        this.retailerDealRepository = retailerDealRepository;
    }

    @Override
    protected Observable<DatabaseReference> createUseCaseObservable(String[] param, Context context) {
        return retailerDealRepository.updateCustomerAccepted(param);
    }
}
