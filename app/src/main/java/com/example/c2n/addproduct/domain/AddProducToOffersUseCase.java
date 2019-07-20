package com.example.c2n.addproduct.domain;

import android.content.Context;
import android.util.Log;

import com.example.c2n.addproduct.data.AddProductRepository;
import com.example.c2n.core.SharedPrefManager;
import com.example.c2n.core.models.ProductDataModel;
import com.example.c2n.core.usecase.UseCase;
import com.example.c2n.core.usecase.UseCaseComposer;

import javax.inject.Inject;

import io.reactivex.Observable;

public class AddProducToOffersUseCase extends UseCase<ProductDataModel, Boolean> {

    private static final String TAG = AddProducToOffersUseCase.class.getSimpleName();
    private AddProductRepository addProductRepository;

    @Inject
    protected AddProducToOffersUseCase(UseCaseComposer useCaseComposer, AddProductRepository addProductRepository) {
        super(useCaseComposer);
        this.addProductRepository = addProductRepository;
    }

    @Override
    protected Observable<Boolean> createUseCaseObservable(ProductDataModel productDataModel, Context context) {
        SharedPrefManager.Init(context);
        SharedPrefManager.LoadFromPref();
        Log.d(TAG, "createUseCaseObservable: " + SharedPrefManager.get_userEmail());
        return addProductRepository.addProductToOffers(productDataModel, SharedPrefManager.get_userEmail());
    }
}
