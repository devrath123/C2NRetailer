package com.example.c2n.login.domain;

import android.content.Context;

import com.example.c2n.core.model.DocumentListToDocumentReferenceIdMapper;
import com.example.c2n.core.usecase.UseCase;
import com.example.c2n.core.usecase.UseCaseComposer;
import com.example.c2n.login.data.LoginRepository;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by vipul.singhal on 14-05-2018.
 */

public class GetUserDocumentIdUseCase extends UseCase<String, String> {
    LoginRepository loginRepository;
    DocumentListToDocumentReferenceIdMapper documentListToDocumentReferenceIdMapper;

    @Inject
    protected GetUserDocumentIdUseCase(UseCaseComposer useCaseComposer, LoginRepository loginRepository, DocumentListToDocumentReferenceIdMapper documentListToDocumentReferenceIdMapper) {
        super(useCaseComposer);
        this.loginRepository = loginRepository;
        this.documentListToDocumentReferenceIdMapper = documentListToDocumentReferenceIdMapper;
    }

    @Override
    protected Observable<String> createUseCaseObservable(String userEmail, Context context) {
        return loginRepository.getUsers().map(documentSnapshotList ->documentListToDocumentReferenceIdMapper.mapDocumentListToDocumentReferenceId(documentSnapshotList,userEmail));
    }
}
