package com.example.c2n.customer_single_product.domain;

import android.content.Context;

import com.example.c2n.core.mappers.QueryDocumentSnapshotToProductDataModelMapper;
import com.example.c2n.core.models.ProductDataModel;
import com.example.c2n.core.usecase.UseCase;
import com.example.c2n.core.usecase.UseCaseComposer;
import com.example.c2n.customer_single_product.data.CustomerSingleProductRepository;

import javax.inject.Inject;

import io.reactivex.Observable;

public class CustomerSingleProductUseCase extends UseCase<String, ProductDataModel> {

    CustomerSingleProductRepository customerSingleProductRepository;
    QueryDocumentSnapshotToProductDataModelMapper queryDocumentSnapshotToProductDataModelMapper;

    @Inject
    protected CustomerSingleProductUseCase(UseCaseComposer useCaseComposer, CustomerSingleProductRepository customerSingleProductRepository, QueryDocumentSnapshotToProductDataModelMapper queryDocumentSnapshotToProductDataModelMapper) {
        super(useCaseComposer);
        this.customerSingleProductRepository = customerSingleProductRepository;
        this.queryDocumentSnapshotToProductDataModelMapper = queryDocumentSnapshotToProductDataModelMapper;
    }

    @Override
    protected Observable<ProductDataModel> createUseCaseObservable(String productID, Context context) {
        return customerSingleProductRepository.getProductOffers(productID).map(documentSnapshot -> queryDocumentSnapshotToProductDataModelMapper.mapQueryDocumentSnapshotToProductDataModelMapper(documentSnapshot));
    }
}
