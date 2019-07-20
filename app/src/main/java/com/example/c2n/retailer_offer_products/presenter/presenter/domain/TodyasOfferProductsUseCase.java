package com.example.c2n.retailer_offer_products.presenter.presenter.domain;

import android.content.Context;

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

public class TodyasOfferProductsUseCase extends UseCase<String[], List<ProductDataModel>> {
    OfferProductsRepository offerProductsRepository;
    ProductsQueryDocumentSnapshotToTodaysProductDataModelMapper queryDocumentSnapshotToTodaysProductDataModelMapper;

    @Inject
    protected TodyasOfferProductsUseCase(UseCaseComposer useCaseComposer, OfferProductsRepository offerProductsRepository, ProductsQueryDocumentSnapshotToTodaysProductDataModelMapper queryDocumentSnapshotToTodaysProductDataModelMapper) {
        super(useCaseComposer);
        this.offerProductsRepository = offerProductsRepository;
        this.queryDocumentSnapshotToTodaysProductDataModelMapper = queryDocumentSnapshotToTodaysProductDataModelMapper;
    }

    @Override
    protected Observable<List<ProductDataModel>> createUseCaseObservable(String[] selectedShopnCategory, Context context) {
        return offerProductsRepository.loadProducts(selectedShopnCategory).map(queryDocumentSnapshots -> queryDocumentSnapshotToTodaysProductDataModelMapper.mapQueryDocumentSnapShotToProductDataModel(queryDocumentSnapshots, selectedShopnCategory[2]));
    }
}
