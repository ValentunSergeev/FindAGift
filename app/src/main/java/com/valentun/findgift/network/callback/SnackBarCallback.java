package com.valentun.findgift.network.callback;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.valentun.findgift.R;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Valentun on 12.08.2017.
 */

public class SnackBarCallback<T> implements Callback<T> {

    private View container;
    private String successString, failureString;
    private Context context;

    public SnackBarCallback(View container, String successString, String failureString) {
        this.container = container;
        this.successString = successString;
        this.failureString = failureString;

        context = container.getContext();
    }

    public SnackBarCallback(View container, String successString) {
        this.container = container;
        this.successString = successString;

        context = container.getContext();
        failureString = context.getString(R.string.default_failure_message);
    }

    public SnackBarCallback(View container) {
        this.container = container;

        context = container.getContext();
        failureString = context.getString(R.string.default_failure_message);
    }

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        try {
            String msg;
            if (response.isSuccessful()) {
                if (successString == null) return;
                msg = successString;
            } else {
                msg = response.errorBody().string();
            }
            Snackbar.make(container, msg, Snackbar.LENGTH_SHORT)
                    .show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        Snackbar.make(container, failureString, Snackbar.LENGTH_SHORT)
                .show();
    }
}
