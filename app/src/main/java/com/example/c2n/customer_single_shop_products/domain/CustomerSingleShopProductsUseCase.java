package com.example.c2n.customer_single_shop_products.domain;

import android.content.Context;

import com.example.c2n.core.mapper.ListDocumentQuerySnapshotToListProductDetailsDataModelMapper;
import com.example.c2n.core.models.ProductDetailsDataModel;
import com.example.c2n.core.usecase.UseCase;
import com.example.c2n.core.usecase.UseCaseComposer;
import com.example.c2n.customer_single_shop_products.data.CustomerSingleShopProductsRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

public class CustomerSingleShopProductsUseCase extends UseCase<String[], List<ProductDetailsDataModel>> {

    private CustomerSingleShopProductsRepository customerSingleShopProductsRepository;
    private ListDocumentQuerySnapshotToListProductDetailsDataModelMapper listDocumentQuerySnapshotToListProductDetailsDataModelMapper;

    @Inject
    protected CustomerSingleShopProductsUseCase(UseCaseComposer useCaseComposer, CustomerSingleShopProductsRepository customerSingleShopProductsRepository, ListDocumentQuerySnapshotToListProductDetailsDataModelMapper listDocumentQuerySnapshotToListProductDetailsDataModelMapper) {
        super(useCaseComposer);
        this.customerSingleShopProductsRepository = customerSingleShopProductsRepository;
        this.listDocumentQuerySnapshotToListProductDetailsDataModelMapper = listDocumentQuerySnapshotToListProductDetailsDataModelMapper;
    }

    @Override
    protected Observable<List<ProductDetailsDataModel>> createUseCaseObservable(String[] param, Context context) {
        return customerSingleShopProductsRepository.getAllProducts(param).map(queryDocumentSnapshots -> listDocumentQuerySnapshotToListProductDetailsDataModelMapper.mapListProductsHashmapToListProductDetailsDataModelMapper(queryDocumentSnapshots, param[0],param[1]));
    }
}
