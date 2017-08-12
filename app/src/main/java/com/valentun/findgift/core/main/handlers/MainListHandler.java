package com.valentun.findgift.core.main.handlers;

import android.view.View;

import com.valentun.findgift.R;
import com.valentun.findgift.databinding.GiftRecyclerItemBinding;
import com.valentun.findgift.models.Gift;
import com.valentun.findgift.network.callback.SnackBarCallback;

@SuppressWarnings("unchecked")
public class MainListHandler extends AbstractGiftListHandler {

    public MainListHandler(GiftRecyclerItemBinding binding, Gift gift) {
        super(binding, gift,  null);
    }

    @Override
    public void onFabClicked(View view) {
        if (gift.isStarred()) {
            binding.giftItemStar.setImageResource(R.drawable.ic_star_border_white_24dp);
            makeUnStarRequest();
            gift.setStarred(false);
        } else {
            binding.giftItemStar.setImageResource(R.drawable.ic_star_white_24dp);
            makeStarRequest();
            gift.setStarred(true);
        }
    }

    private void makeStarRequest() {
        client.starGift(String.valueOf(gift.getId()))
                .enqueue(new SnackBarCallback(root, context.getString(R.string.star_success)));
    }
}
