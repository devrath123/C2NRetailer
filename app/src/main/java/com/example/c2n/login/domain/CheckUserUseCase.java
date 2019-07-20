package com.example.c2n.login.domain;

import android.content.Context;

import com.example.c2n.core.model.DocumentToUserDataModelListMapper;
import com.example.c2n.core.model.UserDataModel;
import com.example.c2n.core.usecase.UseCase;
import com.example.c2n.core.usecase.UseCaseComposer;
import com.example.c2n.login.data.LoginRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by vipul.singhal on 14-05-2018.
 */

public class CheckUserUseCase extends UseCase<Void, List<UserDataModel>> {

    LoginRepository loginRepository;
    DocumentToUserDataModelListMapper documentToUserDataModelListMapper;

    @Inject
    protected CheckUserUseCase(UseCaseComposer useCaseComposer, LoginRepository loginRepository, DocumentToUserDataModelListMapper documentToUserDataModelListMapper) {
        super(useCaseComposer);
        this.loginRepository = loginRepository;
        this.documentToUserDataModelListMapper = documentToUserDataModelListMapper;
    }

    @Override
    protected Observable<List<UserDataModel>> createUseCaseObservable(Void param, Context context) {
        return loginRepository.getUsers().map(documentSnapshotList -> documentToUserDataModelListMapper.mapDocumentListToUsernameBoolean(documentSnapshotList));
    }
}
