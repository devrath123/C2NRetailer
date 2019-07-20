package com.example.c2n.mobileverification.presentation.presenter;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.c2n.R;
import com.example.c2n.core.SharedPrefManager;
import com.example.c2n.core.base.BaseActivity;
import com.example.c2n.customer_home.presenter.CustomerHomeActivity;
import com.example.c2n.mobileverification.presentation.di.MobileVerificationDI;
import com.example.c2n.retailerhome.presenter.RetailerHomeActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MobileVerificationActivity extends BaseActivity implements MobileVerificationView {

    private static final String TAG = MobileVerificationActivity.class.getSimpleName();
    private ProgressDialog progressDialog;

    private String mVerificationId = null;
    private String userDocumentId, userType, userMobileNo;
    private FirebaseAuth mAuth;
    private Boolean AlertDialogFlag = false;
    Boolean flag = false;

    private com.google.firebase.auth.PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

    @BindView(R.id.et_mobile_number)
    EditText editTextMobileNumber;

    @BindView(R.id.et_ot)
    EditText editTextOTP;

    @BindView(R.id.ll_enter_otp)
    LinearLayout linearLayoutEnterOTP;

    @BindView(R.id.bt_verify)
    Button verifyButton;

    @Inject
    MobileVerificationPresenter mobileVerificationPresenter;
    private boolean isVerifyButtonActive = false;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_mobile_number;
    }

    @Override
    protected void initActivity() {

        ButterKnife.bind(this);

        MobileVerificationDI.getMobileVerificationComponent().inject(this);
        mobileVerificationPresenter.bind(this, this);

        getSupportActionBar().setTitle("Mobile Verification");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        SharedPrefManager.Init(this);

        activatateVerifyButton(false);

        linearLayoutEnterOTP.setVisibility(View.INVISIBLE);
        progressDialog = new ProgressDialog(this);

        mAuth = FirebaseAuth.getInstance();

        SharedPrefManager.LoadFromPref();
        userDocumentId = SharedPrefManager.get_userEmail();
        Log.d("MobileVerification_", "" + userDocumentId);
        userType = SharedPrefManager.get_userType();
        Log.d("MobileVerification_", "" + userType);


        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                Log.d(TAG, "onVerificationCompleted:" + phoneAuthCredential);
                signInWithPhoneAuthCredential(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                Log.w(TAG, "onVerificationFailed:" + e.getMessage(), e);
                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                    // ...
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                    // ...
                }
            }

            @Override
            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                Log.d(TAG, "onCodeSent:" + forceResendingToken + " " + s);
                mVerificationId = s;
            }
        };

        editTextMobileNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() >= 10) {
                    isVerifyButtonActive = true;
                    activatateVerifyButton(true);
                } else {
                    isVerifyButtonActive = false;
                    activatateVerifyButton(false);

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void activatateVerifyButton(boolean activate) {
        if (activate) {
            verifyButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        } else {
            verifyButton.setBackgroundColor(getResources().getColor(R.color.inactiveText));
        }
    }

    //
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_skip) {

            if (userType.equals("C"))
                startActivity(new Intent(this, CustomerHomeActivity.class));
            else
                startActivity(new Intent(this, RetailerHomeActivity.class));

            finish();
        } else if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }


        Log.d("skip", "pressed");
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        System.exit(0);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.skip, menu);
        return true;
    }

    @Override
    public void showProgressDialog(String msg) {
        progressDialog.setMessage(msg);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    public void hideProgressDialog() {
        progressDialog.dismiss();
    }

    @OnClick(R.id.bt_verify)
    @Override
    public void verifyMobileNumber() {
        if (isVerifyButtonActive) {
            if (!editTextMobileNumber.getText().toString().trim().equals("")) {
                if (editTextMobileNumber.getText().toString().trim().length() == 10) {
                    mAuth.getCurrentUser().reload();
                    final FirebaseUser user = mAuth.getCurrentUser();
                    if (user.isEmailVerified()) {
                        showProgressDialog("Sit back and Relax while we verify your mobile number");
                        PhoneAuthProvider.getInstance().verifyPhoneNumber("+91" + editTextMobileNumber.getText().toString(), 60, TimeUnit.SECONDS, MobileVerificationActivity.this, mCallbacks);

                        Runnable runnable = new Runnable() {
                            @Override
                            public void run() {
//                        linearLayoutEnterOTP.setVisibility(View.VISIBLE);
                                hideProgressDialog();
                                if (!AlertDialogFlag)
                                    shopAlertDialog();
//                        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(MobileVerificationActivity.this);
//                        View mView = layoutInflaterAndroid.inflate(R.layout.otp_dialog, null);
//                        alertDialogBuilderUserInput = new AlertDialog.Builder(MobileVerificationActivity.this);
//                        alertDialogBuilderUserInput.setView(mView);
//
//                        final EditText editTextOTP = mView.findViewById(R.id.et_otp);
//
//                        alertDialogBuilderUserInput.setCancelable(false)
//                                .setPositiveButton("Submit OTP", new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int which) {
//                                        submitOTP(editTextOTP.getText().toString().trim());
//                                    }
//                                })
//                                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int which) {
//                                    }
//                                });
//                        AlertDialog alertDialogAndroid = alertDialogBuilderUserInput.create();
//                        alertDialogAndroid.show();
                            }
                        };
                        Handler handler = new Handler();
                        handler.postDelayed(runnable, 10000);
                    } else {
                        showToast("Please verify email");
                    }
                } else {
                    showToast("Mobile number should be 10 digit long");
                }
            } else
                showToast("Please enter mobile number");
        }
    }

    private void shopAlertDialog() {

        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(MobileVerificationActivity.this);
        View mView = layoutInflaterAndroid.inflate(R.layout.otp_dialog, null);
        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(MobileVerificationActivity.this);
        alertDialogBuilderUserInput.setView(mView);

        final EditText editTextOTP = mView.findViewById(R.id.et_otp);

        alertDialogBuilderUserInput.setCancelable(false)
                .setPositiveButton("Submit OTP", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        submitOTP(editTextOTP.getText().toString().trim());
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setNeutralButton("Resend OTP", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        PhoneAuthProvider.getInstance().verifyPhoneNumber("+91" + editTextMobileNumber.getText().toString(), 60, TimeUnit.SECONDS, MobileVerificationActivity.this, mCallbacks);
                        shopAlertDialog();
                    }
                });
        AlertDialog alertDialogAndroid = alertDialogBuilderUserInput.create();
        alertDialogAndroid.show();
    }

    //    @OnClick(R.id.submitOTP)
    @Override
    public void submitOTP(String otp) {
        if (!otp.equals("")) {
            if (otp.length() == 6) {
                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, otp);
                signInWithPhoneAuthCredential(credential);
            } else {
                showToast("OTP should be 6 digit long");
                shopAlertDialog();
            }
        } else {
            showToast("Please enter otp");
            shopAlertDialog();
        }

    }

    @Override
    public void signInWithPhoneAuthCredential(PhoneAuthCredential phoneAuthCredential) {
        showProgressDialog("Please wait...");
        mAuth.signInWithCredential(phoneAuthCredential).addOnCompleteListener(MobileVerificationActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    hideProgressDialog();
                    showToast("Success");
                    AlertDialogFlag = true;
                    updateMobileNo();
                } else {
                    hideProgressDialog();
                    showToast("Incorrect OTP");
                    shopAlertDialog();
                }
            }
        });
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(MobileVerificationActivity.this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public String getDocumentID() {
        return userDocumentId;
    }

    @Override
    public String getMobileNo() {
        Log.d("MobileVerificationActiv", "" + editTextMobileNumber.getText().toString().trim());
        return editTextMobileNumber.getText().toString().trim();
    }

    @Override
    public String getUserType() {
        return userType;
    }

    @Override
    public void updateMobileNo() {
        mobileVerificationPresenter.updateMobileNo();
    }

    @Override
    public void openEditProfile() {
        SharedPrefManager.set_userMobileNo(getMobileNo());
        SharedPrefManager.StoreToPref();
//        currentUser.setUserMobileNo(getMobileNo());
        if (SharedPrefManager.get_userType().equals("R"))
            startActivity(new Intent(MobileVerificationActivity.this, RetailerHomeActivity.class));
        else
            startActivity(new Intent(MobileVerificationActivity.this, CustomerHomeActivity.class));
//            Toast.makeText(MobileVerificationActivity.this, "Customer Home Comming Soon", Toast.LENGTH_SHORT).show();
//        intent.putExtra("userType", userType);
//        intent.putExtra("documentid", userDocumentId);
//        intent.putExtra("email", userEmail);
//        startActivity(intent);
        finish();
    }
}
