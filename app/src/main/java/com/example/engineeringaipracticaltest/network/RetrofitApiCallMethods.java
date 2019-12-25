package com.example.engineeringaipracticaltest.network;

import android.app.Activity;

import com.example.engineeringaipracticaltest.R;
import com.example.engineeringaipracticaltest.models.ClsUserResponse;
import com.example.engineeringaipracticaltest.utility.Utility;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RetrofitApiCallMethods
{
    private Activity mContext;

    public RetrofitApiCallMethods(Activity mContext)
    {
        this.mContext = mContext;
    }
    private void retrofitSuccess(Response response,
                                 final OnApiCompleted onApiCompleted)
    {
        if (response.code() == 200)
        {
            try
            {
                onApiCompleted.onApiSuccessResponse(response.body());
            }
            catch (Exception e)
            {
                e.printStackTrace();
                onApiCompleted.onApiFailureResponse(mContext.getString(R.string.try_later));
            }
        }
        else
        {
            try
            {
                // Utility.log("API ERROR",response.body()+"" );
                JSONObject object = new JSONObject(response.errorBody().string());
                onApiCompleted.onApiFailureResponse(object.opt("message"));
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    private void retrofitFailure(Throwable t, final OnApiCompleted onApiCompleted, String msg)
    {
        Utility.log("Response ERROR ", ":" + t.getMessage());
        onApiCompleted.onApiFailureResponse(msg);
    }

    public void getUsers(int pageNumber, int limit ,final OnApiCompleted onApiCompleted)
    {
        Call<ClsUserResponse> call = RetrofitInterface.getRestApiMethods()
                .getList(pageNumber,limit);
        call.enqueue(new Callback<ClsUserResponse>()
        {
            @Override
            public void onResponse(Call<ClsUserResponse> call, Response<ClsUserResponse> response)
            {
                retrofitSuccess(response, onApiCompleted);
            }

            @Override
            public void onFailure(Call<ClsUserResponse> call, Throwable t)
            {
                retrofitFailure(t, onApiCompleted, t.getMessage());
            }
        });
    }
}