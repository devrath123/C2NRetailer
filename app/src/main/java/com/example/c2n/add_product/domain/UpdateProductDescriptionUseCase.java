package com.example.c2n.add_product.domain;

import android.content.Context;

import com.example.c2n.add_product.data.AddProductRepository;
import com.example.c2n.core.SharedPrefManager;
import com.example.c2n.core.model1.ProductDataModel;
import com.example.c2n.core.usecase.UseCase;
import com.example.c2n.core.usecase.UseCaseComposer;
import com.google.firebase.firestore.DocumentReference;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by roshan.nimje on 25-06-2018.
 */

public class UpdateProductDescriptionUseCase extends UseCase<Object[], DocumentReference> {
    private AddProductRepository addProductRepository;

    @Inject
    protected UpdateProductDescriptionUseCase(UseCaseComposer useCaseComposer, AddProductRepository addProductRepository) {
        super(useCaseComposer);
        this.addProductRepository = addProductRepository;
    }

    @Override
    protected Observable<DocumentReference> createUseCaseObservable(Object[] updatedProduct, Context context) {
        SharedPrefManager.Init(context);
        SharedPrefManager.LoadFromPref();
        return addProductRepository.updateProductDescription((ProductDataModel)updatedProduct[0],SharedPrefManager.get_userEmail(),(String) updatedProduct[1]);
    }
}
