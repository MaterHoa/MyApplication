package com.example.myapplication.data;

import com.example.myapplication.model.Data;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

public class DataCheck {
    @SerializedName("code")
    @Expose
    @Getter
    @Setter
    private Integer code;
    @SerializedName("message")
    @Expose
    @Getter
    @Setter
    private String message;
    @SerializedName("data")
    @Expose
    @Getter
    @Setter
    private Data data;
}
