package com.example.c2n.customer_home.domain;

import android.content.Context;

import com.example.c2n.core.SharedPrefManager;
import com.example.c2n.core.models.MasterProductDataModel;
import com.example.c2n.core.usecase.UseCase;
import com.example.c2n.core.usecase.UseCaseComposer;
import com.example.c2n.customer_home.data.CustomerHomeRepository;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by shivani.singh on 16-08-2018.
 */

public class CustomerHomeAddToMylistUseCase extends UseCase<MasterProductDataModel, Boolean> {

    private CustomerHomeRepository customerHomeRepository;

    @Inject
    protected CustomerHomeAddToMylistUseCase(UseCaseComposer useCaseComposer, CustomerHomeRepository customerHomeRepository) {
        super(useCaseComposer);
        this.customerHomeRepository = customerHomeRepository;
    }

    @Override
    protected Observable<Boolean> createUseCaseObservable(MasterProductDataModel masterProductDataModel, Context context) {
        SharedPrefManager.Init(context);
        SharedPrefManager.LoadFromPref();
        return customerHomeRepository.addToMylist(SharedPrefManager.get_userEmail(), masterProductDataModel);
    }
}
