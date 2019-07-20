package com.example.c2n.login.presenter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.c2n.core.model.UserDataModel;
import com.example.c2n.core.model.UserDataModelListToEmailViewModelMapper;
import com.example.c2n.core.model1.CustomerDataModel;
import com.example.c2n.core.model1.RetailerDataModel;
import com.example.c2n.login.domain.AddCustomerFromRetailerUseCase;
import com.example.c2n.login.domain.AddCustomerUseCase;
import com.example.c2n.login.domain.AddRetailerUseCase;
import com.example.c2n.login.domain.CheckUserUseCase;
import com.example.c2n.login.domain.GetCustomerUseCase;
import com.example.c2n.login.domain.GetRetailerUseCase;
import com.example.c2n.login.domain.GetUserDocumentIdUseCase;
import com.example.c2n.login.util.LoginValidator;
import com.example.c2n.signup.domain.SignUpUseCase;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.DocumentReference;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import javax.inject.Inject;

import static android.content.ContentValues.TAG;

/**
 * Created by vipul.singhal on 10-05-2018.
 */

public class LoginPresenter {

    private static final String EMAIL = "email";
    com.example.c2n.login.presenter.LoginView loginView;
    LoginValidator loginValidator;
    CheckUserUseCase checkUserUseCase;
    GetRetailerUseCase getRetailerUseCase;
    GetCustomerUseCase getCustomerUseCase;
    AddRetailerUseCase addRetailerUseCase;
    AddCustomerFromRetailerUseCase addCustomerFromRetailerUseCase;
    AddCustomerUseCase addCustomerUseCase;
    SignUpUseCase signUpUseCase;
    GetUserDocumentIdUseCase getUserDocumentIdUseCase;
    Context mContext;
    UserDataModelListToEmailViewModelMapper userDataModelListToEmailViewModelMapper;
    private FirebaseAuth mAuth;

    public void bind(LoginView loginView, Context mContext) {
        this.loginView = loginView;
        this.mContext = mContext;
        mAuth = FirebaseAuth.getInstance();
    }

    @Inject
    LoginPresenter(LoginValidator loginValidator, UserDataModelListToEmailViewModelMapper userDataModelListToEmailViewModelMapper, CheckUserUseCase checkUserUseCase, AddCustomerFromRetailerUseCase addCustomerFromRetailerUseCase, AddCustomerUseCase addCustomerUseCase, AddRetailerUseCase addRetailerUseCase, GetCustomerUseCase getCustomerUseCase, GetRetailerUseCase getRetailerUseCase, SignUpUseCase signUpUseCase, GetUserDocumentIdUseCase getUserDocumentIdUseCase) {
        this.loginValidator = loginValidator;
        this.userDataModelListToEmailViewModelMapper = userDataModelListToEmailViewModelMapper;
        this.checkUserUseCase = checkUserUseCase;
        this.getCustomerUseCase = getCustomerUseCase;
        this.addCustomerFromRetailerUseCase = addCustomerFromRetailerUseCase;
        this.addCustomerUseCase = addCustomerUseCase;
        this.addRetailerUseCase = addRetailerUseCase;
        this.getRetailerUseCase = getRetailerUseCase;
        this.signUpUseCase = signUpUseCase;
        this.getUserDocumentIdUseCase = getUserDocumentIdUseCase;
    }

    public void validateUser() {
        if (loginValidator.isValidEmail(loginView.getUserEmail())) {
//            if (loginValidator.isValidPassword(loginView.getUserPassword()))
            userSignInWithEmailPassword();
//            else
//                loginView.showPasswordFormatError();
        } else
            loginView.showEmailFormatError();
    }

