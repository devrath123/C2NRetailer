package com.example.c2n.edit_profile.domain;

import android.content.Context;
import android.util.Log;

import com.example.c2n.core.mapper.DocumentToRetailerDataModel;
import com.example.c2n.core.model1.RetailerDataModel;
import com.example.c2n.core.usecase.UseCase;
import com.example.c2n.core.usecase.UseCaseComposer;
import com.example.c2n.edit_profile.data.EditProfileRepository;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by roshan.nimje on 21-05-2018.
 */


public class EditProfileGetRetailerUseCase extends UseCase<RetailerDataModel, RetailerDataModel> {

    EditProfileRepository editProfileRepository;
    DocumentToRetailerDataModel documentToRetailerDataModel;
//    DocumetToUserDataModel documetToUserDataModel;



    @Inject
    protected EditProfileGetRetailerUseCase(UseCaseComposer useCaseComposer, EditProfileRepository editProfileRepository, DocumentToRetailerDataModel documentToRetailerDataModel) {
        super(useCaseComposer);
        this.editProfileRepository = editProfileRepository;
        this.documentToRetailerDataModel = documentToRetailerDataModel;
    }

    @Override
    protected Observable<RetailerDataModel> createUseCaseObservable(RetailerDataModel retailerDataModel, Context context) {
        Log.d("EditProfileGetUser",retailerDataModel.getRetailerID());

        return editProfileRepository.getRetailerDetails(retailerDataModel)
                .map(documentSnapshot -> documentToRetailerDataModel.mapDocumentToUserDataModel(documentSnapshot));
    }


}
