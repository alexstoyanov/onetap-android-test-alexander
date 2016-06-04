package com.stoyanov.onetap.networking.events;

import com.stoyanov.onetap.model.DailyForecast;

import java.util.List;

/**
 * Created by alexander on 6/4/16.
 */
public class OnForecastLoadedEvent {
    private List<DailyForecast> dailyForecastList;

    public OnForecastLoadedEvent(List<DailyForecast> dailyForecastList) {
        this.dailyForecastList = dailyForecastList;
    }

    public List<DailyForecast> getDailyForecastList() {
        return dailyForecastList;
    }

    public void setDailyForecastList(List<DailyForecast> dailyForecastList) {
        this.dailyForecastList = dailyForecastList;
    }
}
