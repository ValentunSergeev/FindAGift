package com.valentun.findgift.core.main.handlers;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.valentun.findgift.R;
import com.valentun.findgift.core.main.adapters.AbstractGiftAdapter;
import com.valentun.findgift.databinding.GiftRecyclerItemBinding;
import com.valentun.findgift.models.Gift;
import com.valentun.findgift.network.APIClient;
import com.valentun.findgift.network.ApiClientFactory;
import com.valentun.findgift.network.callback.SnackBarCallback;

@SuppressWarnings("unchecked")
public abstract class AbstractGiftListHandler {
    protected GiftRecyclerItemBinding binding;
    protected Gift gift;
    protected APIClient client;
    protected Context context;
    protected AbstractGiftAdapter adapter;
    protected View root;
    private SnackBarCallback unStarCallBack;

    public AbstractGiftListHandler(GiftRecyclerItemBinding binding, Gift gift, AbstractGiftAdapter adapter) {
        this.binding = binding;
        this.gift = gift;
        this.adapter = adapter;
        client = ApiClientFactory.getApiClient();
        root = binding.getRoot();
        context = root.getContext();

        unStarCallBack = new SnackBarCallback(root,
                context.getString(R.string.unstar_success));
    }

    public void onVoteUpClicked(View view) {
        if (gift.isLiked()){
            updateRateView(-1);
            setDislikeState();

            gift.setRating(gift.getRating() - 1);
            gift.setIsLiked(false);

            startDownVoteRequest();
        } else {
            updateRateView(1);
            setLikedState();

            gift.setRating(gift.getRating() + 1);
            gift.setIsLiked(true);

            startUpVoteRequest();
        }
    }

    public abstract void onFabClicked(View view);

    void makeUnStarRequest() {
        client.unstarGift(String.valueOf(gift.getId())).enqueue(unStarCallBack);
    }

    private void updateRateView(int delta) {
        int rating = Integer.parseInt(binding.itemRate.getText().toString());

        binding.itemRate.setText(String.valueOf(rating + delta));
    }

    private void setLikedState() {
        int color = ContextCompat.getColor(context, R.color.primary);
        binding.voteUp.setColorFilter(color);
        binding.itemRate.setTextColor(color);
    }

    private void setDislikeState() {
        binding.voteUp.clearColorFilter();
        binding.itemRate.setTextColor(ContextCompat.getColor(context, R.color.ic_color));
    }

    private void startUpVoteRequest() {
        client.upVoteGift(String.valueOf(gift.getId()))
                .enqueue(new SnackBarCallback(root));
    }

    private void startDownVoteRequest() {
        client.downVoteGift(String.valueOf(gift.getId()))
                .enqueue(new SnackBarCallback(root));
    }
}
