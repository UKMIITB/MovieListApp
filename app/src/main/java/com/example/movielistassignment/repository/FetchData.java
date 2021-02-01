package com.example.movielistassignment.repository;

import com.example.movielistassignment.helpers.Endpoints;
import com.example.movielistassignment.model.APIResponse;
import com.example.movielistassignment.repository.retrofit.APICall;
import com.example.movielistassignment.repository.retrofit.RetrofitClient;

import io.reactivex.Single;
import retrofit2.Retrofit;

public class FetchData {

    private static FetchData fetchDataInstance;
    Retrofit retrofit;
    APICall apiCall;

    public static FetchData getInstance() {
        if (fetchDataInstance == null) {
            fetchDataInstance = new FetchData();
        }
        return fetchDataInstance;
    }

    public Single<APIResponse> getData() {
        retrofit = RetrofitClient.getRetrofitClientInstance();
        apiCall = retrofit.create(APICall.class);
        return apiCall.getAPIResponse(Endpoints.API_KEY, Endpoints.CONTENT_TYPE);
    }
}
