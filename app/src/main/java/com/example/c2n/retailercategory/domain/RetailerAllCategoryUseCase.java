package com.example.c2n.retailercategory.domain;

import android.content.Context;

import com.example.c2n.core.mapper.ListDocumentQuerySnapshotToListCategoryDataModelMapper;
import com.example.c2n.core.models.CategoryDataModel;
import com.example.c2n.core.usecase.UseCase;
import com.example.c2n.core.usecase.UseCaseComposer;
import com.example.c2n.retailercategory.data.RetailerCategoryRepository;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by roshan.nimje on 18-06-2018.
 */

public class RetailerAllCategoryUseCase extends UseCase<Void, List<CategoryDataModel>> {

    private static final String TAG = RetailerAllCategoryUseCase.class.getSimpleName();
    private RetailerCategoryRepository retailerCategoryRepository;
    //    private DocumentToCategoryDataModelMapper documentToCategoryDataModelMapper;
    ListDocumentQuerySnapshotToListCategoryDataModelMapper listDocumentQuerySnapshotToListCategoryDataModelMapper;

    @Inject
    protected RetailerAllCategoryUseCase(UseCaseComposer useCaseComposer, RetailerCategoryRepository retailerCategoryRepository, ListDocumentQuerySnapshotToListCategoryDataModelMapper listDocumentQuerySnapshotToListCategoryDataModelMapper) {
        super(useCaseComposer);
        this.retailerCategoryRepository = retailerCategoryRepository;
        this.listDocumentQuerySnapshotToListCategoryDataModelMapper = listDocumentQuerySnapshotToListCategoryDataModelMapper;
//        this.documentToCategoryDataModelMapper = documentToCategoryDataModelMapper;
    }


//    @Override
//    protected Observable<List<CategoryDataModel>> createUseCaseObservable(Void param, Context context) {
//        return retailerCategoryRepository.getCategories().map(documentSnapshotList -> documentToCategoryDataModelMapper.mapDocumentToCategorieList(documentSnapshotList));
//    }

    @Override
    protected Observable<List<CategoryDataModel>> createUseCaseObservable(Void param, Context context) {
        return retailerCategoryRepository.getCategories().map(queryDocumentSnapshots -> listDocumentQuerySnapshotToListCategoryDataModelMapper.mapListDocumentQuerySnapshotToListCategoryDataModelMapper(new ArrayList<>(queryDocumentSnapshots)));
    }
}
