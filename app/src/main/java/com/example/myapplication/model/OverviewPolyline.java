package com.example.myapplication.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

class OverviewPolyline {
    @Setter
    @Getter
    @SerializedName("points")
    @Expose
    private String points;
}
