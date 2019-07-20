package com.example.c2n.signup.presenter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.c2n.R;
import com.example.c2n.core.SharedPrefManager;
import com.example.c2n.core.base.BaseActivity;
import com.example.c2n.core.model.UserDataModel;
import com.example.c2n.initial_phase.MainActivity;
import com.example.c2n.mobileverification.presentation.presenter.MobileVerificationActivity;
import com.example.c2n.signup.di.SignUpDI;
import com.example.c2n.signup.util.SignUpPasswordValidator;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignUpActivity extends BaseActivity implements SignUpView {

    @BindView(R.id.et_signup_password)
    EditText userPassword;

    @BindView(R.id.text_string_view)
    TextView textView1;

    String userType, userFullName, userEmail;
    Intent intent;
    String userDocumentId;

    @Inject
    SignUpPasswordValidator passwordValidator;

    @BindView(R.id.bt_signup)
    AppCompatButton signupButton;

    @Inject
    SignUpPresenter signUpPresenter;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_sign_up;
    }

    @Override
    protected void initActivity() {
        ButterKnife.bind(this);
        SignUpDI.getUserSignUpComponent().inject(this);
        signUpPresenter.bind(this, this);

        SharedPrefManager.Init(this);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            userType = bundle.getString("userType");
            userFullName = bundle.getString("userFullName");
            userEmail = bundle.getString("userEmail");
        }

        deactivateSignupButton();
        userPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() != 0) {
                    if (passwordValidator.isValidPassword(getUserPassword())) {
                        Log.d("onTextChanged", "" + count);
                        activateSignupButton();
                    } else {
                        showPasswordError();
                        deactivateSignupButton();
                    }
                } else {
                    deactivateSignupButton();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        Spannable spannable = new SpannableString(textView1.getText());
        String str = spannable.toString();
        int iStart = str.indexOf("Privacy Policy");
        int iEnd = iStart + 14;
        SpannableString ssText = new SpannableString(spannable);

        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                //your code at here.
                Toast.makeText(SignUpActivity.this, "Privacy policy accepted ", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(true);
                ds.setColor(getResources().getColor(R.color.productColor));
            }
        };
        ssText.setSpan(clickableSpan, iStart, iEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView1.setText(ssText);
        textView1.setMovementMethod(LinkMovementMethod.getInstance());
//        textView1.setHighlightColor(Color.TRANSPARENT);
        textView1.setHighlightColor(Color.BLUE);
        textView1.setEnabled(true);


        Spannable spannable1 = new SpannableString(textView1.getText());
        String str1 = spannable.toString();
        int iStart1 = str1.indexOf("Terms of Service");
        int iEnd1 = iStart1 + 16;
        SpannableString ssText1 = new SpannableString(spannable1);

        ClickableSpan clickableSpan1 = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                //your code at here.
                Toast.makeText(SignUpActivity.this, "accepting terms and Service", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(true);
                ds.setColor(getResources().getColor(R.color.productColor));
            }
        };
        ssText1.setSpan(clickableSpan1, iStart1, iEnd1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView1.setText(ssText1);
        textView1.setMovementMethod(LinkMovementMethod.getInstance());
        textView1.setHighlightColor(Color.BLUE);
        textView1.setEnabled(true);


    }

    private void activateSignupButton() {
        Log.d("SignUpDetailsActivity_", "in activateNextButton");
        if (!TextUtils.isEmpty(getUserPassword())) {
            signupButton.setTextColor(getResources().getColor(R.color.white));
            signupButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_background_hollow));
            Log.d("activatesignup", "signup");
        }
    }

    private void deactivateSignupButton() {
        Log.d("SignUpDetailsActivity_", "in deactivateNextButton");
        signupButton.setTextColor(getResources().getColor(R.color.white));
        signupButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.inactive_button_background_hollow));
    }


    @OnClick(R.id.bt_signup)
    @Override
    public void signUpUser() {
        signUpPresenter.validateUser();
//        signUpPresenter.addUserInDatabase();
    }

    @Override
    public void showUserSignUpProgress(Boolean bool) {
        if (bool)
            showProgressDialog("Signing Up...");
        else
            dismissProgressDialog();
    }

    @Override
    public void showUsernameAuthenticationProgress(Boolean bool) {
        if (bool)
            showProgressDialog("Authenticating...");
        else
            dismissProgressDialog();
    }

    @Override
    public void isUsernameAuthenticationSuccess(Boolean success) {

        if (success)
            signUpPresenter.addUserInFirebase();
        else {
            if (progressDialog.isShowing())
                dismissProgressDialog();
//            showUsernameExistsError();
        }
    }

    @Override
    public void showUsernameAuthenticationException() {
        if (progressDialog.isShowing())
            dismissProgressDialog();
        Toast.makeText(this, "Error in Authentication...", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void isUserCreationSuccess(Boolean success, Exception e) {
        if (progressDialog.isShowing())
            dismissProgressDialog();
        if (success) {
            signUpPresenter.addUserInDatabase();
        } else if (e == null) {
            Toast.makeText(this, "Email Id already in use.", Toast.LENGTH_SHORT).show();
            showAlertDialog();
//            finish();
        } else if (e != null)
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();

    }

    public void showAlertDialog() {
        String dialogMessage = "";
        if (userType.equals("R"))
            dialogMessage = "This Email Id is already registered as a Customer. You can use the same Account for both as a Customer or Retailer.";
        else if (userType.equals("C"))
            dialogMessage = "This Email Id is already registered as a Retailer. You can use the same Account for both as a Customer or Retailer.";

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage(dialogMessage)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        startActivity(new Intent(SignUpActivity.this, MainActivity.class));
                        finish();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void isUserSignUpSuccess(Boolean success) {
        if (progressDialog.isShowing())
            dismissProgressDialog();
        if (success) {
            Toast.makeText(this, "SignUp Successfull", Toast.LENGTH_SHORT).show();
            currentUser = new UserDataModel(getUserType(), getUserName(), getUserEmail(), getUserPassword(), "", "", "", "", "");
            SharedPrefManager.Init(this);
            SharedPrefManager.set_userDocumentID(getUserEmail());
            SharedPrefManager.set_userType(getUserType());
            SharedPrefManager.set_userEmail(getUserEmail());
            SharedPrefManager.set_userFullName(getUserName());
            SharedPrefManager.StoreToPref();

            intent = new Intent(this, MobileVerificationActivity.class);
            startActivity(intent);
            finish();
        } else
            Toast.makeText(this, "Error Occured in SignUp", Toast.LENGTH_SHORT).show();
    }

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
    @Override
    public void showPasswordError() {
        userPassword.setError("Password must be 8 characters long and contains atleast 1 char,1 special char and 1 number");
        userPassword.requestFocus();
    }
//
//    @Override
//    public void showPasswordNotMatchingError() {
//        userConfirmPassword.setError("Password didn't match");
//        userConfirmPassword.requestFocus();
//    }
//
//    @Override
//    public void showUsernameExistsError() {
//        userName.setError("Username already exists");
//        userName.requestFocus();
//    }
//
//    @Override
//    public void showEmailExistsError() {
//        userEmail.setError("Email Id already exists");
//        userEmail.requestFocus();
//    }

    @Override
    public String getUserType() {
        return userType;
    }

    @Override
    public String getUserName() {
        return userFullName;
    }

    @Override
    public String getUserEmail() {
        return userEmail;
    }

    @Override
    public String getUserPassword() {
        return userPassword.getText().toString().trim();
    }

    @Override
    public void setAddedUserDocumentId(String documentId) {
        userDocumentId = documentId;
    }

    @Override
    public void showEmailVerificationToast(String msg) {
        showToast(msg);
    }
}