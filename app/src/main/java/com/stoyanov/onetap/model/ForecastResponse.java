package com.stoyanov.onetap.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by alexander on 6/3/16.
 */
public class ForecastResponse {
    @SerializedName("list")
    private List<DailyForecast> dailyForecastList;
    @SerializedName("cnt")
    private int daysCount;

    public List<DailyForecast> getDailyForecastList() {
        return dailyForecastList;
    }

    public void setDailyForecastList(List<DailyForecast> dailyForecastList) {
        this.dailyForecastList = dailyForecastList;
    }

    public int getDaysCount() {
        return daysCount;
    }

    public void setDaysCount(int daysCount) {
        this.daysCount = daysCount;
    }
}
