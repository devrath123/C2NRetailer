package com.example.c2n.core.base;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.example.c2n.R;
import com.example.c2n.core.model.UserDataModel;

/**
 * Created by vipul.singhal on 09-05-2018.
 */

public abstract class BaseActivity extends AppCompatActivity {

    public ProgressDialog progressDialog;
    public static UserDataModel currentUser;
    boolean doubleBackToExitPressedOnce = false;
    public int fragmentCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResourceId());
        progressDialog = new ProgressDialog(this);
        if(getSupportActionBar()!=null) {
            getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.color.themecolor));
            getSupportActionBar().setElevation(8);
        }
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.themecolordark));
        }
        initActivity();
    }

    protected abstract int getLayoutResourceId();

    protected abstract void initActivity();

    protected void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    protected void showProgressDialog(String msg) {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(msg);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    protected void dismissProgressDialog() {
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }

//    @Override
//    public void onBackPressed() {
//        if (doubleBackToExitPressedOnce) {
//            super.onBackPressed();
//            System.exit(0);
//            return;
//        }
//
//        this.doubleBackToExitPressedOnce = true;
//        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();
//
//        new Handler().postDelayed(new Runnable() {
//
//            @Override
//            public void run() {
//                doubleBackToExitPressedOnce = false;
//            }
//        }, 2000);
//    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Log.d("on_back", "pressed");

//        fragmentPop();
    }

    public void fragmentPop() {
        fragmentCount--;
        if (fragmentCount <= 0) {
            finish();
            return;
        } else {
            Log.d("on_back", fragmentCount + "");
            getSupportFragmentManager().popBackStack();
        }
    }
}
