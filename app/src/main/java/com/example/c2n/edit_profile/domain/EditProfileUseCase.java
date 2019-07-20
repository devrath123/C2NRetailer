package com.example.c2n.edit_profile.domain;

import android.content.Context;
import android.util.Log;

import com.example.c2n.core.model1.RetailerDataModel;
import com.example.c2n.core.usecase.UseCase;
import com.example.c2n.core.usecase.UseCaseComposer;
import com.example.c2n.edit_profile.data.EditProfileRepository;
import com.google.firebase.firestore.DocumentReference;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by roshan.nimje on 11-05-2018.
 */

public class EditProfileUseCase extends UseCase<Object[], DocumentReference> {

    EditProfileRepository editProfileRepository;

    @Inject
    protected EditProfileUseCase(UseCaseComposer useCaseComposer, EditProfileRepository editProfileRepository) {
        super(useCaseComposer);
        this.editProfileRepository = editProfileRepository;
    }

    @Override
    protected Observable<DocumentReference> createUseCaseObservable(Object[] user, Context context) {
//        Log.d("DocumentReference",retailerDataModel.getRetailerID());

        return editProfileRepository.updateUser(user);
    }
}