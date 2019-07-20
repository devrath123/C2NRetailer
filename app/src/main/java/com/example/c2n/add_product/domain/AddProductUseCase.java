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
 * Created by vipul.singhal on 13-06-2018.
 */

public class AddProductUseCase extends UseCase<Object[], String> {
    private AddProductRepository addProductRepository;

    @Inject
    protected AddProductUseCase(UseCaseComposer useCaseComposer, AddProductRepository addProductRepository) {
        super(useCaseComposer);
        this.addProductRepository = addProductRepository;
    }

    @Override
    protected Observable<String> createUseCaseObservable(Object[] product, Context context) {
        SharedPrefManager.Init(context);
        SharedPrefManager.LoadFromPref();
        return addProductRepository.addProduct((ProductDataModel) product[0],SharedPrefManager.get_userEmail(),(String)product[1]);
    }
}
