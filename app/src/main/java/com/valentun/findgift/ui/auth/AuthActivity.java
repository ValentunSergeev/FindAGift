package com.valentun.findgift.ui.auth;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.valentun.findgift.Constants;
import com.valentun.findgift.GiftApplication;
import com.valentun.findgift.R;
import com.valentun.findgift.models.AuthModel;
import com.valentun.findgift.network.APIClient;
import com.valentun.findgift.network.callback.BaseCallback;
import com.valentun.findgift.persistence.SessionManager;
import com.valentun.findgift.ui.abstracts.ApiActivity;
import com.valentun.findgift.ui.main.MainActivity;
import com.valentun.findgift.utils.WidgetUtils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Headers;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

public class AuthActivity extends ApiActivity {

    @BindView(R.id.email) EditText emailField;
    @BindView(R.id.password) EditText passwordField;
    @BindView(R.id.register_submit) Button registerSubmit;
    @BindView(R.id.login_submit) Button loginSubmit;

    @Inject APIClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        ButterKnife.bind(this);
        GiftApplication.getAppComponent().inject(this);

        WidgetUtils.colorizeTextWidget(registerSubmit, R.color.accent);
        WidgetUtils.colorizeTextWidget(loginSubmit, R.color.indigo);
    }



    public void logInClicked(View view) {
        if (isFieldsEmpty()) {
            showSnackbarMessage(R.string.empty_fields_message);
        } else {
            login();
        }
    }

    public void registerClicked(View view) {
        if (isFieldsEmpty()) {
            showSnackbarMessage(R.string.empty_fields_message);
        } else {
            register();
        }
    }

    private void register() {
        showProgress(getString(R.string.register_process));

        client.createUser(getFilledData())
                .enqueue(new AuthCallback());
    }

    private void login() {
        showProgress(getString(R.string.log_in_process));

        client.createSession(getFilledData())
                .enqueue(new AuthCallback());
    }

    private AuthModel getFilledData() {
        String email = emailField.getText().toString();
        String password = passwordField.getText().toString();

        return new AuthModel(email, password);
    }

    private boolean isFieldsEmpty() {
        String email = emailField.getText().toString();
        String password = passwordField.getText().toString();

        return TextUtils.isEmpty(email) || TextUtils.isEmpty(password);
    }

    private class AuthCallback extends BaseCallback<ResponseBody> {

        AuthCallback() {
            super(AuthActivity.this.container, AuthActivity.this.progressDialog);
        }

        @Override
        public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
            if (response.isSuccessful()) {
                Headers headers = response.headers();

                String uid = headers.get(Constants.UID_KEY);
                String client = headers.get(Constants.CLIENT_KEY);
                String token = headers.get(Constants.ACCESS_TOKEN_KEY);

                SessionManager.startSession(uid, client, token);

                startActivity(new Intent(AuthActivity.this, MainActivity.class));
                finish();
            } else {
                showSnackbarMessage("Invalid credentials please try again");
            }

            progressDialog.dismiss();
        }
    }
}
