package com.example.myapplication.network;

import com.example.myapplication.data.DataUser;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static Retrofit retrofit = null;
    static DataUser dataUser = new DataUser();

    public static Retrofit getClient(String url){

        final OkHttpClient okHttpClient = new OkHttpClient().newBuilder().addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request originalRequest = chain.request();
                Request.Builder builder = originalRequest.newBuilder().addHeader("x-auth-token", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjVjZWNhM2IzMDEzYTUyMGY4ZTAwYTVkZiIsImlhdCI6MTU1OTAzOTE0NCwiZXhwIjoxNTU5NjQzOTQ0fQ.L62LySPb5S8gwhxbzIV9ISi9jJ7FSTJTMEvFqW6S9ws");
                Request newRequest = builder.build();
                return chain.proceed(newRequest);
            }
        }).build();

        if (retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build();
        }
        return retrofit;
    }

}
