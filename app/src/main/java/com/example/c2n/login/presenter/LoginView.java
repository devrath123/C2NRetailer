package com.example.c2n.login.presenter;

import android.content.Intent;

import com.example.c2n.core.model1.CustomerDataModel;

/**
 * Created by vipul.singhal on 10-05-2018.
 */

public interface LoginView {

    void showEmailProgress(Boolean show);

    void isGoogleSigninSuccess(Boolean bool);

    Intent getGoogleResultIntent();

    void showLoginProgress(Boolean bool);

    String getUserType();

    String getCurrentUserEmail();

    String getUserProfilePic();

    String getUserEmail();

    String getUserPassword();

    String getUserName();

    Boolean isGoogleOrFbLogin();

    void isUserLoginSuccess(Boolean bool, Boolean emailException, Boolean passwordException);

    void showEmailFormatError();

    void showPasswordFormatError();

    void showInvalidEmailError();

    void showInvalidPasswordError();

    void isFbLoginSuccess(Boolean bool);

    void navigateUser(Boolean isUserExists, com.example.c2n.core.model1.RetailerDataModel retailerDataModel, CustomerDataModel customerDataModel);

    void setFbCurrentUserEmail(String FbEmail);

    void setFbCurrentUserProfilePic(String FbProfilePic);

    void setUserDocumentId(String documentId);

    void addUserSuccess(Boolean bool);

    void checkIfUserExists();
}
