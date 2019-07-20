package com.example.c2n.add_product.domain;

import android.content.Context;

import com.example.c2n.add_product.data.AddProductRepository;
import com.example.c2n.core.SharedPrefManager;
import com.example.c2n.core.model1.ProductDataModel;
import com.example.c2n.core.usecase.UseCaseComposer;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by vipul.singhal on 30-07-2018.
 */

public class AddProductOfferUseCase {
//    private AddProductRepository addProductRepository;
//
//    @Inject
//    protected AddProductUseCase(UseCaseComposer useCaseComposer, AddProductRepository addProductRepository) {
//        super(useCaseComposer);
//        this.addProductRepository = addProductRepository;
//    }
//
//    @Override
//    protected Observable<String> createUseCaseObservable(Object[] product, Context context) {
//        SharedPrefManager.Init(context);
//        SharedPrefManager.LoadFromPref();
//        return addProductRepository.addProductMaster((ProductDataModel) product[0],SharedPrefManager.get_userEmail(),(String)product[1]);
//    }
}
