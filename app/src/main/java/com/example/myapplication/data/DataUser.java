package com.example.myapplication.data;

import com.example.myapplication.model.User;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

public class DataUser {
    @Getter
    @Setter
    @SerializedName("accessToken")
    @Expose
    private String accessToken;
    @Getter
    @Setter
    @SerializedName("user")
    @Expose
    private User user;
}
