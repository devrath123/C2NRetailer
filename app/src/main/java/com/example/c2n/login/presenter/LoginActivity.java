package com.example.c2n.login.presenter;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.c2n.R;
import com.example.c2n.core.SharedPrefManager;
import com.example.c2n.core.base.BaseActivity;
import com.example.c2n.core.model.UserDataModel;
import com.example.c2n.core.model1.CustomerDataModel;
import com.example.c2n.customer_home.presenter.CustomerHomeActivity;
import com.example.c2n.login.di.LoginDI;
import com.example.c2n.login.util.LoginValidator;
import com.example.c2n.mobileverification.presentation.presenter.MobileVerificationActivity;
import com.example.c2n.retailerhome.presenter.RetailerHomeActivity;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity implements LoginView {

    private String userType = "";

    @BindView(R.id.text_login_type)
    TextView loginTypeText;

    @BindView(R.id.layout_customer_view)
    LinearLayout customerLayoutView;

    @BindView(R.id.et_login_username)
    EditText userEmail;

    @BindView(R.id.et_login_password)
    EditText userPassword;

    @BindView(R.id.bt_login)
    AppCompatButton appCompatButtonLogin;

    @BindView(R.id.login_button)
    LoginButton loginButton;

    @BindView(R.id.email_progress_bar)
    ProgressBar emailProgressBar;

    GoogleSignInOptions gso;
    GoogleSignInClient googleSignInClient;
    Intent googleResultIntent;
    private static final int RC_SIGN_IN = 9001;

    CallbackManager callbackManager;
    String userDocumentId;
    Intent intent;
    Boolean isLogInButtonActive = false;
    LoginValidator loginValidator;

    @Inject
    LoginPresenter loginPresenter;
    String FbCurrentUserEmail;
    String FbCurrentUserProfilePicUrl;

    String signinMethod = "";

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initActivity() {
        ButterKnife.bind(this);
        LoginDI.getUserLoginComponent().inject(this);
        loginPresenter.bind(this, this);
        loginValidator = new LoginValidator();

        deactivateLoginButton();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            userType = bundle.getString("userType");
        }
        if (userType.equals("C"))
            customerLayoutView.setVisibility(View.VISIBLE);
        else {
            loginTypeText.setText("Retailer Log In");
            customerLayoutView.setVisibility(View.GONE);
        }

        SharedPrefManager.Init(this);

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(this, gso);


        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

//        loginButton.setBackgroundResource(R.drawable.fb_button);
//        loginButton.setBackgroundColor(getResources().getColor(R.color.loginColor));

//        loginButton.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);

        callbackManager = CallbackManager.Factory.create();

        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.example.c2n",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.d("KeyHash:Na", "" + e.getMessage());
        } catch (NoSuchAlgorithmException e) {
            Log.d("KeyHash:No", "" + e.getMessage());
        }

        SharedPrefManager.LoadFromPref();
        if (!SharedPrefManager.get_userEmail().equals("")) {

            navigateUser(true, null, null);
        }

        userEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (s.length() != 0) {
                    Log.d("onTextChangedEmail", "" + s.length());
                    showEmailProgress(true);
                    if (loginValidator.isValidEmail(getUserEmail())) {
                        Log.d("onTextChangedEmail", "in isValidEmail");
                        showEmailProgress(false);
                        activateLoginButton();
                    } else {
                        showInvalidEmailError();
                        deactivateLoginButton();
                    }
                } else {
                    showEmailProgress(true);
                    deactivateLoginButton();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        userPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() != 0) {
                    Log.d("onTextChangedpassword", "" + count);
                    activateLoginButton();
//                    userSignIn();
                } else {
                    deactivateLoginButton();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    public void validateEmail() {
        loginPresenter.validateUser();
    }

    @Override
    public void showEmailProgress(Boolean show) {
        if (show)
            emailProgressBar.setVisibility(View.VISIBLE);
        if (!show) {
            activateLoginButton();
            emailProgressBar.setVisibility(View.GONE);
        }
    }

    private void activateLoginButton() {
        isLogInButtonActive = true;
        if (!TextUtils.isEmpty(userEmail.getText().toString().trim()) && !TextUtils.isEmpty(userPassword.getText().toString().trim())) {
            emailProgressBar.setVisibility(View.GONE);
            appCompatButtonLogin.setTextColor(getResources().getColor(R.color.white));
            appCompatButtonLogin.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_background_hollow));
            Log.d("activateLoginButton", "activatelogin");
        }
    }


    private void deactivateLoginButton() {
        isLogInButtonActive = false;
        appCompatButtonLogin.setTextColor(getResources().getColor(R.color.white));
        appCompatButtonLogin.setBackgroundDrawable(getResources().getDrawable(R.drawable.inactive_button_background_hollow));
        Log.d("deactivateLoginButton", "deactivatelogin");

    }

