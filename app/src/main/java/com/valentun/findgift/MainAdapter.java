package com.valentun.findgift;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainViewHolder> {
    private ArrayList<Gift> data;

    public MainAdapter(ArrayList<Gift> data) {
        this.data = data;
    }

    @Override
    public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.main_recycler_item, parent, false);
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
        @BindView(R.id.main_item_icon) ImageView image;
        @BindView(R.id.item_price) TextView price;
        @BindView(R.id.item_name) TextView name;

        public MainViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(itemView);
        }

        void bind(int position) {
            Gift gift = data.get(position);


        }
    }
}
