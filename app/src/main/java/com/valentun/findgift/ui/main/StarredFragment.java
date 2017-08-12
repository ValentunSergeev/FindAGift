package com.valentun.findgift.ui.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.valentun.findgift.R;
import com.valentun.findgift.core.main.adapters.AbstractGiftAdapter;
import com.valentun.findgift.core.main.adapters.StartGiftAdapter;
import com.valentun.findgift.models.Gift;
import com.valentun.findgift.network.callback.BaseCallback;
import com.valentun.findgift.ui.abstracts.ApiFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Response;

public class StarredFragment extends ApiFragment implements AbstractGiftAdapter.RemoveItemListener {
    @BindView(R.id.starred_progress) ProgressBar progressBar;
    @BindView(R.id.starred_recycler) RecyclerView recyclerView;
    @BindView(R.id.starred_placeholder) View placeholder;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_starred, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, view);

        setUpRecycler();

        placeholder.setVisibility(View.GONE);
    }

    private void setUpRecycler() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(parent));
        recyclerView.setAdapter(new StartGiftAdapter(null, this));
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        placeholder.setVisibility(View.GONE);
    }

    @Override
    protected void makeRequest() {
        apiClient.getStarredGifts().enqueue(new BaseCallback<List<Gift>>(container, progressBar) {
            @Override
            public void onResponse(Call<List<Gift>> call, Response<List<Gift>> response) {
                List<Gift> result = response.body();
                recyclerView.setAdapter(new StartGiftAdapter(result, StarredFragment.this));
                progressBar.setVisibility(View.GONE);
                if (result.size() == 0) placeholder.setVisibility(View.VISIBLE);
                else recyclerView.setVisibility(View.VISIBLE);
                refreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void onItemRemoved(int dataSize) {
        if (dataSize == 0) {
            recyclerView.setVisibility(View.GONE);
            placeholder.setVisibility(View.VISIBLE);
        }
    }
}
