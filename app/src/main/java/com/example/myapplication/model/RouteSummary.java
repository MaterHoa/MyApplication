package com.example.myapplication.model;

import lombok.Getter;
import lombok.Setter;

public class RouteSummary {
    @Getter
    @Setter
    private String startName;
    @Getter
    @Setter
    private String endName;
    @Getter
    @Setter
    private Double startLat;
    @Getter
    @Setter
    private Double startLng;
    @Getter
    @Setter
    private Double endLat;
    @Getter
    @Setter
    private Double endLng;
    @Getter
    @Setter
    private String overviewPolyline;
    public RouteSummary(String startName, String endName, Double startLat, Double startLng, Double endLat, Double endLng, String overviewPolyline) {
        this.startName = startName;
        this.endName = endName;
        this.startLat = startLat;
        this.startLng = startLng;
        this.endLat = endLat;
        this.endLng = endLng;
        this.overviewPolyline = overviewPolyline;
    }
}
