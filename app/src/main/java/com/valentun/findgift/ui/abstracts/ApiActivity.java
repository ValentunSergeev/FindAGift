package com.valentun.findgift.ui.abstracts;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.valentun.findgift.network.callback.BaseCallback;

public abstract class ApiActivity extends AppCompatActivity {
    protected View container;
    protected ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        container = findViewById(android.R.id.content);

        setUpProgress();
    }

    protected void showProgress(String string) {
        if (progressDialog.isShowing()) progressDialog.dismiss();
        progressDialog.setTitle(string);
        progressDialog.show();
    }
    protected void showSnackbarMessage(@StringRes int stringRes) {
        showSnackbarMessage(getString(stringRes));
    }

    protected void showSnackbarMessage(String message) {
        Snackbar.make(container, message, Snackbar.LENGTH_SHORT)
                .show();
    }

    private void setUpProgress() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
    }

    protected abstract class ApiCallback<T> extends BaseCallback<T> {

        protected ApiCallback() {
            super(ApiActivity.this.container, progressDialog);
        }
    }
}
