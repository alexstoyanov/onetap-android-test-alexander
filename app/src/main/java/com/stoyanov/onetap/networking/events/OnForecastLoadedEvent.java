package com.stoyanov.onetap.networking.events;

import com.stoyanov.onetap.model.DailyForecast;

import java.util.List;

/**
 * Created by alexander on 6/4/16.
 */
public class OnForecastLoadedEvent {
    private List<DailyForecast> dailyForecastList;
    private int daysCount;

    public OnForecastLoadedEvent(List<DailyForecast> dailyForecastList, int daysCount) {
        this.dailyForecastList = dailyForecastList;
        this.daysCount = daysCount;
    }

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
