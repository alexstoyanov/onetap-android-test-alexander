package com.stoyanov.onetap.networking;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit.Ok3Client;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

/**
 * Created by alexander on 6/3/16.
 */
public class RestServiceCreator {
        private static final String ENDPOINT = "http://api.openweathermap.org";
        private static RestAdapter adapter;
        private static OneTapDataService oneTapDataService;

        private static RestAdapter getRestAdapter() {
            if (adapter == null) {
                RestAdapter.Builder adapterBuilder = new RestAdapter.Builder()
                        .setEndpoint(ENDPOINT)
                        .setClient(new Ok3Client(getClient()))
                        .setConverter(new GsonConverter(new GsonBuilder()
                                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                                .create()));
                adapter = adapterBuilder.build();
            }
            return adapter;
        }

        public static OneTapDataService createDataService() {
            if (oneTapDataService == null) {
                oneTapDataService = getRestAdapter().create(OneTapDataService.class);
            }
            return oneTapDataService;
        }

        private static OkHttpClient getClient() {
            OkHttpClient client = new OkHttpClient();
            client.newBuilder().connectTimeout(15 * 1000, TimeUnit.SECONDS)
                    .readTimeout(15 * 1000, TimeUnit.SECONDS);
            return client;
        }
}