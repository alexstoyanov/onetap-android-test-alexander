package com.stoyanov.onetap.networking;

import com.stoyanov.onetap.model.ForecastResponse;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by alexander on 6/3/16.
 */
public interface OneTapDataService {

    @GET("/data/2.5/forecast/daily")
    void getForecast(@Query("lon") double longitude,
                     @Query("lat") double latitude,
                     @Query("cnt") int daysCount,
                     @Query("APPID") String appId,
                     Callback<ForecastResponse> callback);

}
