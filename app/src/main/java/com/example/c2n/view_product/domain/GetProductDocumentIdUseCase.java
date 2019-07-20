package com.example.c2n.view_product.domain;

import android.content.Context;

import com.example.c2n.core.model.DocumentListToProductDocumentIdMapper;
import com.example.c2n.core.model.ProductDataModel;
import com.example.c2n.core.usecase.UseCase;
import com.example.c2n.core.usecase.UseCaseComposer;
import com.example.c2n.view_product.data.ViewUpdateProductRepository;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by vipul.singhal on 25-06-2018.
 */

public class GetProductDocumentIdUseCase extends UseCase<ProductDataModel, String> {
    ViewUpdateProductRepository viewUpdateProductRepository;
    DocumentListToProductDocumentIdMapper documentListToProductDocumentIdMapper;

    @Inject
    protected GetProductDocumentIdUseCase(UseCaseComposer useCaseComposer, ViewUpdateProductRepository viewUpdateProductRepository, DocumentListToProductDocumentIdMapper documentListToProductDocumentIdMapper) {
        super(useCaseComposer);
        this.viewUpdateProductRepository = viewUpdateProductRepository;
        this.documentListToProductDocumentIdMapper = documentListToProductDocumentIdMapper;
    }

    @Override
    protected Observable<String> createUseCaseObservable(ProductDataModel productDataModel, Context context) {
//        return viewShopRepository.getShopDetails(shopDataModel).map(documentSnapshot -> documentToShopDataModel.mapDocumentToShopDataModel(documentSnapshot));
//        return viewUpdateProductRepository.getProducts(productDataModel).map(documentSnapshotList -> documentListToProductDocumentIdMapper.mapDocumentListToProductDocumentId(documentSnapshotList,productDataModel.getProductName()));
        return null;
    }
}
