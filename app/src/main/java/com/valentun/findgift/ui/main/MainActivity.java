package com.valentun.findgift.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.valentun.findgift.R;
import com.valentun.findgift.core.main.MainAdapter;
import com.valentun.findgift.models.Gift;
import com.valentun.findgift.network.BaseCallback;
import com.valentun.findgift.persistence.SessionManager;
import com.valentun.findgift.ui.abstracts.ApiActivity;
import com.valentun.findgift.ui.auth.AuthActivity;
import com.valentun.findgift.ui.newgift.NewGiftActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Response;
//--------------------------------------
//TODO hide/close fab when recyclerView is scrolling down/up
//TODO land layout for new_gift
//TODO land/port layouts for gift item
//TODO navDrawer
//--------------------------------------
//TODO query parameters - server and app(add slide panel, modify activity&adapter)
//--------------------------------------
//TODO Like and dislike functionality
//TODO Save(star) functionality
//--------------------------------------
//TODO credentials page
//TODO settings page
//--------------------------------------

public class MainActivity extends ApiActivity {

    @BindView(R.id.main_recycler) RecyclerView recyclerView;
    @BindView(R.id.main_progress) ProgressBar progressBar;

    private static final int NEW_GIFT_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(!SessionManager.isSessionStarted()) {
            Intent intent = new Intent(this, AuthActivity.class);
            startActivity(intent);

            finish();
            return;
        }

        ButterKnife.bind(this);

        container = findViewById(R.id.main_container);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new MainAdapter(null));

        makeRequest();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode, data);
        if (requestCode == NEW_GIFT_REQUEST_CODE && resultCode == RESULT_OK) {
            showSnackbarMessage(R.string.gift_created_message);
            refreshData();
        }
    }

    private void refreshData() {
        recyclerView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);

        makeRequest();
    }

    private void makeRequest() {
        apiClient.getGifts().enqueue(new BaseCallback<List<Gift>>(container, progressBar) {
            @Override
            public void onResponse(Call<List<Gift>> call, Response<List<Gift>> response) {
                List<Gift> result = response.body();
                recyclerView.setAdapter(new MainAdapter(result));
                progressBar.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
            }
        });
    }

    public void newButtonClicked(View view) {
        Intent intent = new Intent(this, NewGiftActivity.class);
        startActivityForResult(intent, NEW_GIFT_REQUEST_CODE);
    }
}
