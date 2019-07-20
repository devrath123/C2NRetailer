package com.example.c2n.retailercategory.domain;

import android.content.Context;

import com.example.c2n.core.mapper.ListDocumentQuerySnapshotToListProductDataModelMapper;
import com.example.c2n.core.models.ProductDataModel;
import com.example.c2n.core.usecase.UseCase;
import com.example.c2n.core.usecase.UseCaseComposer;
import com.example.c2n.retailercategory.data.RetailerCategoryRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by roshan.nimje on 18-06-2018.
 */

public class RetailerCategoryUseCase extends UseCase<String[], List<ProductDataModel>> {

    private RetailerCategoryRepository retailerCategoryRepository;
    //    private DocumentToCategoryDataModelMapper documentToCategoryDataModelMapper;
    private ListDocumentQuerySnapshotToListProductDataModelMapper listDocumentQuerySnapshotToListProductDataModelMapper;

    @Inject
    protected RetailerCategoryUseCase(UseCaseComposer useCaseComposer, RetailerCategoryRepository retailerCategoryRepository, ListDocumentQuerySnapshotToListProductDataModelMapper listDocumentQuerySnapshotToListProductDataModelMapper) {
        super(useCaseComposer);
        this.retailerCategoryRepository = retailerCategoryRepository;
        this.listDocumentQuerySnapshotToListProductDataModelMapper = listDocumentQuerySnapshotToListProductDataModelMapper;
//        this.documentToCategoryDataModelMapper = documentToCategoryDataModelMapper;
    }


//    @Override
//    protected Observable<List<CategoryDataModel>> createUseCaseObservable(Void param, Context context) {
//        return retailerCategoryRepository.getCategories().map(documentSnapshotList -> documentToCategoryDataModelMapper.mapDocumentToCategorieList(documentSnapshotList));
//    }

    @Override
    protected Observable<List<ProductDataModel>> createUseCaseObservable(String[] param, Context context) {
        return retailerCategoryRepository.loadProducts(param).map(queryDocumentSnapshots -> listDocumentQuerySnapshotToListProductDataModelMapper.mapQueryDocumentSnapshotToProductDataModel(queryDocumentSnapshots));
    }
}
