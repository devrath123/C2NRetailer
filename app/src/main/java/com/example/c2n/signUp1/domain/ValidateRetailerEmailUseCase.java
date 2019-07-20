package com.example.c2n.signUp1.domain;

import android.content.Context;

import com.example.c2n.core.mapper.DocumentListToRetailerDataModelListMapper;
import com.example.c2n.core.model1.RetailerDataModel;
import com.example.c2n.core.usecase.UseCase;
import com.example.c2n.core.usecase.UseCaseComposer;
import com.example.c2n.signUp1.data.SignUp1Repository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by roshan.nimje on 05-06-2018.
 */

public class ValidateRetailerEmailUseCase extends UseCase<Void, List<RetailerDataModel>> {

    SignUp1Repository signUp1Repository;
    DocumentListToRetailerDataModelListMapper documentListToRetailerDataModelListMapper;

    @Inject
    protected ValidateRetailerEmailUseCase(UseCaseComposer useCaseComposer, SignUp1Repository signUp1Repository, DocumentListToRetailerDataModelListMapper documentListToRetailerDataModelListMapper) {
        super(useCaseComposer);
        this.signUp1Repository = signUp1Repository;
        this.documentListToRetailerDataModelListMapper = documentListToRetailerDataModelListMapper;
    }

    @Override
    protected Observable<List<RetailerDataModel>> createUseCaseObservable(Void param, Context context) {
        return signUp1Repository.getRetailers().map(queryDocumentSnapshots -> documentListToRetailerDataModelListMapper.mapDocumentListToRetailerDataModelList(queryDocumentSnapshots));
    }
}
