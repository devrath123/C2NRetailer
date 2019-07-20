package com.example.c2n.retailer_deal.domain;

import android.content.Context;
import android.util.Log;

import com.example.c2n.core.SharedPrefManager;
import com.example.c2n.core.models.RetailerDealDataModel;
import com.example.c2n.core.usecase.UseCase;
import com.example.c2n.core.usecase.UseCaseComposer;
import com.example.c2n.retailer_deal.data.RetailerDealRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

public class LoadDealsUseCase extends UseCase<Void, List<RetailerDealDataModel>> {

    private static final String TAG = LoadDealsUseCase.class.getSimpleName();
    private RetailerDealRepository retailerDealRepository;

    @Inject
    protected LoadDealsUseCase(UseCaseComposer useCaseComposer, RetailerDealRepository retailerDealRepository) {
        super(useCaseComposer);
        this.retailerDealRepository = retailerDealRepository;
    }

    @Override
    protected Observable<List<RetailerDealDataModel>> createUseCaseObservable(Void param, Context context) {
        SharedPrefManager.Init(context);
        SharedPrefManager.LoadFromPref();
        Log.d(TAG, "createUseCaseObservable: " + SharedPrefManager.get_userDocumentID());
        return retailerDealRepository.getRetailerDeal(SharedPrefManager.get_userDocumentID().replace(".", "-"));
    }
}
