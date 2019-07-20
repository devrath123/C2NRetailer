package com.example.c2n.customer_deal.domain;

import android.content.Context;

import com.example.c2n.core.SharedPrefManager;
import com.example.c2n.core.models.CustomerDealDataModel;
import com.example.c2n.core.usecase.UseCase;
import com.example.c2n.core.usecase.UseCaseComposer;
import com.example.c2n.customer_deal.data.CustomerDealRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

public class LoadDealsUseCase extends UseCase<Void, List<CustomerDealDataModel>> {

    private CustomerDealRepository customerDealRepository;

    @Inject
    protected LoadDealsUseCase(UseCaseComposer useCaseComposer, CustomerDealRepository customerDealRepository) {
        super(useCaseComposer);
        this.customerDealRepository = customerDealRepository;
    }

    @Override
    protected Observable<List<CustomerDealDataModel>> createUseCaseObservable(Void param, Context context) {
        SharedPrefManager.Init(context);
        SharedPrefManager.LoadFromPref();
        return customerDealRepository.getCusomerDeal(SharedPrefManager.get_userDocumentID().replace(".", "-"));
    }
}
