package com.example.myapplication.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

public class News {
    @SerializedName("_id")
    @Expose
    @Setter
    @Getter
    private String id;
    @SerializedName("title")
    @Expose
    @Setter
    @Getter
    private String title;
    @SerializedName("description")
    @Expose
    @Setter
    @Getter
    private String description;
    @SerializedName("image")
    @Expose
    @Setter
    @Getter
    private String image;
    @SerializedName("url")
    @Expose
    @Setter
    @Getter
    private String url;
}
