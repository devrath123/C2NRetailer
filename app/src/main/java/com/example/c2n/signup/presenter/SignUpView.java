package com.example.c2n.signup.presenter;

/**
 * Created by vipul.singhal on 10-05-2018.
 */

public interface SignUpView {

    void signUpUser();

    void showUserSignUpProgress(Boolean bool);

    void showUsernameAuthenticationProgress(Boolean bool);

    void isUsernameAuthenticationSuccess(Boolean bool);

    void showUsernameAuthenticationException();

    void isUserCreationSuccess(Boolean bool, Exception e);

    void isUserSignUpSuccess(Boolean bool);

//    void showUsernameError();
//
//    void showEmailError();
//
//    void showPasswordError();
//
//    void showPasswordNotMatchingError();
//
//    void showUsernameExistsError();
//
//    void showEmailExistsError();

    //    @Override
//    public void showUsernameError() {
//        userName.setError("UserName must be unique and don't have Whitespace");
//        userName.requestFocus();
//    }
//
//    @Override
//    public void showEmailError() {
//        userEmail.setError("Enter valid Email Id");
//        userEmail.requestFocus();
//    }
//
    void showPasswordError();

    String getUserType();

    String getUserName();

    String getUserEmail();

    String getUserPassword();

//    String getUserConfirmPassword();

    void setAddedUserDocumentId(String documentId);

    void showEmailVerificationToast(String msg);
}
