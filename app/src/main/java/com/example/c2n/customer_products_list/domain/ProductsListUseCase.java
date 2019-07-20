package com.example.c2n.customer_products_list.domain;

import android.content.Context;

import com.example.c2n.core.model.DocumentToProductDataModelMapper;
import com.example.c2n.core.model.ProductDataModel;
import com.example.c2n.core.usecase.UseCase;
import com.example.c2n.core.usecase.UseCaseComposer;
import com.example.c2n.customer_products_list.data.ProductsListRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by vipul.singhal on 23-05-2018.
 */

public class ProductsListUseCase extends UseCase<Void, List<ProductDataModel>> {

    DocumentToProductDataModelMapper documentToProductDataModelMapper;
    ProductsListRepository productsListRepository;

    @Inject
    protected ProductsListUseCase(UseCaseComposer useCaseComposer, ProductsListRepository productsListRepository, DocumentToProductDataModelMapper documentToProductDataModelMapper) {
        super(useCaseComposer);
        this.productsListRepository = productsListRepository;
        this.documentToProductDataModelMapper = documentToProductDataModelMapper;
    }

    @Override
    protected Observable<List<ProductDataModel>> createUseCaseObservable(Void param, Context context) {
        return productsListRepository.getProducts().map(documentSnapshotList ->documentToProductDataModelMapper.mapDocumentToProductsList(documentSnapshotList));
    }
}
