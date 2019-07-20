package com.example.c2n.nearby_shops.domain;

import android.content.Context;

import com.example.c2n.core.usecase.UseCase;
import com.example.c2n.core.usecase.UseCaseComposer;
import com.example.c2n.customer_home.data.CustomerHomeRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by shivani.singh on 16-08-2018.
 */

public class NearbyShopsLoadAllUserIDsUseCase extends UseCase<Void, List<String>> {

    private CustomerHomeRepository customerHomeRepository;

    @Inject
    protected NearbyShopsLoadAllUserIDsUseCase(UseCaseComposer useCaseComposer, CustomerHomeRepository customerHomeRepository) {
        super(useCaseComposer);
        this.customerHomeRepository = customerHomeRepository;
    }

    @Override
    protected Observable<List<String>> createUseCaseObservable(Void param, Context context) {
        return customerHomeRepository.getAllUserIDs();
    }
}
