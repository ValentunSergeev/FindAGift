package com.valentun.findgift.core.main.handlers;

import android.view.View;

import com.valentun.findgift.R;
import com.valentun.findgift.databinding.GiftRecyclerItemBinding;
import com.valentun.findgift.models.Gift;
import com.valentun.findgift.network.callback.BaseCallback;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

@SuppressWarnings("unchecked")
public class MainListHandler extends AbstractGiftListHandler {

    public MainListHandler(GiftRecyclerItemBinding binding, Gift gift) {
        super(binding, gift,  null);
    }

    @Override
    public void onFabClicked(View view) {
        if (gift.isStarred()) {
            makeUnStarRequest();
        } else {
            makeStarRequest();
        }
    }

    private void makeUnStarRequest() {
        client.unstarGift(String.valueOf(gift.getId())).enqueue(new BaseCallback<ResponseBody>(root) {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    binding.giftItemStar.setImageResource(R.drawable.ic_star_border_white_24dp);
                    gift.setStarred(false);
                    showMessage(context.getString(R.string.unstar_success));
                } else {
                    showDefaultErrorMessage();
                }
            }
        });
    }

    private void makeStarRequest() {
        client.starGift(String.valueOf(gift.getId()))
                .enqueue(new BaseCallback<ResponseBody>(root) {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            binding.giftItemStar.setImageResource(R.drawable.ic_star_white_24dp);
                            gift.setStarred(true);
                            showMessage(context.getString(R.string.star_success));
                        } else {
                            showDefaultErrorMessage();
                        }
                    }
                });
    }
}
