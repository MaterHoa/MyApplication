package com.example.myapplication.network;

public class ApiUtils {
    public static final String BASE_URL = "http://18.234.222.106:3011";

    public static UserService getUsernameService(){
        return RetrofitClient.getClient(BASE_URL).create(UserService.class);
    }
}
