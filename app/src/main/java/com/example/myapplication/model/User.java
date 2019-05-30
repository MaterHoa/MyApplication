package com.example.myapplication.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

public class User {
    @Setter
    @Getter
    @SerializedName("_id")
    @Expose
    private String id;
    @Setter
    @Getter
    @SerializedName("email")
    @Expose
    private String email;
    @Setter
    @Getter
    @SerializedName("phone")
    @Expose
    private String phone;
    @Setter
    @Getter
    @SerializedName("name")
    @Expose
    private String name;
    @Setter
    @Getter
    @SerializedName("avatar")
    @Expose
    private String avatar;
    @Setter
    @Getter
    @SerializedName("role")
    @Expose
    private String role;
    @Setter
    @Getter
    @SerializedName("created")
    @Expose
    private String created;
    @Setter
    @Getter
    @SerializedName("__v")
    @Expose
    private Integer v;
    @Setter
    @Getter
    @SerializedName("isVIP")
    @Expose
    private Boolean isVIP;
}
