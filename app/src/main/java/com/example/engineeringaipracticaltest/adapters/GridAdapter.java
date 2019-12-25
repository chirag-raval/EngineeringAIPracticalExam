package com.example.engineeringaipracticaltest.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.engineeringaipracticaltest.R;

import java.util.List;


public class GridAdapter extends RecyclerView.Adapter {

    Activity activity;
    List<String> userItems;

    GridAdapter(Activity activity,List<String> userItems)
    {
        this.activity = activity;
        this.userItems = userItems;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view =  LayoutInflater.from(activity).inflate(R.layout.item_user_items,parent,false);
        ItemViewHolder itemViewHolder = new ItemViewHolder(view);
        return itemViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
        String userImageItem = userItems.get(position);
        Glide.with(activity).load(userImageItem).placeholder(R.drawable.ic_placeholder_24dp).into(itemViewHolder.imageViewUserItem);
    }

    @Override
    public int getItemCount() {
        return userItems.size();
    }

    public  class ItemViewHolder extends RecyclerView.ViewHolder
    {
        ImageView imageViewUserItem;

        ItemViewHolder(View view)
        {
            super(view);
            imageViewUserItem = view.findViewById(R.id.imageViewUserItem);
        }
    }
}
