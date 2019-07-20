package com.example.c2n.offer_cards_list.domain;

import android.content.Context;

import com.example.c2n.core.SharedPrefManager;
import com.example.c2n.core.mappers.DocumentListToOfferDataModelListMapper;
import com.example.c2n.core.models.OfferDataModel;
import com.example.c2n.core.usecase.UseCase;
import com.example.c2n.core.usecase.UseCaseComposer;
import com.example.c2n.offer_cards_list.data.OffersRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by vipul.singhal on 02-07-2018.
 */

public class GetOfferCardListUseCase extends UseCase<Void, List<OfferDataModel>> {
    OffersRepository  offersRepository;
    DocumentListToOfferDataModelListMapper documentListToOfferDataModelListMapper;

    @Inject
    protected GetOfferCardListUseCase(UseCaseComposer useCaseComposer, OffersRepository offersRepository, DocumentListToOfferDataModelListMapper documentListToOfferDataModelListMapper) {
        super(useCaseComposer);
        this.offersRepository = offersRepository;
        this.documentListToOfferDataModelListMapper = documentListToOfferDataModelListMapper;
    }

    @Override
    protected Observable<List<OfferDataModel>> createUseCaseObservable(Void param, Context context) {
        SharedPrefManager.Init(context);
        SharedPrefManager.LoadFromPref();
        return offersRepository.getOfferCards(SharedPrefManager.get_userDocumentID()).map(documentSnapshotList -> documentListToOfferDataModelListMapper.mapDocumentListToOfferCardsList(documentSnapshotList));
    }

}
