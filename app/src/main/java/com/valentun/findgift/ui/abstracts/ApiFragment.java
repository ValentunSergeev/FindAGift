package com.valentun.findgift.ui.abstracts;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.View;

import com.valentun.findgift.R;
import com.valentun.findgift.network.APIClient;
import com.valentun.findgift.network.ApiClientFactory;

/**
 * Created by Valentun on 11.08.2017.
 */

public class ApiFragment extends Fragment {
    protected APIClient apiClient;
    protected Activity parent;
    protected View container;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        parent = getActivity();
        apiClient = ApiClientFactory.getApiClient();
        container = parent.findViewById(R.id.main_fragment_container);

        setRetainInstance(true);
    }

    protected void showSnackbarMessage(@StringRes int stringRes) {
        showSnackbarMessage(getString(stringRes));
    }

    protected void showSnackbarMessage(String message) {
        Snackbar.make(container, message, Snackbar.LENGTH_SHORT)
                .show();
    }
}
