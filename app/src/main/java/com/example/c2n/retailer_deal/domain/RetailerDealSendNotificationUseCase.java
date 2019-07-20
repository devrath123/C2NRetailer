package com.example.c2n.retailer_deal.domain;

import android.content.Context;

import com.example.c2n.core.mappers.DealResponseEntityToDealResponseDataModelMapper;
import com.example.c2n.core.models.DealResponseDataModel;
import com.example.c2n.core.usecase.UseCase;
import com.example.c2n.core.usecase.UseCaseComposer;
import com.example.c2n.retailer_deal.data.RetailerDealRepository;

import javax.inject.Inject;

import io.reactivex.Observable;

public class RetailerDealSendNotificationUseCase extends UseCase<String[], DealResponseDataModel> {

    private RetailerDealRepository retailerDealRepository;
    private DealResponseEntityToDealResponseDataModelMapper dealResponseEntityToDealResponseDataModelMapper;

    @Inject
    protected RetailerDealSendNotificationUseCase(UseCaseComposer useCaseComposer, RetailerDealRepository retailerDealRepository, DealResponseEntityToDealResponseDataModelMapper dealResponseEntityToDealResponseDataModelMapper) {
        super(useCaseComposer);
        this.retailerDealRepository = retailerDealRepository;
        this.dealResponseEntityToDealResponseDataModelMapper = dealResponseEntityToDealResponseDataModelMapper;
    }

    @Override
    protected Observable<DealResponseDataModel> createUseCaseObservable(String[] params, Context context) {
        return retailerDealRepository.sendNotification(params).map(dealResponseEntity -> dealResponseEntityToDealResponseDataModelMapper.mapEntityToData(dealResponseEntity));
    }
}
