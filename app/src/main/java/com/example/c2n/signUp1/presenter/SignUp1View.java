package com.example.c2n.signUp1.presenter;

/**
 * Created by roshan.nimje on 05-06-2018.
 */

public interface SignUp1View {

    String getUserName();

    String getUserEmail();

    String getUserType();

    void showEmailError(String msg);

    void isUserExist(Boolean bool);

    void validateEmail();

    void showEmailProgress(Boolean show);

    void openPasswordActivity();
}
