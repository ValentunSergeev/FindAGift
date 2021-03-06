package com.valentun.findgift.ui.abstracts;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;

import com.valentun.findgift.R;

public abstract class ApiFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener  {
    protected Activity parent;
    protected View container;
    protected SwipeRefreshLayout refreshLayout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        parent = getActivity();
        container = parent.findViewById(R.id.main_fragment_container);

        setRetainInstance(true);

        parent.setTitle(getTitle());
    }

    protected abstract CharSequence getTitle();

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.gifts_container);
        refreshLayout.setColorSchemeResources(R.color.primary, R.color.accent);
        refreshLayout.setOnRefreshListener(this);

        makeRequest();
    }

    @Override
    public void onRefresh() {
        refreshLayout.setRefreshing(true);
        makeRequest();
    }

    protected abstract void makeRequest();

    protected void showSnackbarMessage(@StringRes int stringRes) {
        showSnackbarMessage(getString(stringRes));
    }

    protected void showSnackbarMessage(String message) {
        Snackbar.make(container, message, Snackbar.LENGTH_SHORT)
                .show();
    }
}
