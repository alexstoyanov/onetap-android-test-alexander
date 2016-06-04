package com.stoyanov.onetap.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by alexander on 6/3/16.
 */
public class DailyForecast {
    @SerializedName("pressure")
    private double pressure;
    @SerializedName("weather")
    private List<Weather> weatherList;

    public double getPressure() {
        return pressure;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    public List<Weather> getWeatherList() {
        return weatherList;
    }

    public void setWeatherList(List<Weather> weatherList) {
        this.weatherList = weatherList;
    }
}