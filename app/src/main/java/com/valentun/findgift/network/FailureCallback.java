package com.valentun.findgift.network;

import android.support.design.widget.Snackbar;
import android.view.View;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Valentun on 09.08.2017.
 */

public class FailureCallback extends BaseCallback {
    public FailureCallback(View container) {
        super(container);
    }

    @Override
    public void onResponse(Call call, Response response) {
        Snackbar.make(container, String.valueOf(response.code()), Snackbar.LENGTH_SHORT)
                .show();
    }
}
