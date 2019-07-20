package com.example.c2n.nearby_shops.domain;

import android.content.Context;

import com.example.c2n.core.mappers.ListQueryDocumentSnapshotToListShopDataModel;
import com.example.c2n.core.models.ShopDataModel;
import com.example.c2n.core.usecase.UseCase;
import com.example.c2n.core.usecase.UseCaseComposer;
import com.example.c2n.nearby_shops.data.NearbyShopsRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by shivani.singh on 16-08-2018.
 */

public class NearbyShopsLoadAllShopsUseCase extends UseCase<Void, List<ShopDataModel>> {

    private static final String TAG = NearbyShopsLoadAllShopsUseCase.class.getSimpleName();
    private NearbyShopsRepository nearbyShopsRepository;
    private ListQueryDocumentSnapshotToListShopDataModel listQueryDocumentSnapshotToListShopDataModel;

    @Inject
    protected NearbyShopsLoadAllShopsUseCase(UseCaseComposer useCaseComposer, NearbyShopsRepository nearbyShopsRepository, ListQueryDocumentSnapshotToListShopDataModel listQueryDocumentSnapshotToListShopDataModel) {
        super(useCaseComposer);
        this.nearbyShopsRepository = nearbyShopsRepository;
        this.listQueryDocumentSnapshotToListShopDataModel = listQueryDocumentSnapshotToListShopDataModel;
    }

    @Override
    protected Observable<List<ShopDataModel>> createUseCaseObservable(Void param, Context context) {
        return nearbyShopsRepository.getAllShops().map(queryDocumentSnapshots -> listQueryDocumentSnapshotToListShopDataModel.mapListQueryDocumentSnapshopToListShopDataModel(context, queryDocumentSnapshots));
    }
}
