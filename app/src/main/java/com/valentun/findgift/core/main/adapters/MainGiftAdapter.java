package com.valentun.findgift.core.main.adapters;

import com.valentun.findgift.R;
import com.valentun.findgift.core.main.handlers.AbstractGiftListHandler;
import com.valentun.findgift.core.main.handlers.MainListHandler;
import com.valentun.findgift.databinding.GiftRecyclerItemBinding;
import com.valentun.findgift.models.Gift;

import java.util.List;

public class MainGiftAdapter extends AbstractGiftAdapter {
    public MainGiftAdapter(List<Gift> data) {
        super(data, null);
    }

    @Override
    int getFabImageId(Gift gift) {
        return gift.isStarred() ? R.drawable.ic_star_white_24dp
                                : R.drawable.ic_star_border_white_24dp;
    }

    @Override
    AbstractGiftListHandler getHandler(GiftRecyclerItemBinding binding, Gift gift) {
        return new MainListHandler(binding, gift);
    }
}
