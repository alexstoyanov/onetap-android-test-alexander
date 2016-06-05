package com.stoyanov.onetap.networking;

import android.content.Context;

import com.stoyanov.onetap.model.ForecastResponse;
import com.stoyanov.onetap.networking.events.OnForecastLoadedEvent;
import com.stoyanov.onetap.utils.BusProvider;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Header;
import retrofit.client.Response;

/**
 * Created by alexander on 6/4/16.
 */
public class OneTabDataProvider {
    private static OneTabDataProvider instance;
    private Context appContext;

    private OneTabDataProvider(Context context) {
        appContext = context.getApplicationContext();
    }

    public static synchronized OneTabDataProvider getInstance(Context context) {
        if (instance == null) {
            instance = new OneTabDataProvider(context);
        }

        return instance;
    }

    public void getForecast(double longitude, double latitude, int daysCount, String appId) {
        RestServiceCreator.createDataService().getForecast(longitude, latitude, daysCount, appId,
                new Callback<ForecastResponse>() {
                    @Override
                    public void success(ForecastResponse forecastResponse, Response response) {
                        BusProvider.getInstance().post(
                                new OnForecastLoadedEvent(forecastResponse.getDailyForecastList(), forecastResponse.getDaysCount()));
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        BusProvider.getInstance().post(new OnForecastLoadedEvent(null, 0));
                    }
                });
    }
}
