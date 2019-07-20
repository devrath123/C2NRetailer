package com.example.c2n.signup.presenter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.c2n.core.model.UserDataModelListToUsernameViewModelMapper;
import com.example.c2n.core.model1.CustomerDataModel;
import com.example.c2n.core.model1.RetailerDataModel;
import com.example.c2n.signup.domain.CustomerSignUpUseCase;
import com.example.c2n.signup.domain.RetailerSignUpUseCase;
import com.example.c2n.signup.domain.SignUpUseCase;
import com.example.c2n.signup.domain.UsernameUseCase;
import com.example.c2n.signup.util.SignUpPasswordValidator;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;

import javax.inject.Inject;

/**
 * Created by vipul.singhal on 10-05-2018.
 */

public class SignUpPresenter {

    SignUpPasswordValidator signUpPasswordValidator;
    SignUpUseCase signUpUseCase;
    CustomerSignUpUseCase customerSignUpUseCase;
    RetailerSignUpUseCase retailerSignUpUseCase;
    UsernameUseCase usernameUseCase;
    SignUpView signUpView;
    Context context;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Inject
    SignUpPresenter(SignUpUseCase signUpUseCase, RetailerSignUpUseCase retailerSignUpUseCase, CustomerSignUpUseCase customerSignUpUseCase, UsernameUseCase usernameUseCase, UserDataModelListToUsernameViewModelMapper userDataModelListToUsernameViewModelMapper, SignUpPasswordValidator signUpPasswordValidator) {
        this.signUpUseCase = signUpUseCase;
        this.retailerSignUpUseCase = retailerSignUpUseCase;
        this.customerSignUpUseCase = customerSignUpUseCase;
        this.usernameUseCase = usernameUseCase;
        this.signUpPasswordValidator = signUpPasswordValidator;
    }

    public void bind(SignUpView signUpView, Context context) {
        this.signUpView = signUpView;
        this.context = context;
    }

    public void validateUser() {
//        if (signUpPasswordValidator.isValidUsername(signUpView.getUserName())) {
//            if (signUpPasswordValidator.isValidEmail(signUpView.getUserEmail())) {
        if (signUpPasswordValidator.isValidPassword(signUpView.getUserPassword())) {
//            if (signUpPasswordValidator.isPasswordMatched(signUpView.getUserPassword(), signUpView.getUserConfirmPassword())) {

//                        isUsernameUnique();
            addUserInFirebase();
//
//            } else
//                signUpView.showPasswordNotMatchingError();
        } else
            signUpView.showPasswordError();
//            } else
//                signUpView.showEmailError();
//        } else
//            signUpView.showUsernameError();
    }

    public void addUserInFirebase() {
        signUpView.showUserSignUpProgress(true);
        mAuth.createUserWithEmailAndPassword(signUpView.getUserEmail(), signUpView.getUserPassword())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            Log.d("CreateUser:success", user.toString());
                            signUpView.isUserCreationSuccess(true, null);
                            verifyEmail();
                        } else {
                            Log.d("CreateUser(Email):fail", task.getException().toString());
                            if (task.getException().getMessage().equals("The email address is already in use by another account.")) {
//                                signUpView.showEmailExistsError();
                                signUpView.isUserCreationSuccess(false, null);
                            } else
                                signUpView.isUserCreationSuccess(false, task.getException());
                        }
                    }
                });
    }

    public void verifyEmail() {
        FirebaseUser user = mAuth.getCurrentUser();
        user.sendEmailVerification()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            signUpView.showEmailVerificationToast("Please check for email verfication");
                        } else {
                        }
                    }
                });

    }

    @SuppressLint("RxLeakedSubscription")
    public void addUserInDatabase() {
        if (signUpView.getUserType().equals("R")) {
            RetailerDataModel retailerDataModel = new RetailerDataModel();
            retailerDataModel.setRetailerID(signUpView.getUserEmail());
            retailerDataModel.setRetailerName(signUpView.getUserName());
            retailerSignUpUseCase.execute(retailerDataModel, context)
                    .doOnSubscribe(disposable -> signUpView.showUserSignUpProgress(true))
                    .subscribe(this::handleResponse, throwable -> handleError(throwable));
        } else if (signUpView.getUserType().equals("C")) {
            CustomerDataModel customerDataModel = new CustomerDataModel();
            customerDataModel.setCustomerID(signUpView.getUserEmail());
            customerDataModel.setCustomerName(signUpView.getUserName());
            customerSignUpUseCase.execute(customerDataModel, context)
                    .doOnSubscribe(disposable -> signUpView.showUserSignUpProgress(true))
                    .subscribe(this::handleResponse, throwable -> handleError(throwable));
        }
//        signUpUseCase.execute(new UserDataModel(signUpView.getUserType(), signUpView.getUserName(), signUpView.getUserEmail(), signUpView.getUserPassword(), "", "", "", "", ""), context)
//                .doOnSubscribe(disposable ->
//                        signUpView.showUserSignUpProgress(true))
//                .subscribe(this::handleResponse, throwable -> handleError(throwable));
    }

    public void handleResponse(DocumentReference documentReference) {
        Log.d("Document_refersnce:..", "............  " + documentReference + documentReference.getId());
        signUpView.setAddedUserDocumentId(documentReference.getId());
        signUpView.isUserSignUpSuccess(true);
    }

    public void handleError(Throwable e) {
        Log.d("error...", "..." + e.getMessage());
        signUpView.isUserSignUpSuccess(false);
    }
}
