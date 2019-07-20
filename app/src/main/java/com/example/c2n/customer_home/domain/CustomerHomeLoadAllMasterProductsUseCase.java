package com.example.c2n.customer_home.domain;

import android.content.Context;

import com.example.c2n.core.mappers.ListDocumentSnapshotToListMasterProductDataModel;
import com.example.c2n.core.models.MasterProductDataModel;
import com.example.c2n.core.usecase.UseCase;
import com.example.c2n.core.usecase.UseCaseComposer;
import com.example.c2n.customer_home.data.CustomerHomeRepository;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by shivani.singh on 16-08-2018.
 */

public class CustomerHomeLoadAllMasterProductsUseCase extends UseCase<String, List<MasterProductDataModel>> {

    private CustomerHomeRepository customerHomeRepository;
    private ListDocumentSnapshotToListMasterProductDataModel listDocumentSnapshotToListMasterProductDataModel;

    @Inject
    protected CustomerHomeLoadAllMasterProductsUseCase(UseCaseComposer useCaseComposer, CustomerHomeRepository customerHomeRepository, ListDocumentSnapshotToListMasterProductDataModel listDocumentSnapshotToListMasterProductDataModel) {
        super(useCaseComposer);
        this.customerHomeRepository = customerHomeRepository;
        this.listDocumentSnapshotToListMasterProductDataModel = listDocumentSnapshotToListMasterProductDataModel;
    }

    @Override
    protected Observable<List<MasterProductDataModel>> createUseCaseObservable(String userIDs, Context context) {
        return customerHomeRepository.getAllMasterProducts().map(queryDocumentSnapshots -> listDocumentSnapshotToListMasterProductDataModel.mapListDocumentSnapshotToListMasterProductDataModel(context, queryDocumentSnapshots));
    }
}
