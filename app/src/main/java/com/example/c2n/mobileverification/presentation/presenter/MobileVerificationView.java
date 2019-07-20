package com.example.c2n.mobileverification.presentation.presenter;

import com.google.firebase.auth.PhoneAuthCredential;

/**
 * Created by roshan.nimje on 10-05-2018.
 */

public interface MobileVerificationView {

    void showProgressDialog(String msg);

    void hideProgressDialog();

    void verifyMobileNumber();

    void submitOTP(String opt);

    void signInWithPhoneAuthCredential(PhoneAuthCredential phoneAuthCredential);

    void showToast(String msg);

    String getDocumentID();

    String getMobileNo();

    String getUserType();

    void updateMobileNo();

    void openEditProfile();

}
