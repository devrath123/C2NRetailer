package com.example.c2n.master_list.domain;

import android.content.Context;

import com.example.c2n.core.mappers.QueryDocumentSnapshotToListMasterProductMapper;
import com.example.c2n.core.models.MasterProductDataModel;
import com.example.c2n.core.usecase.UseCase;
import com.example.c2n.core.usecase.UseCaseComposer;
import com.example.c2n.master_list.data.MasterListRepository;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

public class MasterListUseCase extends UseCase<Void, List<MasterProductDataModel>> {

    private MasterListRepository masterListRepository;
    private QueryDocumentSnapshotToListMasterProductMapper queryDocumentSnapshotToListMasterProductMapper;

    @Inject
    protected MasterListUseCase(UseCaseComposer useCaseComposer, MasterListRepository masterListRepository, QueryDocumentSnapshotToListMasterProductMapper queryDocumentSnapshotToListMasterProductMapper) {
        super(useCaseComposer);
        this.masterListRepository = masterListRepository;
        this.queryDocumentSnapshotToListMasterProductMapper = queryDocumentSnapshotToListMasterProductMapper;
    }

    @Override
    protected Observable<List<MasterProductDataModel>> createUseCaseObservable(Void param, Context context) {
        return masterListRepository.getAllProducts().map(queryDocumentSnapshots -> queryDocumentSnapshotToListMasterProductMapper.mapQueryDocumentSnapshotToListMasterProductMapper(queryDocumentSnapshots));
    }
}
