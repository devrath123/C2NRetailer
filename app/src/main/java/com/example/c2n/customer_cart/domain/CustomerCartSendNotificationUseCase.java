package com.example.c2n.customer_cart.domain;

import android.content.Context;

import com.example.c2n.core.mappers.DealResponseEntityToDealResponseDataModelMapper;
import com.example.c2n.core.models.DealResponseDataModel;
import com.example.c2n.core.usecase.UseCase;
import com.example.c2n.core.usecase.UseCaseComposer;
import com.example.c2n.customer_cart.data.CustomerCartRepository;

import javax.inject.Inject;

import io.reactivex.Observable;

public class CustomerCartSendNotificationUseCase extends UseCase<String[], DealResponseDataModel> {

    private CustomerCartRepository customerCartRepository;
    private DealResponseEntityToDealResponseDataModelMapper dealResponseEntityToDealResponseDataModelMapper;

    @Inject
    protected CustomerCartSendNotificationUseCase(UseCaseComposer useCaseComposer, CustomerCartRepository customerCartRepository, DealResponseEntityToDealResponseDataModelMapper dealResponseEntityToDealResponseDataModelMapper) {
        super(useCaseComposer);
        this.customerCartRepository = customerCartRepository;
        this.dealResponseEntityToDealResponseDataModelMapper = dealResponseEntityToDealResponseDataModelMapper;
    }

    @Override
    protected Observable<DealResponseDataModel> createUseCaseObservable(String[] params, Context context) {
        return customerCartRepository.sendNotification(params).map(dealResponseEntity -> dealResponseEntityToDealResponseDataModelMapper.mapEntityToData(dealResponseEntity));
    }
}
