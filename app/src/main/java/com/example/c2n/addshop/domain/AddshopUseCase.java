package com.example.c2n.addshop.domain;

import android.content.Context;

import com.example.c2n.addshop.data.AddshopRepository;
import com.example.c2n.core.models.ShopDataModel;
import com.example.c2n.core.usecase.UseCase;
import com.example.c2n.core.usecase.UseCaseComposer;
import com.google.firebase.firestore.DocumentReference;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by roshan.nimje on 16-05-2018.
 */

public class AddshopUseCase extends UseCase<ShopDataModel, DocumentReference> {

    AddshopRepository addshopRepository;

    @Inject
    protected AddshopUseCase(UseCaseComposer useCaseComposer, AddshopRepository addshopRepository) {
        super(useCaseComposer);
        this.addshopRepository = addshopRepository;
    }

    @Override
    protected Observable<DocumentReference> createUseCaseObservable(ShopDataModel param, Context context) {
        return addshopRepository.addShop(param);
    }
}
