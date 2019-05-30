package com.example.myapplication.data;

import com.example.myapplication.model.Notification;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class DataNotification {
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
    private List<Notification> data = null;
}
