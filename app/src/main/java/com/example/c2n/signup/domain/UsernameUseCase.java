package com.example.c2n.signup.domain;

import android.content.Context;

import com.example.c2n.core.model.DocumentToUserDataModelListMapper;
import com.example.c2n.core.model.UserDataModel;
import com.example.c2n.core.usecase.UseCase;
import com.example.c2n.core.usecase.UseCaseComposer;
import com.example.c2n.signup.data.SignUpRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by vipul.singhal on 11-05-2018.
 */

public class UsernameUseCase extends UseCase<Void, List<UserDataModel>> {
    SignUpRepository signUpRepository;
    DocumentToUserDataModelListMapper documentToUserDataModelListMapper;

    @Inject
    protected UsernameUseCase(UseCaseComposer useCaseComposer, SignUpRepository signUpRepository, DocumentToUserDataModelListMapper documentToUserDataModelListMapper) {
        super(useCaseComposer);
        this.signUpRepository = signUpRepository;
        this.documentToUserDataModelListMapper = documentToUserDataModelListMapper;
    }

    @Override
    protected Observable<List<UserDataModel>> createUseCaseObservable(Void param, Context context) {
        return signUpRepository.getUsers().map(documentSnapshotList -> documentToUserDataModelListMapper.mapDocumentListToUsernameBoolean(documentSnapshotList));
    }
}
