package com.valentun.findgift.core.main.adapters;

import android.databinding.DataBindingUtil;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.valentun.findgift.R;
import com.valentun.findgift.core.main.handlers.AbstractGiftListHandler;
import com.valentun.findgift.databinding.GiftRecyclerItemBinding;
import com.valentun.findgift.models.Gift;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public abstract class AbstractGiftAdapter extends RecyclerView.Adapter<AbstractGiftAdapter.MainViewHolder> {
    protected List<Gift> data;
    private RemoveItemListener removeListener;

    public interface RemoveItemListener {
        void onItemRemoved(int dataSize);
    }

    public AbstractGiftAdapter(List<Gift> data, RemoveItemListener listener) {
        this.data = data;
        removeListener = listener;
    }

    @Override
    public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.gift_recycler_item, parent, false);
        return new MainViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MainViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        if (data == null) return 0;
        return data.size();
    }

    public void removeAt(int position) {
        data.remove(position);
        notifyItemRemoved(position);
        if (removeListener != null) removeListener.onItemRemoved(data.size());
    }

    public int getPositionOf(Gift gift) {
        return data.indexOf(gift);
    }

    class MainViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_image)
        ImageView image;
        GiftRecyclerItemBinding binding;

        MainViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
            binding = DataBindingUtil.bind(itemView);
        }

        void bind(int position) {
            Gift gift = data.get(position);

            int colorId = gift.isLiked() ? R.color.primary : R.color.ic_color;
            int color = ContextCompat.getColor(itemView.getContext(), colorId);

            binding.voteUp.setColorFilter(color);
            binding.itemRate.setTextColor(color);

            binding.setGift(gift);
            binding.giftItemStar.setImageResource(getFabImageId(gift));
            binding.setHandlers(getHandler(binding, gift));

            Picasso.with(itemView.getContext())
                    .load(gift.getImageUrl())
                    .placeholder(R.color.placeholder_color)
                    .into(image);
        }
    }

    abstract int getFabImageId(Gift gift);

    abstract AbstractGiftListHandler getHandler(GiftRecyclerItemBinding binding, Gift gift);
}
