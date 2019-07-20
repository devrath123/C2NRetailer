package com.example.c2n.viewshops.domain;

import android.content.Context;

import com.example.c2n.core.SharedPrefManager;
import com.example.c2n.core.mappers.ListDocumentSnapshotToListShopDataModel;
import com.example.c2n.core.models.ShopDataModel;
import com.example.c2n.core.usecase.UseCase;
import com.example.c2n.core.usecase.UseCaseComposer;
import com.example.c2n.viewshops.data.ViewShopsRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by roshan.nimje on 21-05-2018.
 */

public class ViewShopsUseCase extends UseCase<String, List<ShopDataModel>> {

    ViewShopsRepository viewShopsRepository;
    ListDocumentSnapshotToListShopDataModel listDocumentSnapshotToListShopDataModel;

    @Inject
    protected ViewShopsUseCase(UseCaseComposer useCaseComposer, ViewShopsRepository viewShopsRepository, ListDocumentSnapshotToListShopDataModel listDocumentSnapshotToListShopDataModel) {
        super(useCaseComposer);
        this.viewShopsRepository = viewShopsRepository;
        this.listDocumentSnapshotToListShopDataModel = listDocumentSnapshotToListShopDataModel;
    }

    @Override
    protected Observable<List<ShopDataModel>> createUseCaseObservable(String param, Context context) {
        SharedPrefManager.Init(context);
        SharedPrefManager.LoadFromPref();
        return viewShopsRepository.loadShops(SharedPrefManager.get_userDocumentID()).map(documentSnapshot -> listDocumentSnapshotToListShopDataModel.mapListQueryDocumentSnapshopToListShopDataModel(context, documentSnapshot));
    }

//    @Override
//    protected Observable<List<QueryDocumentSnapshot>> createUseCaseObservable(Void param, Context context) {
//        return viewShopsRepository.loadShops().map(queryDocumentSnapshots -> shopsQueryDocumentSnapShopToShopDataModel.queryDocumentSnapShotToShopDatModel(context, queryDocumentSnapshots));
//        return viewShopsRepository.getOffers();
//    }
}
