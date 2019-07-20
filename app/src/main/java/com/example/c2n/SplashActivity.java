package com.example.c2n;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;

import butterknife.ButterKnife;

public class SplashActivity extends AppCompatActivity {

    private static final String TAG = SplashActivity.class.getSimpleName();

    //    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        progressBar = findViewById(R.id.progressBar);

        fetchWelcome();
    }

    private void fetchWelcome() {
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                // Run mFirebaseRemoteConfig.fetch(timeout) here, and it works
//
//                mFirebaseRemoteConfig.fetch(0)
//                        .addOnCompleteListener(new OnCompleteListener<Void>() {
//                            @Override
//                            public void onComplete(@NonNull Task<Void> task) {
//                                if (task.isSuccessful()) {
//                                    mFirebaseRemoteConfig.activateFetched();
//                                    Log.d(TAG, "onComplete: " + mFirebaseRemoteConfig.getString("BASE_URL"));
//                                    baseUrl = mFirebaseRemoteConfig.getString("BASE_URL");
//                                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
//                                } else {
//                                    Log.e(TAG, "onComplete: " + task.getException().getMessage());
//                                }
//                            }
//                        });
//            }
//        }, 0);
    }
}