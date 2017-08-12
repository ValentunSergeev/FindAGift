package com.valentun.findgift.core.main.handlers;

import android.support.design.widget.Snackbar;
import android.view.View;

import com.valentun.findgift.R;
import com.valentun.findgift.core.main.adapters.AbstractGiftAdapter;
import com.valentun.findgift.databinding.GiftRecyclerItemBinding;
import com.valentun.findgift.models.Gift;
import com.valentun.findgift.network.callback.BaseCallback;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

public class StarredListHandler extends AbstractGiftListHandler {
    public StarredListHandler(GiftRecyclerItemBinding binding, Gift gift, AbstractGiftAdapter adapter) {
        super(binding, gift, adapter);
    }

    @Override
    public void onFabClicked(View view) {
        makeUnStarRequest();
    }

    @Override
    void makeUnStarRequest() {
        client.unstarGift((String.valueOf(gift.getId()))).enqueue(new BaseCallback<ResponseBody>(root) {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Snackbar.make(root, context.getString(R.string.unstar_success), Snackbar.LENGTH_LONG)
                        .show();
                adapter.removeAt(adapter.getPositionOf(gift));
                gift.setStarred(false);
            }
        });
    }
}
