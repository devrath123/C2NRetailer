package com.example.c2n.retailer_offer_products.presenter.presenter.domain;

import android.content.Context;

import com.example.c2n.core.model.DocumetToProductDataModel;
import com.example.c2n.core.model.ProductDataModel;
import com.example.c2n.core.model.ProductsQueryDocumentSnapshotToTodaysProductDataModelMapper;
import com.example.c2n.core.usecase.UseCase;
import com.example.c2n.core.usecase.UseCaseComposer;
import com.example.c2n.retailer_offer_products.presenter.presenter.data.OfferProductsRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by vipul.singhal on 21-06-2018.
 */

public class GetDiscountUseCase extends UseCase<String[], ProductDataModel> {
    OfferProductsRepository offerProductsRepository;
    DocumetToProductDataModel documetToProductDataModel;

    @Inject
    protected GetDiscountUseCase(UseCaseComposer useCaseComposer, OfferProductsRepository offerProductsRepository, DocumetToProductDataModel documetToProductDataModel) {
        super(useCaseComposer);
        this.offerProductsRepository = offerProductsRepository;
        this.documetToProductDataModel = documetToProductDataModel;
    }

    @Override
    protected Observable<ProductDataModel> createUseCaseObservable(String[] userandfferID, Context context) {
//        return offerProductsRepository.loadProducts(selectedShopnCategory).map(queryDocumentSnapshots -> queryDocumentSnapshotToTodaysProductDataModelMapper.mapQueryDocumentSnapShotToProductDataModel(queryDocumentSnapshots, selectedShopnCategory[2]));
        return offerProductsRepository.getDiscount(userandfferID).map(documentSnapshot -> documetToProductDataModel.mapDocumentToProductDataModel(documentSnapshot));
    }
}
