package com.example.c2n.edit_profile.domain;

import android.content.Context;
import android.util.Log;

import com.example.c2n.core.mapper.DocumentToCustomerDataModel;
import com.example.c2n.core.model1.CustomerDataModel;
import com.example.c2n.core.model1.RetailerDataModel;
import com.example.c2n.core.usecase.UseCase;
import com.example.c2n.core.usecase.UseCaseComposer;
import com.example.c2n.edit_profile.data.EditProfileRepository;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by roshan.nimje on 21-05-2018.
 */


public class EditProfileGetCustomerUseCase extends UseCase<CustomerDataModel, CustomerDataModel> {

    EditProfileRepository editProfileRepository;
    DocumentToCustomerDataModel documentToCustomerDataModel;


    @Inject
    protected EditProfileGetCustomerUseCase(UseCaseComposer useCaseComposer, EditProfileRepository editProfileRepository, DocumentToCustomerDataModel documentToCustomerDataModel) {
        super(useCaseComposer);
        this.editProfileRepository = editProfileRepository;
        this.documentToCustomerDataModel = documentToCustomerDataModel;
    }

    @Override
    protected Observable<CustomerDataModel> createUseCaseObservable(CustomerDataModel customerDataModel, Context context) {
        Log.d("EditProfileGetCustomer", customerDataModel.getCustomerID());

        return editProfileRepository.getCustomerDetails(customerDataModel)
                .map(documentSnapshot -> documentToCustomerDataModel.mapDocumentToCustomerDataModel(documentSnapshot));
    }


}
