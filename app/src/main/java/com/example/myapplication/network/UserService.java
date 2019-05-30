package com.example.myapplication.network;

import com.example.myapplication.data.DataCheck;
import com.example.myapplication.data.DataNews;
import com.example.myapplication.data.DataNotification;
import com.example.myapplication.data.DataResponse;
import com.example.myapplication.data.DataStores;
import com.example.myapplication.data.DataUser;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface UserService {

    @FormUrlEncoded
    @POST("exists")
    Call<DataCheck> checkUsername(@Field("username") String username);

    @FormUrlEncoded
    @POST("login")
    Call<DataResponse<DataUser>> checkUser(@Field("username") String username, @Field("password") String password);

    @FormUrlEncoded
    @POST("register")
    Call<DataResponse<DataUser>> registerRequest(@Field("username") String username, @Field("name") String name, @Field("password") String password);

    @GET("news")
    Call<DataNews> getNews();

    @GET("stores")
    Call<DataStores> getStores();

    @GET("notifications")
    Call<DataNotification> getNotification(@Header("x-auth-token") String accessToken);

}

