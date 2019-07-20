package com.example.c2n.signUp1.domain;

import android.content.Context;

import com.example.c2n.core.mapper.DocumentListToCustomerDataModelListMapper;
import com.example.c2n.core.model.DocumentToUserDataModelListMapper;
import com.example.c2n.core.model.UserDataModel;
import com.example.c2n.core.model1.CustomerDataModel;
import com.example.c2n.core.usecase.UseCase;
import com.example.c2n.core.usecase.UseCaseComposer;
import com.example.c2n.signUp1.data.SignUp1Repository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by vipul.singhal on 25-07-2018.
 */

public class ValidateCustomerEmailUseCase extends UseCase<Void,List<CustomerDataModel>>{
    SignUp1Repository signUp1Repository;
    DocumentListToCustomerDataModelListMapper documentListToCustomerDataModelListMapper;

    @Inject
    protected ValidateCustomerEmailUseCase(UseCaseComposer useCaseComposer, SignUp1Repository signUp1Repository,DocumentListToCustomerDataModelListMapper documentListToCustomerDataModelListMapper) {
        super(useCaseComposer);
        this.signUp1Repository = signUp1Repository;
        this.documentListToCustomerDataModelListMapper = documentListToCustomerDataModelListMapper;
    }

    @Override
    protected Observable<List<CustomerDataModel>> createUseCaseObservable(Void param, Context context) {
        return signUp1Repository.getCustomers().map(queryDocumentSnapshots -> documentListToCustomerDataModelListMapper.mapDocumentListToCustomerataModelList(queryDocumentSnapshots));
    }
}
