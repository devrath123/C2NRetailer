package com.example.c2n.retailerhome.domain;

import android.content.Context;

import com.example.c2n.core.model.RetailerDataModel;
import com.example.c2n.core.model.RetailerDataModelMapper;
import com.example.c2n.core.usecase.UseCase;
import com.example.c2n.core.usecase.UseCaseComposer;
import com.example.c2n.retailerhome.data.RetailerHomeRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by shivani.singh on 21-06-2018.
 */

public class RetailerHomeUseCase extends UseCase<Void, List<RetailerDataModel>> {

    RetailerDataModelMapper retailerDataModelMapper;
    RetailerHomeRepository retailerHomeRepository;

    @Inject
    protected RetailerHomeUseCase(UseCaseComposer useCaseComposer, RetailerHomeRepository retailerHomeRepository, RetailerDataModelMapper retailerDataModelMapper) {
        super(useCaseComposer);
        this.retailerHomeRepository = retailerHomeRepository;
        this.retailerDataModelMapper = retailerDataModelMapper;
    }

    @Override
    protected Observable<List<RetailerDataModel>> createUseCaseObservable(Void param, Context context) {
      return retailerHomeRepository.getRetailerList().map(documentSnapshotList -> retailerDataModelMapper.mapDocumentToRetailerList(documentSnapshotList));
//        return null;
    }

}
