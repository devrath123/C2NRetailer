package com.example.c2n.customer_home.domain;

import android.content.Context;

import com.example.c2n.core.SharedPrefManager;
import com.example.c2n.core.mappers.DocumentSnapshotToListMasterProductDataModel;
import com.example.c2n.core.models.MasterProductDataModel;
import com.example.c2n.core.usecase.UseCase;
import com.example.c2n.core.usecase.UseCaseComposer;
import com.example.c2n.customer_home.data.CustomerHomeRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by shivani.singh on 16-08-2018.
 */

public class CustomerHomeLoadMylistUseCase extends UseCase<Void, List<MasterProductDataModel>> {

    private CustomerHomeRepository customerHomeRepository;
    private DocumentSnapshotToListMasterProductDataModel documentSnapshotToListMasterProductDataModel;

    @Inject
    protected CustomerHomeLoadMylistUseCase(UseCaseComposer useCaseComposer, CustomerHomeRepository customerHomeRepository, DocumentSnapshotToListMasterProductDataModel documentSnapshotToListMasterProductDataModel) {
        super(useCaseComposer);
        this.customerHomeRepository = customerHomeRepository;
        this.documentSnapshotToListMasterProductDataModel = documentSnapshotToListMasterProductDataModel;
    }

    @Override
    protected Observable<List<MasterProductDataModel>> createUseCaseObservable(Void param, Context context) {
        SharedPrefManager.Init(context);
        SharedPrefManager.LoadFromPref();
        return customerHomeRepository.getMylist(SharedPrefManager.get_userEmail()).map(documentSnapshot -> documentSnapshotToListMasterProductDataModel.mapDocumentSnapshotToListMasterProductDataModel(context, documentSnapshot));
    }
}