//    @OnClick(R.id.radio_bt_customer)
//    public void setCustomerLoginView() {
//        customerLayoutView.setVisibility(View.VISIBLE);
////        isUserRetailer = false;
//    }
//
//    @OnClick(R.id.radio_bt_retailer)
//    public void setRetailerLoginView() {
//        customerLayoutView.setVisibility(View.INVISIBLE);
////        isUserRetailer = true;
//    }

    @OnClick(R.id.bt_login)
    public void userSignIn() {
        signinMethod = "EmailPassword";
        loginPresenter.validateUser();
    }

    @OnClick(R.id.bt_google_login)
    public void googleSignin() {
        signinMethod = "Google";
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @OnClick(R.id.login_button)
    public void facebookSignin() {
        signinMethod = "Facebook";
        loginPresenter.handleFacebookSignInResult(loginButton, callbackManager);
    }

//    @OnClick(R.id.tv_signup)
//    public void userSignUp() {
//        startActivity(new Intent(LoginActivity.this, SignUpDetailsActivity.class));
//    }

    @Override
    public void isGoogleSigninSuccess(Boolean success) {
        dismissProgressDialog();
        if (success) {

            showToast("Login Successfull");
            startActivity(new Intent(this, MobileVerificationActivity.class));
            finish();
            finish();
        } else
            showToast("An error occured...Please Retry");
    }

    @Override
    public Intent getGoogleResultIntent() {
        return googleResultIntent;
    }

    @Override
    public void showLoginProgress(Boolean show) {
        if (show) {
            showProgressDialog("Signing In....");
        } else
            dismissProgressDialog();
    }

    @Override
    public String getUserType() {
        return userType;
    }

    @Override
    public String getCurrentUserEmail() {
        if (!getUserEmail().equals("")) {
            return getUserEmail();

        } else {
            GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
            if (account != null)
                return account.getEmail();
            else
                return FbCurrentUserEmail;
        }
    }

    @Override
    public String getUserProfilePic() {

        if (signinMethod.equals("Google")) {
            GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
            if (account != null) {
                Log.d("google_user_pic", account.getPhotoUrl().toString());
                return account.getPhotoUrl().toString();
            }
        }
        if (signinMethod.equals("Facebook")) {
            if (FbCurrentUserProfilePicUrl != null) {
                Log.d("fb_user_pic", FbCurrentUserProfilePicUrl);
                return FbCurrentUserProfilePicUrl;
            }
        }
        if (currentUser != null && currentUser.getUserProfilePicUrl() != null) {
            Log.d("curent_user_pic", currentUser.getUserProfilePicUrl());
            return currentUser.getUserProfilePicUrl();
        }

        return "";
    }

    @Override
    public String getUserEmail() {
        if (signinMethod.equals("EmailPassword"))
            return userEmail.getText().toString().trim();
        if (signinMethod.equals("Google")) {
            GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
            if (account != null)
                return account.getEmail();
        }
        if (signinMethod.equals("Facebook"))
            return FbCurrentUserEmail;
        return "";
    }

    @Override
    public String getUserPassword() {
        return userPassword.getText().toString();
    }

    @Override
    public String getUserName() {
        if (signinMethod.equals("Google")) {
            GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
            if (account != null)
                return account.getDisplayName();
        }
        return "";
    }

    @Override
    public Boolean isGoogleOrFbLogin() {
        if (signinMethod.equals("Google") || signinMethod.equals("Facebook"))
            return true;
        if (signinMethod.equals("EmailPassword"))
            return false;
        return true;
    }

    @Override
    public void isUserLoginSuccess(Boolean success, Boolean emailException, Boolean
            passwordException) {
        if (success) {
            showToast("Login Successfull");
//            startActivity(new Intent(this, MainActivity.class));
//            finish();
            if (userType.equals("C")) {
                SharedPrefManager.set_userDocumentID(userEmail.getText().toString());
                SharedPrefManager.StoreToPref();
                loginPresenter.getCustomerInfo();
                //                loginPresenter.isUserExistsInDatabase();
            } else if (userType.equals("R")) {

                SharedPrefManager.set_retailerDocumentID(userEmail.getText().toString());
                SharedPrefManager.StoreToPref();
                loginPresenter.getRetailerInfo();

            }
        } else {
            dismissProgressDialog();
            if (emailException) {
                showToast("No such User exists.");
//                Snackbar snackbar = Snackbar
//                        .make(new CoordinatorLayout(this), "No such User exists.", Snackbar.LENGTH_LONG);
//
//                snackbar.show();
            } else if (passwordException == true)
                showToast("Email Id or Password is incorrect");
            else
                showToast("An error occured...Please Retry.");
        }
    }

    @Override
    public void showEmailFormatError() {
        userEmail.setError("Enter valid Email Id");
        userEmail.requestFocus();
    }

    @Override
    public void showPasswordFormatError() {
        userPassword.setError("Enter valid Password");
        userPassword.requestFocus();
    }

    @Override
    public void showInvalidEmailError() {
        userEmail.setError("Please enter correct Email Id");
        userEmail.requestFocus();
    }

    @Override
    public void showInvalidPasswordError() {
        userPassword.setError("Please enter correct password");
        userPassword.requestFocus();
    }

    @Override
    public void isFbLoginSuccess(Boolean success) {
        if (success) {
//            loginPresenter.isUserExistsInDatabase();
            showToast("Successfully logged in");
        } else {
            dismissProgressDialog();
            showToast("Error while logging in");
        }
    }

    @Override
    public void navigateUser(Boolean isUserExists, com.example.c2n.core.model1.RetailerDataModel retailer, CustomerDataModel customer) {
        SharedPrefManager.LoadFromPref();

        if (userType.equals("R") && retailer != null) {
            if (SharedPrefManager.get_userEmail().equals("") || SharedPrefManager.get_userEmail() == null) {
                SharedPrefManager.set_userFullName(retailer.getRetailerName());
                SharedPrefManager.set_userMobileNo(retailer.getRetailerMobileNo());
                SharedPrefManager.set_userAddress(retailer.getRetailerAddress());
                SharedPrefManager.set_userType(userType);
                SharedPrefManager.set_userEmail(retailer.getRetailerID());
                SharedPrefManager.set_userDocumentID(retailer.getRetailerID());
                SharedPrefManager.set_userProfilePic(retailer.getRetailerImageURL());
                SharedPrefManager.StoreToPref();
            }

        } else if (userType.equals("C") && customer != null) {
            if (SharedPrefManager.get_userEmail().equals("") || SharedPrefManager.get_userEmail() == null) {
                SharedPrefManager.set_userFullName(customer.getCustomerName());
                SharedPrefManager.set_userMobileNo(customer.getCustomerMobileNo());
                SharedPrefManager.set_userAddress(customer.getCustomerAddress());
                SharedPrefManager.set_userType(userType);
                SharedPrefManager.set_userEmail(customer.getCustomerID());
                SharedPrefManager.set_userDocumentID(customer.getCustomerID());
                SharedPrefManager.set_userProfilePic(customer.getCustomerImageURL());
                SharedPrefManager.StoreToPref();
            }
        }

        if (isUserExists) {

            // < for customer

//            if (!userType.equals("R") && (SharedPrefManager.get_userDocumentID().equals("") || SharedPrefManager.get_userDocumentID() == null)) {
//                loginPresenter.setExistedUserDocumentId();
//                return;
//            }
//            if (SharedPrefManager.get_userEmail().equals("") && !userType.equals("R")) {
//                SharedPrefManager.set_userFullName(currentUser.getUserName());
//                SharedPrefManager.set_userMobileNo(currentUser.getUserMobileNo());
//                SharedPrefManager.set_userAddress(currentUser.getUserAddress());
//                SharedPrefManager.set_userType(userType);
//                SharedPrefManager.set_userEmail(getCurrentUserEmail());
////                SharedPrefManager.set_userDocumentID(userDocumentId);
//                SharedPrefManager.set_userProfilePic(getCurrentUserProfilePic());
//                SharedPrefManager.StoreToPref();
//            }

            //for customer >

            Log.d("SharedPrefManager", "uesrEmail : " + SharedPrefManager.get_userEmail());
            Log.d("login_document_id", userDocumentId + "");
            if (SharedPrefManager.get_userMobileNo().equals("")) {
                dismissProgressDialog();
                Log.d("navigation..", "mobile=null");
                intent = new Intent(LoginActivity.this, MobileVerificationActivity.class);
//                intent.putExtra("logintype", "login");
//                //   intent.putExtra("usertype", userType);
//                // intent.putExtra("username", getUserName());
//                intent.putExtra("email", SharedPrefManager.get_userEmail());
//                intent.putExtra("profilepic", SharedPrefManager.get_userProfilePic());
//                intent.putExtra("documentid", SharedPrefManager.get_userDocumentID());
                startActivity(intent);
                finish();
                finish();
//            } else if (SharedPrefManager.get_userAddress().equals("")) {
//                dismissProgressDialog();
//                Log.d("navigation..", "profile details not complete");
//                intent = new Intent(LoginActivity.this, EditProfileActivity.class);
//                intent.putExtra("logintype", "login");
////                intent.putExtra("usertype", userType);
//                // intent.putExtra("username", getUserName());
//                intent.putExtra("email", SharedPrefManager.get_userEmail());
//                intent.putExtra("profilepic", SharedPrefManager.get_userProfilePic());
//                intent.putExtra("documentid", SharedPrefManager.get_userDocumentID());
//                startActivity(intent);
//                finish();
            } else if (SharedPrefManager.get_userType().equals("C")) {
                dismissProgressDialog();
                Log.d("navigation..", "all profile details already proper.");
//                intent = new Intent(LoginActivity.this, CustomerHomeActivity.class);
//                intent.putExtra("logintype", "login");
//                //   intent.putExtra("usertype", userType);
//                // intent.putExtra("username", getUserName());
//                intent.putExtra("email", SharedPrefManager.get_userEmail());
//                intent.putExtra("profilepic", SharedPrefManager.get_userProfilePic());
//                intent.putExtra("documentid", SharedPrefManager.get_userDocumentID());
////                Log.d("profile_curent_pic", currentUser.getUserProfilePicUrl());
//                Log.d("navigation..qwerty", "" + "https://lh6.googleusercontent.com/-QQo1HD6qN2Q/AAAAAAAAAAI/AAAAAAAAAGg/DJIRRa3KqXY/s96-c/photo.jpg");
//                startActivity(intent);
//                finish();

                //< for customer

//                Toast.makeText(LoginActivity.this, "Customer Home Comming Soon", Toast.LENGTH_SHORT).show();
//                SharedPrefManager.DeleteAllEntriesFromPref();

                // for customer >

                startActivity(new Intent(LoginActivity.this, CustomerHomeActivity.class));
                finish();
                finish();
            } else if (SharedPrefManager.get_userType().equals("R")) {
                dismissProgressDialog();
                Log.d("navigation..", "all profile details already proper.");
                startActivity(new Intent(LoginActivity.this, RetailerHomeActivity.class));
                finish();
                finish();
            }

        } else
            loginPresenter.addUserInDatabase();
    }

    @Override
    public void setFbCurrentUserEmail(String FbEmail) {
        FbCurrentUserEmail = FbEmail;
    }

    @Override
    public void setFbCurrentUserProfilePic(String FbProfilePic) {
        FbCurrentUserProfilePicUrl = FbProfilePic;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            googleResultIntent = data;
            loginPresenter.userGoogleSignin();
        }
    }

    @Override
    public void setUserDocumentId(String documentId) {
        Log.d("setUserDocumentId", "" + documentId);
        userDocumentId = documentId;
        SharedPrefManager.set_userDocumentID(documentId);
        SharedPrefManager.StoreToPref();
    }

    @Override
    public void addUserSuccess(Boolean success) {
        dismissProgressDialog();
        if (success) {
            showToast("Details Added.");
//            SharedPrefManager.set_userDocumentID(userDocumentId);
//            SharedPrefManager.StoreToPref();

            currentUser = new UserDataModel("C", "", getCurrentUserEmail(), "", "", "", getUserProfilePic(), "", "");

            if (SharedPrefManager.get_userEmail().equals("")) {
                SharedPrefManager.set_userMobileNo(currentUser.getUserMobileNo());
                SharedPrefManager.set_userAddress(currentUser.getUserAddress());
                SharedPrefManager.set_userEmail(getCurrentUserEmail());
                SharedPrefManager.set_userDocumentID(userDocumentId);
                SharedPrefManager.set_userProfilePic(getUserProfilePic());
                SharedPrefManager.StoreToPref();
            }
            startActivity(new Intent(LoginActivity.this, MobileVerificationActivity.class));
            finish();
            finish();
        } else
            showToast("An Error occured.. ");
    }

    @Override
    public void checkIfUserExists() {
        loginPresenter.getCustomerInfo();
//        activateLoginButton();
    }
}
