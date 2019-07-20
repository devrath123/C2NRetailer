package com.example.c2n.retailer_shop_products_list.domain;

import android.content.Context;

import com.example.c2n.core.model.ProductsQueryDocumentSnapshotToProductDataModelMapper;
import com.example.c2n.core.models.ProductDataModel;
import com.example.c2n.core.usecase.UseCase;
import com.example.c2n.core.usecase.UseCaseComposer;
import com.example.c2n.retailer_shop_products_list.data.RetailerShopProductsRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by vipul.singhal on 21-06-2018.
 */

public class RetailerShopProductsUseCase extends UseCase<String[], List<ProductDataModel>> {
    RetailerShopProductsRepository retailerShopProductsRepository;
    ProductsQueryDocumentSnapshotToProductDataModelMapper productsQueryDocumentSnapshotToProductDataModelMapper;

    @Inject
    protected RetailerShopProductsUseCase(UseCaseComposer useCaseComposer, RetailerShopProductsRepository retailerShopProductsRepository, ProductsQueryDocumentSnapshotToProductDataModelMapper productsQueryDocumentSnapshotToProductDataModelMapper) {
        super(useCaseComposer);
        this.retailerShopProductsRepository = retailerShopProductsRepository;
        this.productsQueryDocumentSnapshotToProductDataModelMapper = productsQueryDocumentSnapshotToProductDataModelMapper;
    }

    @Override
    protected Observable<List<ProductDataModel>> createUseCaseObservable(String[] selectedShopnCategory, Context context) {
        return retailerShopProductsRepository.loadProducts(selectedShopnCategory).map(queryDocumentSnapshots -> productsQueryDocumentSnapshotToProductDataModelMapper.mapQueryDocumentSnapShotToProductDataModel(queryDocumentSnapshots));
    }
}
