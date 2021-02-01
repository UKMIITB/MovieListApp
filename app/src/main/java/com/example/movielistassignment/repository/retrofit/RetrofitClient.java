package com.example.movielistassignment.repository.retrofit;

import com.example.movielistassignment.helpers.Endpoints;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static Retrofit retrofitClientInstance;

    public static Retrofit getRetrofitClientInstance() {
        if (retrofitClientInstance == null)
            retrofitClientInstance = new Retrofit.Builder()
                    .baseUrl(Endpoints.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();

        return retrofitClientInstance;
    }
}