    public void userSignInWithEmailPassword() {
        loginView.showLoginProgress(true);
        mAuth.signInWithEmailAndPassword(loginView.getUserEmail(), loginView.getUserPassword())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            loginView.isUserLoginSuccess(true, false, false);
                            Log.d("signin_success:", user.getEmail());
                        } else if (task.getException().getMessage().equals("There is no user record corresponding to this identifier. The user may have been deleted.")) {
                            loginView.showInvalidEmailError();
                            loginView.isUserLoginSuccess(false, true, false);
                        } else if (task.getException().getMessage().equals("The password is invalid or the user does not have a password.")) {
                            loginView.showInvalidPasswordError();
                            loginView.isUserLoginSuccess(false, false, true);
                        }
//                        Log.d("error in login", task.getException().getMessage());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "userSignInWithEmailPassword onFailure: " + e.getMessage());
                    }
                });
    }

    public void userGoogleSignin() {
        loginView.showLoginProgress(true);
        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(loginView.getGoogleResultIntent());
        handleSigninResult(task);
    }

    private void handleSigninResult(Task<GoogleSignInAccount> completedTask) {
        if (completedTask != null) {
            try {
                GoogleSignInAccount account = completedTask.getResult(ApiException.class);
                Log.i("User_account....", "-----> " + account.toString());
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                Log.w("Error in login...", " signInResult:failed code=" + e.getStatusCode() + "error message..." + e.getMessage());
                loginView.isGoogleSigninSuccess(false);
            }

        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId() + "....account holder name::---" + acct.getDisplayName());

        //showProgressDialog();

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            loginView.checkIfUserExists();
                        } else {
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            loginView.isGoogleSigninSuccess(false);
                        }
                    }
                });
    }

    public void handleFacebookSignInResult(LoginButton loginButton, CallbackManager callbackManager) {
        Log.d("handleFacebook", "in");
        loginButton.setReadPermissions(Arrays.asList(EMAIL));
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d("onSuccess", "" + loginResult.getAccessToken());
                loginView.isFbLoginSuccess(true);
                GraphRequest graphRequest = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        try {
                            //  FB_EMAIL = object.getString("email");
                            loginView.setFbCurrentUserEmail(object.getString("email"));
                            loginView.setFbCurrentUserProfilePic(object.getJSONObject("picture").getJSONObject("CustomerSingleProductRepository").getString("url"));
                            Log.d("Facebook_Email_Complete", "" + object.getString("email"));

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d("Facebook_Email_Error", "" + e.getMessage());
                        }
                    }

                });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender,birthday,picture");
                graphRequest.setParameters(parameters);
                graphRequest.executeAsync();

            }

            @Override
            public void onCancel() {
                Log.d("onCancel", "cancel");
                loginView.isFbLoginSuccess(false);
            }

            @Override
            public void onError(FacebookException exception) {
                Log.d("onError", "" + exception.getMessage());
                loginView.isFbLoginSuccess(false);
            }
        });
    }

    @SuppressLint("RxLeakedSubscription")
    public void setExistedUserDocumentId() {
        getUserDocumentIdUseCase.execute(loginView.getCurrentUserEmail(), mContext)
                .subscribe(this::handleDocumentIdResponse, throwable -> handleDocumentIdError(throwable));
    }

    public void handleDocumentIdResponse(String userDocumentId) {
        Log.d("login_presenter_id", userDocumentId);
        loginView.setUserDocumentId(userDocumentId);
        loginView.navigateUser(true, null, null);
    }

    public void handleDocumentIdError(Throwable e) {
        Log.d("error...", "..." + e.getMessage());
        loginView.isUserLoginSuccess(false, false, false);
    }

    @SuppressLint("RxLeakedSubscription")
    public void addUserInDatabase() {
        signUpUseCase.execute(new UserDataModel("C", "", loginView.getCurrentUserEmail(), "", "", "", loginView.getUserProfilePic(), "", ""), mContext)
                .subscribe(this::handleResponse, throwable -> handleError(throwable));
    }

    public void handleResponse(DocumentReference documentReference) {
        loginView.setUserDocumentId(documentReference.getId());
        loginView.addUserSuccess(true);
    }

    public void handleError(Throwable e) {
        loginView.addUserSuccess(false);
    }


    @SuppressLint("RxLeakedSubscription")
    public void getRetailerInfo() {
        getRetailerUseCase.execute(loginView.getUserEmail(), mContext)
                .subscribe(this::handleRetailerResponse, throwable -> handleRetailerResponseError(throwable));
    }

    public void handleRetailerResponse(RetailerDataModel retailerDataModel) {
        if (retailerDataModel.getRetailerID() == null || retailerDataModel.getRetailerID().equals("")) {
            addRetailerInDatabase();
            return;
        }
        Log.d("handleRetailResponse", "" + retailerDataModel.toString());
        loginView.navigateUser(true, retailerDataModel, null);

    }

    public void handleRetailerResponseError(Throwable e) {
        Log.d("error...", "..." + e.getMessage());
        loginView.isUserLoginSuccess(false, false, false);
    }

    @SuppressLint("RxLeakedSubscription")
    public void addRetailerInDatabase() {
        RetailerDataModel retailerDataModel = new RetailerDataModel();
        retailerDataModel.setRetailerID(loginView.getUserEmail());
        addRetailerUseCase.execute(retailerDataModel, mContext)
                .subscribe(this::handleAddingRetailerResponse, throwable -> handleAddingRetailerError(throwable));
    }

    public void handleAddingRetailerResponse(DocumentReference documentReference) {
        getRetailerInfo();
    }

    public void handleAddingRetailerError(Throwable e) {
        Log.d("error...", "..." + e.getMessage());
        loginView.isUserLoginSuccess(false, false, false);
    }

    @SuppressLint("RxLeakedSubscription")
    public void getCustomerInfo() {
        getCustomerUseCase.execute(loginView.getUserEmail(), mContext)
                .subscribe(this::handleCustomerResponse, throwable -> handleCustomerResponseError(throwable));
    }

    public void handleCustomerResponse(CustomerDataModel customerDataModel) {
        if (customerDataModel.getCustomerID() == null || customerDataModel.getCustomerID().equals("")) {
            if (loginView.isGoogleOrFbLogin())
                addCustomerInDatabase();
            else
                addCustomerFromRetailerInDatabase();
            return;
        }
        Log.d("handleCustomerResponse", "" + customerDataModel.toString());
        loginView.navigateUser(true, null, customerDataModel);

    }

    public void handleCustomerResponseError(Throwable e) {
        Log.d("error...", "..." + e.getMessage());
        loginView.isUserLoginSuccess(false, false, false);
    }

    @SuppressLint("RxLeakedSubscription")
    public void addCustomerFromRetailerInDatabase() {
        CustomerDataModel customerDataModel = new CustomerDataModel();
        customerDataModel.setCustomerID(loginView.getUserEmail());
        addCustomerFromRetailerUseCase.execute(customerDataModel, mContext)
                .subscribe(this::handleAddingCustomerResponse, throwable -> handleAddingCustomerError(throwable));
    }

    public void handleAddingCustomerResponse(DocumentReference documentReference) {
        getCustomerInfo();
    }

    public void handleAddingCustomerError(Throwable e) {
        Log.d("error...", "..." + e.getMessage());
        loginView.isUserLoginSuccess(false, false, false);
    }

    @SuppressLint("RxLeakedSubscription")
    public void addCustomerInDatabase() {
        CustomerDataModel customerDataModel = new CustomerDataModel();
        customerDataModel.setCustomerID(loginView.getUserEmail());
        customerDataModel.setCustomerName(loginView.getUserName());
        customerDataModel.setCustomerImageURL(loginView.getUserProfilePic());
        addCustomerUseCase.execute(customerDataModel, mContext)
                .subscribe(this::handleAddingCustomerResponse, throwable -> handleAddingCustomerError(throwable));
    }
//    @SuppressLint("RxLeakedSubscription")
//    public void isUserExistsInDatabase() {
//        checkUserUseCase.execute(null, mContext)
//                .subscribe(this::handleEmailResponse, throwable -> handleEmailResponseError(throwable));
//    }
//
//    public void handleEmailResponse(List<UserDataModel> usersList) {
//        Log.d("handleEmailResponse", "" + usersList.toString());
//        loginView.navigateUser(userDataModelListToEmailViewModelMapper.mapUserDataModelListToEmailBoolean(usersList, loginView.getCurrentUserEmail()), null);
//    }
//
//    public void handleEmailResponseError(Throwable e) {
//        Log.d("error...", "..." + e.getMessage());
//        loginView.isUserLoginSuccess(false, false, false);
//    }
}