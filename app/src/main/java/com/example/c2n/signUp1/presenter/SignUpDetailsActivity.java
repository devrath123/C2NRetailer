package com.example.c2n.signUp1.presenter;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.c2n.R;
import com.example.c2n.core.base.BaseKeyBoardActivity;
import com.example.c2n.signUp1.di.SignUp1DI;
import com.example.c2n.signup.presenter.SignUpActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignUpDetailsActivity extends BaseKeyBoardActivity implements SignUp1View {
    private String userType;

//    @BindView(R.id.iv_user_type)
//    ImageView userTypeImage;

    @BindView(R.id.link_signup)
    TextView usersType;

    @BindView(R.id.input_full_name)
    EditText editTextFullName;

    @BindView(R.id.input_email)
    EditText editTextEmail;

    @BindView(R.id.btn_next)
    AppCompatButton buttonNext;

    @BindView(R.id.email_progress_bar)
    ProgressBar emailProgressBar;
    @Inject
    SignUp1Presenter signUp1Presenter;

    public ProgressDialog progressDialog;
    Boolean iskeyboardOpened = false;
    Boolean isUserEmailValid = false;
    Boolean isNextButtonActive = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_details);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        ButterKnife.bind(this);

        SignUp1DI.getSignUp1Component().inject(this);
        signUp1Presenter.bind(this, this);

        deactivateNextButton();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            userType = bundle.getString("userType");
        }
        if (userType != null) {
            if (userType.equals("R"))
                usersType.setText("Retailer SignUp");
            else
                usersType.setText("Customer SignUp");
        }
        attachKeyboardListeners();

        editTextFullName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.d("SignUpDetailsActivity_", "length : " + s.length());
                if (count == 0) {
                    deactivateNextButton();
                } else {
                    activateNextButton();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        editTextEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                deactivateNextButton();
                if (count != 0)
                    validateEmail();
                else
                    deactivateNextButton();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
//        editTextEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (!TextUtils.isEmpty(editTextEmail.getText()) && !isNextButtonActive)
//                    validateEmail();
//            }
//        });
//        editTextEmail.setOnEditorActionListener(new EditText.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                if (actionId == EditorInfo.IME_ACTION_DONE ||
//                        event != null &&
//                                (event.getAction() == KeyEvent.ACTION_DOWN || event.getKeyCode() == KeyEvent.ACTION_DOWN || event.getFlags() == KeyEvent.FLAG_CANCELED || event.getFlags() == KeyEvent.FLAG_VIRTUAL_HARD_KEY) ||
//                        event != null &&
//                                event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
//                    if (event == null || !event.isShiftPressed()) {
//                        // the user is done typing.
//                        validateEmail();
//                        Log.d("type", "done");
//                        return true; // consume.
//                    }
//                }
//                Log.d("type", "not_done");
//                return false; // pass on to other listeners.
//            }
//        });


    }

    @Override
    protected void onShowKeyboard(int keyboardHeight) {
        super.onShowKeyboard(keyboardHeight);
        Log.d("type_s", "keyboard_show");
        iskeyboardOpened = true;

    }

    @Override
    protected void onHideKeyboard() {
        super.onHideKeyboard();
        Log.d("type", "keyboard_hide");
        if (!TextUtils.isEmpty(editTextEmail.getText()) && iskeyboardOpened && editTextEmail.hasFocus() && !isNextButtonActive)
            validateEmail();
    }

    @Override
    public void showEmailProgress(Boolean show) {
        if (show)
            emailProgressBar.setVisibility(View.VISIBLE);
        else {
            activateNextButton();
            emailProgressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public String getUserName() {
        return editTextFullName.getText().toString().trim();
    }

    @Override
    public String getUserEmail() {
        return editTextEmail.getText().toString().trim();
    }

    @Override
    public String getUserType() {
        return userType;
    }

    @Override
    public void showEmailError(String errorMsg) {
        editTextEmail.setError(errorMsg);
    }

    @Override
    public void isUserExist(Boolean isExists) {
        if (isExists) {
            deactivateNextButton();
            isUserEmailValid = false;
            showEmailError("Email Id Already exists");
        } else {
            isUserEmailValid = true;
            activateNextButton();
//            if(TextUtils.isEmpty(editTextFullName.getText()))
//            editTextEmail.setFocusable(false);
//            openPasswordActivity();
        }
    }

    private void activateNextButton() {
        Log.d("SignUpDetailsActivity_", "in activateNextButton");
        if (isUserEmailValid && !TextUtils.isEmpty(editTextFullName.getText())) {
            isNextButtonActive = true;
            buttonNext.setTextColor(getResources().getColor(R.color.white));
            buttonNext.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_background_hollow));
            Log.d("activateuse", isUserEmailValid.toString());
        }
    }

    private void deactivateNextButton() {
        Log.d("SignUpDetailsActivity_", "in deactivateNextButton");
        isNextButtonActive = false;
        buttonNext.setTextColor(getResources().getColor(R.color.white));
        buttonNext.setBackgroundDrawable(getResources().getDrawable(R.drawable.inactive_button_background_hollow));
    }

    @OnClick(R.id.btn_next)
    public void navigatePasswordActivity() {
        if (isNextButtonActive)
            openPasswordActivity();
    }

    @Override
    public void validateEmail() {
        signUp1Presenter.validateEmail();
    }

    @Override
    public void openPasswordActivity() {
        Intent intent = new Intent(this, SignUpActivity.class);
        intent.putExtra("userType", userType);
        intent.putExtra("userFullName", getUserName());
        intent.putExtra("userEmail", getUserEmail());
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        iskeyboardOpened = false;
    }
}
