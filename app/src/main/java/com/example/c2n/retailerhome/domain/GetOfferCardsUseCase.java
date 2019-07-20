package com.example.c2n.retailerhome.domain;

import android.content.Context;

import com.example.c2n.core.SharedPrefManager;
import com.example.c2n.core.mapper.DocumentListToOfferDataModelListMapper;
import com.example.c2n.core.models.OfferDataModel;
import com.example.c2n.core.usecase.UseCase;
import com.example.c2n.core.usecase.UseCaseComposer;
import com.example.c2n.retailerhome.data.RetailerHomeRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by vipul.singhal on 02-07-2018.
 */

public class GetOfferCardsUseCase extends UseCase<Void, List<OfferDataModel>> {
    RetailerHomeRepository retailerHomeRepository;
    DocumentListToOfferDataModelListMapper documentListToOfferDataModelListMapper;

    @Inject
    protected GetOfferCardsUseCase(UseCaseComposer useCaseComposer, RetailerHomeRepository retailerHomeRepository, DocumentListToOfferDataModelListMapper documentListToOfferDataModelListMapper) {
        super(useCaseComposer);
        this.retailerHomeRepository = retailerHomeRepository;
        this.documentListToOfferDataModelListMapper = documentListToOfferDataModelListMapper;
    }

    @Override
    protected Observable<List<OfferDataModel>> createUseCaseObservable(Void param, Context context) {
        SharedPrefManager.Init(context);
        SharedPrefManager.LoadFromPref();
        return retailerHomeRepository.getOfferCards(SharedPrefManager.get_userDocumentID()).map(documentSnapshotList -> documentListToOfferDataModelListMapper.mapDocumentListToOfferCardsList(documentSnapshotList));
    }

}
