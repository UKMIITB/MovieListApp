package com.example.movielistassignment.repository.retrofit;

import com.example.movielistassignment.model.APIResponse;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface APICall {
    @GET("4/list/1")
    Single<APIResponse> getAPIResponse(@Query("api_key") String API_KEY, @Header("content-type") String CONTENT_TYPE);
}
