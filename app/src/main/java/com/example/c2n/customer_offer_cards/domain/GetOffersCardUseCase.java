package com.example.c2n.customer_offer_cards.domain;

import android.content.Context;

import com.example.c2n.core.mapper.DocumentListToOfferDataModelListMapper;
import com.example.c2n.core.mapper.DocumentListToOfferDetailsDataModelListMapper;
import com.example.c2n.core.model1.OfferDataModel;
import com.example.c2n.core.model1.OfferDetailsDataModel;
import com.example.c2n.core.usecase.UseCase;
import com.example.c2n.core.usecase.UseCaseComposer;
import com.example.c2n.customer_offer_cards.data.CustomerOfferRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by shivani.singh on 28-08-2018.
 */

public class GetOffersCardUseCase extends UseCase<String, List<OfferDetailsDataModel>> {

    CustomerOfferRepository customerOfferRepository;
    DocumentListToOfferDetailsDataModelListMapper documentListToOfferDetailsDataModelListMapper;

    @Inject
    protected GetOffersCardUseCase(UseCaseComposer useCaseComposer, CustomerOfferRepository customerOfferRepository, DocumentListToOfferDetailsDataModelListMapper documentListToOfferDetailsDataModelListMapper) {
        super(useCaseComposer);
        this.customerOfferRepository = customerOfferRepository;
        this.documentListToOfferDetailsDataModelListMapper = documentListToOfferDetailsDataModelListMapper;
    }

    @Override
    protected Observable<List<OfferDetailsDataModel>> createUseCaseObservable(String retailerID, Context context) {
//        return customerOfferRepository.getAllOffers(retailerID).map(documentSnapshotList -> documentListToOfferDetailsDataModelListMapper.mapDocumentListToOfferCardsList(documentSnapshotList, retailerID));
    return null;
    }
}
