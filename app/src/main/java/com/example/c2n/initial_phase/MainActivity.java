package com.example.c2n.initial_phase;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.WindowManager;

import com.example.c2n.R;
import com.example.c2n.core.FirebaseConfig;
import com.example.c2n.core.SharedPrefManager;
import com.example.c2n.customer_home.presenter.CustomerHomeActivity;
import com.example.c2n.login.presenter.LoginActivity;
import com.example.c2n.mobileverification.presentation.presenter.MobileVerificationActivity;
import com.example.c2n.retailerhome.presenter.RetailerHomeActivity;
import com.example.c2n.signUp1.presenter.SignUpDetailsActivity;
import com.example.c2n.signUp1.presenter.SignUpOptionsActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private FirebaseAnalytics mFirebaseAnalytics;

    private String userType = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        mFirebaseAnalytics.setUserProperty("favorite_food", "apple");
//        mFirebaseAnalytics.logEvent();
//        mFirebaseAnalytics.setUserProperty("App Version", "apple");

        Bundle intent = getIntent().getExtras();
        userType = intent.getString("userType");
//        Log.d(TAG, "onCreate: " + intent.getString("userType"));

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        ButterKnife.bind(this);

        FirebaseConfig firebaseConfig = new FirebaseConfig();
        firebaseConfig.fetchNewBaseUrl(this);

        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        Log.d("IID_TOKEN", task.getResult().getToken());
                    }
                });

        SharedPrefManager.Init(this);
        SharedPrefManager.LoadFromPref();
        if (!SharedPrefManager.get_userDocumentID().equals("")) {
            if (SharedPrefManager.get_userMobileNo().equals("")) {
                Log.d("MainActivity", "mobile no: " + SharedPrefManager.get_userMobileNo().equals(""));
                startActivity(new Intent(MainActivity.this, MobileVerificationActivity.class));
                finish();
            } else if (SharedPrefManager.get_userType().equals("C")) {
//                Toast.makeText(MainActivity.this, "Customer Home Comming Soon", Toast.LENGTH_SHORT).show();
                Intent customerIntent = new Intent(MainActivity.this, CustomerHomeActivity.class);
                if (userType != null) {
                    customerIntent.putExtra("status", true);
                } else {
                    customerIntent.putExtra("status", false);
                }
                startActivity(customerIntent);
                finish();
            } else if (SharedPrefManager.get_userType().equals("R")) {
                Intent retailerIntent = new Intent(MainActivity.this, RetailerHomeActivity.class);
                if (userType != null) {
                    retailerIntent.putExtra("status", true);
                } else {
                    retailerIntent.putExtra("status", false);
                }
                startActivity(retailerIntent);
                finish();
            }
        }
    }

    @OnClick(R.id.btn_login_c)
    public void openCustomerActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.putExtra("userType", "C");
        startActivity(intent);
    }

    @OnClick(R.id.btn_login_r)
    public void openRetailerActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.putExtra("userType", "R");
        startActivity(intent);
    }

    @OnClick(R.id.btn_sign_up)
    public void openSignUpActivity() {
        Intent intent = new Intent(this, SignUpDetailsActivity.class);
        intent.putExtra("userType", "R");
        startActivity(intent);
       // startActivity(new Intent(this, SignUpOptionsActivity.class));
    }
}