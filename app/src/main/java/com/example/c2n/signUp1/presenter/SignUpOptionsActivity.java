package com.example.c2n.signUp1.presenter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.example.c2n.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignUpOptionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_options);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_signup_c)
    public void openSignUpActivity() {
        Intent intent = new Intent(this, SignUpDetailsActivity.class);
        intent.putExtra("userType", "C");
        startActivity(intent);
    }

    @OnClick(R.id.btn_signup_r)
    public void openSignUpActivityy() {
        Intent intent = new Intent(this, SignUpDetailsActivity.class);
        intent.putExtra("userType", "R");
        startActivity(intent);
    }
}
