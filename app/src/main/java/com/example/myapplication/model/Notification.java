package com.example.myapplication.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

public class Notification {
    @SerializedName("_id")
    @Expose
    @Setter
    @Getter
    private int id;
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
}
