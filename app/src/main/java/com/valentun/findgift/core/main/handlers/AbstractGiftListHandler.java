package com.valentun.findgift.core.main.handlers;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.valentun.findgift.R;
import com.valentun.findgift.core.main.adapters.AbstractGiftAdapter;
import com.valentun.findgift.databinding.GiftRecyclerItemBinding;
import com.valentun.findgift.models.Gift;
import com.valentun.findgift.network.APIClient;
import com.valentun.findgift.network.RetrofitClientFactory;
import com.valentun.findgift.network.callback.BaseCallback;

import retrofit2.Call;
import retrofit2.Response;

@SuppressWarnings("unchecked")
public abstract class AbstractGiftListHandler {
    protected GiftRecyclerItemBinding binding;
    protected Gift gift;
    protected APIClient client;
    protected Context context;
    protected AbstractGiftAdapter adapter;
    protected View root;

    public AbstractGiftListHandler(GiftRecyclerItemBinding binding, Gift gift, AbstractGiftAdapter adapter) {
        this.binding = binding;
        this.gift = gift;
        this.adapter = adapter;
        client = RetrofitClientFactory.getApiClient();
        root = binding.getRoot();
        context = root.getContext();
    }

    public void onVoteUpClicked(View view) {
        if (gift.isLiked()) {
            startDownVoteRequest();
        } else {
            startUpVoteRequest();
        }
    }

    public abstract void onFabClicked(View view);

    private void updateRateView(int rating) {
        binding.itemRate.setText(String.valueOf(rating));
    }

    private void setLikedState() {
        int color = ContextCompat.getColor(context, R.color.primary);
        binding.voteUp.setColorFilter(color);
        binding.itemRate.setTextColor(color);
    }

    private void setDislikedState() {
        binding.voteUp.clearColorFilter();
        binding.itemRate.setTextColor(ContextCompat.getColor(context, R.color.ic_color));
    }

    private void startUpVoteRequest() {
        client.upVoteGift(String.valueOf(gift.getId()))
                .enqueue(new BaseCallback<Gift>(root) {
                    @Override
                    public void onResponse(Call<Gift> call, Response<Gift> response) {
                        if (response.isSuccessful()) {
                            gift.updateLikeState(response.body());
                            updateRateView(gift.getRating());

                            setLikedState();
                        } else {
                            showDefaultErrorMessage();
                        }
                    }
                });
    }

    private void startDownVoteRequest() {
        client.downVoteGift(String.valueOf(gift.getId()))
                .enqueue(new BaseCallback<Gift>(root) {
                    @Override
                    public void onResponse(Call<Gift> call, Response<Gift> response) {
                        if (response.isSuccessful()) {
                            gift.updateLikeState(response.body());

                            updateRateView(gift.getRating());
                            setDislikedState();
                        } else {
                            showDefaultErrorMessage();
                        }
                    }
                });
    }
}
