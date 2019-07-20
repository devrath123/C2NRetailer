package com.example.c2n.addproduct.domain;

import android.content.Context;

import com.example.c2n.addproduct.data.AddProductRepository;
import com.example.c2n.core.models.ProductDataModel;
import com.example.c2n.core.usecase.UseCase;
import com.example.c2n.core.usecase.UseCaseComposer;

import javax.inject.Inject;

import io.reactivex.Observable;

public class AddProducOffersExistingUseCase extends UseCase<ProductDataModel,Boolean> {

    private AddProductRepository addProductRepository;

    @Inject
    protected AddProducOffersExistingUseCase(UseCaseComposer useCaseComposer, AddProductRepository addProductRepository) {
        super(useCaseComposer);
        this.addProductRepository = addProductRepository;
    }

    @Override
    protected Observable<Boolean> createUseCaseObservable(ProductDataModel productDataModel, Context context) {
        return addProductRepository.addProductOffersExisting(productDataModel);
    }
}
