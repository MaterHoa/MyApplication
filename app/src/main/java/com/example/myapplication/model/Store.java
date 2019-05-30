package com.example.myapplication.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

public class Store {
    @SerializedName("_id")
    @Expose
    @Setter
    @Getter
    private String id;
    @SerializedName("name")
    @Expose
    @Setter
    @Getter
    private String name;
    @SerializedName("address")
    @Expose
    @Setter
    @Getter
    private String address;
    @SerializedName("lat")
    @Expose
    @Setter
    @Getter
    private double lat;
    @SerializedName("long")
    @Expose
    @Setter
    @Getter
    private double longt;
}
