package com.valentun.findgift.core.main.adapters;

import com.valentun.findgift.R;
import com.valentun.findgift.core.main.handlers.AbstractGiftListHandler;
import com.valentun.findgift.core.main.handlers.StarredListHandler;
import com.valentun.findgift.databinding.GiftRecyclerItemBinding;
import com.valentun.findgift.models.Gift;

import java.util.List;

public class StartGiftAdapter extends AbstractGiftAdapter {
    public StartGiftAdapter(List<Gift> data, RemoveItemListener listener) {
        super(data, listener);
    }

    @Override
    int getFabImageId(Gift gift) {
        return R.drawable.ic_delete_white_24dp;
    }

    @Override
    AbstractGiftListHandler getHandler(GiftRecyclerItemBinding binding, Gift gift) {
        return new StarredListHandler(binding, gift, this);
    }
}
