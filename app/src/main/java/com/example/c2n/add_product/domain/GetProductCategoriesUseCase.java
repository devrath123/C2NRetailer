package com.example.c2n.add_product.domain;

import android.content.Context;

import com.example.c2n.add_product.data.AddProductRepository;
import com.example.c2n.core.mapper.DocumentListToCategoryDataModelListMapper;
import com.example.c2n.core.model1.CategoryDataModel;
import com.example.c2n.core.usecase.UseCase;
import com.example.c2n.core.usecase.UseCaseComposer;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by vipul.singhal on 29-05-2018.
 */

public class GetProductCategoriesUseCase extends UseCase<Void, List<CategoryDataModel>> {
    private AddProductRepository addProductRepository;
    private DocumentListToCategoryDataModelListMapper documentListToCategoryDataModelListMapper;

    @Inject
    protected GetProductCategoriesUseCase(UseCaseComposer useCaseComposer,DocumentListToCategoryDataModelListMapper documentListToCategoryDataModelListMapper, AddProductRepository addProductRepository) {
        super(useCaseComposer);
        this.documentListToCategoryDataModelListMapper = documentListToCategoryDataModelListMapper;
        this.addProductRepository = addProductRepository;
    }

    @Override
    protected Observable<List<CategoryDataModel>> createUseCaseObservable(Void param, Context context) {
        return addProductRepository.getCategories().map(documentSnapshotList -> documentListToCategoryDataModelListMapper.mapDocumentListToCategoriesList(documentSnapshotList));
    }
}
