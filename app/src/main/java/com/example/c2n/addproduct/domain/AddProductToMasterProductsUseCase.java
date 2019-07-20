package com.example.c2n.addproduct.domain;

import android.content.Context;

import com.example.c2n.addproduct.data.AddProductRepository;
import com.example.c2n.core.models.MasterProductDataModel;
import com.example.c2n.core.usecase.UseCase;
import com.example.c2n.core.usecase.UseCaseComposer;

import javax.inject.Inject;

import io.reactivex.Observable;

public class AddProductToMasterProductsUseCase extends UseCase<MasterProductDataModel, String> {

    private AddProductRepository addProductRepository;

    @Inject
    protected AddProductToMasterProductsUseCase(UseCaseComposer useCaseComposer, AddProductRepository addProductRepository) {
        super(useCaseComposer);
        this.addProductRepository = addProductRepository;
    }

    @Override
    protected Observable<String> createUseCaseObservable(MasterProductDataModel masterProductDataModel, Context context) {
        return addProductRepository.addProductMaster(masterProductDataModel);
    }
}
