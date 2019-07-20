package com.example.c2n.customer_offer_cards.domain;

import android.content.Context;

import com.example.c2n.core.mapper.DocumentListToOfferDetailsDataModelListMapper;
import com.example.c2n.core.mapper.DocumentSnapshotListToProductDataModelListMapper;
import com.example.c2n.core.model1.OfferDetailsDataModel;
import com.example.c2n.core.model1.OfferProductDataModel;
import com.example.c2n.core.model1.ProductDataModel;
import com.example.c2n.core.usecase.UseCase;
import com.example.c2n.core.usecase.UseCaseComposer;
import com.example.c2n.customer_offer_cards.data.CustomerOfferRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by shivani.singh on 28-08-2018.
 */

public class GetOfferedProductsUseCase extends UseCase<OfferProductDataModel, ProductDataModel> {

    CustomerOfferRepository customerOfferRepository;
    DocumentSnapshotListToProductDataModelListMapper documentSnapshotListToProductDataModelListMapper;

    @Inject
    protected GetOfferedProductsUseCase(UseCaseComposer useCaseComposer, CustomerOfferRepository customerOfferRepository, DocumentSnapshotListToProductDataModelListMapper documentSnapshotListToProductDataModelListMapper) {
        super(useCaseComposer);
        this.customerOfferRepository = customerOfferRepository;
        this.documentSnapshotListToProductDataModelListMapper = documentSnapshotListToProductDataModelListMapper;
    }

    @Override
    protected Observable<ProductDataModel> createUseCaseObservable(OfferProductDataModel offeredProducts, Context context) {
        return customerOfferRepository.getOfferedProducts(offeredProducts).map(documentSnapshot -> documentSnapshotListToProductDataModelListMapper.mapDocumentListToProductsList(documentSnapshot));
    }
}
