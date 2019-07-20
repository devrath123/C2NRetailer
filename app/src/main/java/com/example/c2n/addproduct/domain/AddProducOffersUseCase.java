package com.example.c2n.addproduct.domain;

import android.content.Context;

import com.example.c2n.addproduct.data.AddProductRepository;
import com.example.c2n.core.models.ProductDataModel;
import com.example.c2n.core.usecase.UseCase;
import com.example.c2n.core.usecase.UseCaseComposer;

import javax.inject.Inject;

import io.reactivex.Observable;

public class AddProducOffersUseCase extends UseCase<ProductDataModel, String> {

    private AddProductRepository addProductRepository;

    @Inject
    protected AddProducOffersUseCase(UseCaseComposer useCaseComposer, AddProductRepository addProductRepository) {
        super(useCaseComposer);
        this.addProductRepository = addProductRepository;
    }

    @Override
    protected Observable<String> createUseCaseObservable(ProductDataModel productDataModel, Context context) {
        return addProductRepository.addProductOffers(productDataModel);
    }
}
