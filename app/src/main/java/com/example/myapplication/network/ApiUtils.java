package com.example.myapplication.network;

public class ApiUtils {
    public static final String BASE_URL = "http://18.234.222.106:3011";
    public static final String GOOGLE_API_URL = "https://maps.googleapis.com/";

    public static UserService getUsernameService(){
        return RetrofitClient.getClient(BASE_URL).create(UserService.class);
    }

    public static UserService getUsernameServiceScalars(){
        return RetrofitScalarsClient.getScalarsClient(GOOGLE_API_URL).create(UserService.class);
    }
}
