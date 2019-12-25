package com.example.engineeringaipracticaltest;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.engineeringaipracticaltest.adapters.UserListAdapter;
import com.example.engineeringaipracticaltest.models.ClsUserResponse;
import com.example.engineeringaipracticaltest.network.OnApiCompleted;
import com.example.engineeringaipracticaltest.network.RetrofitApiCallMethods;
import com.example.engineeringaipracticaltest.utility.EndLessRecyclerViewScrollListener;
import com.example.engineeringaipracticaltest.utility.Utility;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerViewUser;
    private ProgressBar progressBarBottom;

    private int pageOffset = 1;
    private static final int RECORD_LIMIT = 10;

    private ClsUserResponse clsUserResponse;
    List<ClsUserResponse.Data.User> userList = new ArrayList<>();

    private UserListAdapter userListAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initControls();
    }

    /*
    This method is used to initalize the view
     */

    private void initControls() {

        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        recyclerViewUser = findViewById(R.id.recyclerViewUser);
        progressBarBottom = findViewById(R.id.progressBarBottom);

        swipeRefreshLayout.setOnRefreshListener(this);

        // Here we call the web service for the first time
        callUserListWebService();

    }

    private void callUserListWebService() {

        if (Utility.isNetworkAvailable(this)) {
            if (pageOffset > 1) {
                // Here we show the bottom progress
                showBottomProgress(progressBarBottom, View.VISIBLE);
            } else {
                Utility.showProgress(this, false);
            }

            RetrofitApiCallMethods retrofitApiCallMethods = new RetrofitApiCallMethods(this);
            retrofitApiCallMethods.getUsers(pageOffset, RECORD_LIMIT, new OnApiCompleted() {
                @Override
                public void onApiSuccessResponse(Object objects) {
                    // Here we parse the object
                    parseUserListResponse(objects);
                }

                @Override
                public void onApiFailureResponse(Object objects) {

                    // Here we show message to user
                    showBottomProgress(progressBarBottom, View.GONE);
                    Utility.cancelProgress();
                    Toast.makeText(MainActivity.this, R.string.try_later, Toast.LENGTH_LONG).show();

                }
            });
        } else {
            Toast.makeText(this, getString(R.string.check_connection), Toast.LENGTH_SHORT).show();
        }

    }

    private void parseUserListResponse(Object responseString) {

        if (responseString != null) {
            clsUserResponse = (ClsUserResponse) responseString;
            // Here we set the Adapter
            showBottomProgress(progressBarBottom, View.GONE);
            Utility.cancelProgress();
            setAdapter();
        }
    }

    private void setAdapter() {


        userList.addAll(clsUserResponse.getData().getUsers());

        if (clsUserResponse.getData().getHasMore()) {
            pageOffset++;

            if (userListAdapter == null) {
                userListAdapter = new UserListAdapter(MainActivity.this, userList);
                recyclerViewUser.setLayoutManager(getLayoutManager());
                recyclerViewUser.setAdapter(userListAdapter);

                recyclerViewUser.addItemDecoration(new DividerItemDecoration(recyclerViewUser.getContext(),DividerItemDecoration.VERTICAL));

                recyclerViewUser.addOnScrollListener(endLessRecyclerViewScrollListener);
            } else {
                userListAdapter.setUserList(userList);
                userListAdapter.notifyDataSetChanged();
            }
        }
        else
        {
            pageOffset = 0;
            showBottomProgress(progressBarBottom, View.GONE);
            Toast.makeText(MainActivity.this, getString(R.string.no_data),Toast.LENGTH_LONG).show();
        }
    }

    private void showBottomProgress(ProgressBar progressBarBottom, int visible) {
        if (progressBarBottom != null) {
            progressBarBottom.setVisibility(visible);
        }
    }

    private RecyclerView.LayoutManager getLayoutManager() {
        if (layoutManager == null) {
            layoutManager = new LinearLayoutManager(this);
        }

        return layoutManager;
    }

    @Override
    public void onRefresh() {

        swipeRefreshLayout.setRefreshing(false);
        if (userList != null && userList.size() != 0) {
            userList.clear();
            userListAdapter.notifyDataSetChanged();
        }

        pageOffset = 1;

        callUserListWebService();
    }


    EndLessRecyclerViewScrollListener endLessRecyclerViewScrollListener = new EndLessRecyclerViewScrollListener((LinearLayoutManager) getLayoutManager()) {
        @Override
        public void onLoadMore(int page, int totalItemsCount) {

            if (pageOffset == 0) {
                showBottomProgress(progressBarBottom, View.GONE);
            } else {
                callUserListWebService();
            }
        }
    };

}
