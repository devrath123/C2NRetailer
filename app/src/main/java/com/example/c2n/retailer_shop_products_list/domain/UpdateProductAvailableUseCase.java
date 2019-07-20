package com.example.c2n.retailer_shop_products_list.domain;

import android.content.Context;

import com.example.c2n.core.SharedPrefManager;
import com.example.c2n.core.usecase.UseCase;
import com.example.c2n.core.usecase.UseCaseComposer;
import com.example.c2n.retailer_shop_products_list.data.RetailerShopProductsRepository;
import com.google.firebase.firestore.DocumentReference;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by vipul.singhal on 21-06-2018.
 */

public class UpdateProductAvailableUseCase extends UseCase<String[], DocumentReference> {
    RetailerShopProductsRepository retailerShopProductsRepository;
//    ProductsQueryDocumentSnapshotToProductDataModelMapper productsQueryDocumentSnapshotToProductDataModelMapper;

    @Inject
    protected UpdateProductAvailableUseCase(UseCaseComposer useCaseComposer, RetailerShopProductsRepository retailerShopProductsRepository) {
        super(useCaseComposer);
        this.retailerShopProductsRepository = retailerShopProductsRepository;
//        this.productsQueryDocumentSnapshotToProductDataModelMapper = productsQueryDocumentSnapshotToProductDataModelMapper;
    }

    @Override
    protected Observable<DocumentReference> createUseCaseObservable(String[] product, Context context) {
        SharedPrefManager.Init(context);
        SharedPrefManager.LoadFromPref();
        return retailerShopProductsRepository.updateProductAvailable(SharedPrefManager.get_userDocumentID(),product[0],product[1]);
//        return retailerShopProductsRepository.loadProducts(selectedShopnCategory).map(queryDocumentSnapshots -> productsQueryDocumentSnapshotToProductDataModelMapper.mapQueryDocumentSnapShotToProductDataModel(queryDocumentSnapshots));
    }
}
