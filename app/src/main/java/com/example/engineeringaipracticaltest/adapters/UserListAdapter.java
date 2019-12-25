package com.example.engineeringaipracticaltest.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.engineeringaipracticaltest.R;
import com.example.engineeringaipracticaltest.models.ClsUserResponse;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserListAdapter extends RecyclerView.Adapter {


    Activity activity;
    List<ClsUserResponse.Data.User> userList;

    public static final int SPAN_COUNT = 2;

    public UserListAdapter(Activity activity, List<ClsUserResponse.Data.User> userList)
    {
        this.activity = activity;
        this.userList = userList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view =  LayoutInflater.from(activity).inflate(R.layout.user_list,parent,false);
        ItemViewHolder itemViewHolder = new ItemViewHolder(view);
        return itemViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        ItemViewHolder itemViewHolder = (ItemViewHolder) holder;

        ClsUserResponse.Data.User clsUserDetails = userList.get(position);
        Glide.with(activity).load(clsUserDetails.getImage()).into(itemViewHolder.imageViewProfile);
        itemViewHolder.textViewUserName.setText(clsUserDetails.getName());

        // Here we set the item Adapter to the user
        if(clsUserDetails.getItems() != null && clsUserDetails.getItems().size() != 0)
        {
            setItemAdapter(itemViewHolder.recyclerViewUserItems, clsUserDetails.getItems());
        }

    }

    private void setItemAdapter(RecyclerView recyclerViewItems, final List<String> items)
    {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(activity,SPAN_COUNT);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {

                if(items.size() % 2 == 0)
                {
                    return 1;
                }
                else if(position == 0)
                {
                    return 2;
                }
                else
                {
                    return 1;
                }
            }
        });

        // Now here we would require the Grid Adapter

        GridAdapter gridAdapter = new GridAdapter(activity,items);
        recyclerViewItems.setLayoutManager(gridLayoutManager);
        recyclerViewItems.setAdapter(gridAdapter);
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public void setUserList(List<ClsUserResponse.Data.User> newItems)
    {
        this.userList = newItems;
    }

    public  class ItemViewHolder extends RecyclerView.ViewHolder
    {
        CircleImageView imageViewProfile;
        TextView textViewUserName;
        RecyclerView recyclerViewUserItems;

        ItemViewHolder(View view)
        {
            super(view);
            imageViewProfile = view.findViewById(R.id.imageViewProfile);
            textViewUserName = view.findViewById(R.id.textViewUserName);
            recyclerViewUserItems = view.findViewById(R.id.recyclerViewUserItems);
        }
    }
}
