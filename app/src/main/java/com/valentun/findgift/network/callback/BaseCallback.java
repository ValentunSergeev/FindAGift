package com.valentun.findgift.network.callback;


import android.app.ProgressDialog;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.ProgressBar;

import com.valentun.findgift.R;

import retrofit2.Call;
import retrofit2.Callback;

public abstract class BaseCallback<T> implements Callback<T> {
    protected View container;
    private ProgressDialog dialog;
    private ProgressBar bar;

    public BaseCallback(View container) {
        this.container = container;
    }

    public BaseCallback(View container, ProgressBar bar) {
        this.container = container;
        this.bar = bar;
    }

    public BaseCallback(View container, ProgressDialog dialog) {
        this.container = container;
        this.dialog = dialog;
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        if (dialog != null) dialog.dismiss();
        if (bar != null) bar.setVisibility(View.GONE);
        showMessage(t.getMessage());
    }

    protected void showMessage(String msg) {
        Snackbar.make(container, msg, Snackbar.LENGTH_SHORT)
                .show();
    }

    protected void showDefaultErrorMessage() {
        showMessage(container.getContext().getString(R.string.not_success_default));
    }
}
