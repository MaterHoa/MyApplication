package com.example.myapplication.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

public class Data {
    @Getter
    @Setter
    @SerializedName("exists")
    @Expose
    private String exists;

}
