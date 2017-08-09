package com.valentun.findgift.core.main;

import android.databinding.DataBindingUtil;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.valentun.findgift.R;
import com.valentun.findgift.databinding.GiftRecyclerItemBinding;
import com.valentun.findgift.models.Gift;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainViewHolder> {
    private List<Gift> data;

    public MainAdapter(List<Gift> data) {
        this.data = data;
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
        if(data == null) return 0;
        return data.size();
    }

    class MainViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_image) ImageView image;
        GiftRecyclerItemBinding binding;

        public MainViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
            binding = DataBindingUtil.bind(itemView);
        }

        void bind(int position) {
            Gift gift = data.get(position);

            if(gift.isLiked()) {
                int color = ContextCompat.getColor(itemView.getContext(), R.color.primary);
                binding.voteUp.setColorFilter(color);
                binding.itemRate.setTextColor(color);
            }

            binding.setGift(gift);
            binding.setHandlers(new MainListHandler(binding, gift));

            Picasso.with(itemView.getContext())
                    .load(gift.getImageUrl())
                    .placeholder(R.color.placeholder_color)
                    .into(image);
        }
    }
}
