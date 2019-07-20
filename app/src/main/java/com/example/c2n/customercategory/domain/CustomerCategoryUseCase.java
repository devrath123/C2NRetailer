package com.example.c2n.customercategory.domain;

import android.content.Context;

import com.example.c2n.core.mapper.ListDocumentQuerySnapshotToListCategoryDataModelMapper;
import com.example.c2n.core.models.CategoryDataModel;
import com.example.c2n.core.usecase.UseCase;
import com.example.c2n.core.usecase.UseCaseComposer;
import com.example.c2n.customercategory.data.CustomerCategoryRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by shivani.singh on 17-08-2018.
 */

public class CustomerCategoryUseCase extends UseCase<Void,List<CategoryDataModel>> {

    CustomerCategoryRepository categoryRepository;
    ListDocumentQuerySnapshotToListCategoryDataModelMapper listDocumentQuerySnapshotToListCategoryDataModelMapper;


    @Inject
    protected CustomerCategoryUseCase(UseCaseComposer useCaseComposer,CustomerCategoryRepository categoryRepository,ListDocumentQuerySnapshotToListCategoryDataModelMapper categoryDataModelMapper) {
        super(useCaseComposer);
        this.categoryRepository = categoryRepository;
        this.listDocumentQuerySnapshotToListCategoryDataModelMapper = categoryDataModelMapper;
    }

    @Override
    protected Observable<List<CategoryDataModel>> createUseCaseObservable(Void param, Context context) {
        return categoryRepository.getCategories().map(queryDocumentSnapshots -> listDocumentQuerySnapshotToListCategoryDataModelMapper.mapListDocumentQuerySnapshotToListCategoryDataModelMapper(queryDocumentSnapshots));
    }
}
