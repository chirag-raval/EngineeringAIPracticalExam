package com.example.engineeringaipracticaltest.network;

import androidx.annotation.NonNull;

import com.example.engineeringaipracticaltest.BuildConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RetrofitInterface {
    private static Retrofit retrofit = null;
    private static int CONNECTION_TIME_OUT = 60;

    public static RestApiMethods getRestApiMethods() {
        return createRetrofit().create(RestApiMethods.class);
    }

    private static Retrofit createRetrofit() {
        OkHttpClient.Builder httpClient = getBuilder();
        retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();
        return retrofit;
    }

    @NonNull
    private static OkHttpClient.Builder getBuilder() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();

        final OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        httpClient.connectTimeout(CONNECTION_TIME_OUT * 3L, TimeUnit.SECONDS)
                .readTimeout(CONNECTION_TIME_OUT * 3L, TimeUnit.SECONDS)
                .writeTimeout(CONNECTION_TIME_OUT * 3L, TimeUnit.SECONDS);

        // add logging as last interceptor
        // set your desired log level

        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClient.addInterceptor(logging);
        return httpClient;
    }
}