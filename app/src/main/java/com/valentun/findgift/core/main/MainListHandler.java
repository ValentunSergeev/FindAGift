package com.valentun.findgift.core.main;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Toast;

import com.valentun.findgift.R;
import com.valentun.findgift.databinding.GiftRecyclerItemBinding;
import com.valentun.findgift.models.Gift;
import com.valentun.findgift.network.APIClient;
import com.valentun.findgift.network.ApiClientFactory;
import com.valentun.findgift.network.FailureCallback;

@SuppressWarnings("unchecked")
public class MainListHandler {
    private GiftRecyclerItemBinding binding;
    private Gift gift;
    private APIClient client;
    private Context context;

    public MainListHandler(GiftRecyclerItemBinding binding, Gift gift) {
        this.binding = binding;
        this.gift = gift;
        client = ApiClientFactory.getApiClient();
        context = binding.getRoot().getContext();
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

    public void onStarClicked(View view) {
        Toast.makeText(view.getContext(), "starClicked", Toast.LENGTH_SHORT).show();
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
                .enqueue(new FailureCallback(binding.getRoot()));
    }

    private void startDownVoteRequest() {
        client.downVoteGift(String.valueOf(gift.getId()))
                .enqueue(new FailureCallback(binding.getRoot()));
    }
}
