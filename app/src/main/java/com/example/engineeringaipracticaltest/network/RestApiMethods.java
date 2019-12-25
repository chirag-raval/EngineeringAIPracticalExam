package com.example.engineeringaipracticaltest.network;

import com.example.engineeringaipracticaltest.models.ClsUserResponse;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface RestApiMethods
{
    @GET("users")
    Call<ClsUserResponse> getList(@Query("offset") int pageNo, @Query("limit") int limit);
}