package com.stoyanov.onetap.ui.activities;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.otto.Subscribe;
import com.stoyanov.onetap.R;
import com.stoyanov.onetap.adapters.ForecastListAdapter;
import com.stoyanov.onetap.adapters.listeners.OnForecastRowClickListener;
import com.stoyanov.onetap.model.DailyForecast;
import com.stoyanov.onetap.model.ForecastResponse;
import com.stoyanov.onetap.networking.OneTabDataProvider;
import com.stoyanov.onetap.networking.RestServiceCreator;
import com.stoyanov.onetap.networking.events.OnForecastLoadedEvent;
import com.stoyanov.onetap.utils.Constants;

import java.util.List;

import butterknife.BindView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by alexander on 6/3/16.
 */
public class ForecastListActivity extends BaseActivity implements OnForecastRowClickListener {
    @BindView(R.id.rv_forecast_list)
    RecyclerView rvForecastList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        rvForecastList.setLayoutManager(new LinearLayoutManager(this));
        requestForecast();
    }

    private void requestForecast() {
        Double longitude = Double.longBitsToDouble(prefs.getLong(Constants.LONGITUDE, -1));
        Double latitude = Double.longBitsToDouble(prefs.getLong(Constants.LATITUDE, -1));
        if (!longitude.isNaN() && !latitude.isNaN()
                && longitude != -1 && latitude != -1) {
            OneTabDataProvider.getInstance(this).
                    getForecast(longitude, latitude, Constants.FORECAST_DAYS_COUNT, Constants.APP_ID);
        } else {
            Toast.makeText(this, R.string.no_location, Toast.LENGTH_SHORT).show();
        }

    }

    private void showCustomSnackBar(double pressure) {
        Snackbar snackbar = Snackbar.make(rvForecastList, "" + pressure, Snackbar.LENGTH_LONG);
        Snackbar.SnackbarLayout layout = (Snackbar.SnackbarLayout) snackbar.getView();
        TextView textView = (TextView) layout.findViewById(android.support.design.R.id.snackbar_text);
        layout.setBackgroundColor(ContextCompat.getColor(this, R.color.color_primary));
        textView.setText(Html.fromHtml(
                String.format(getString(R.string.pressure_value), pressure)));
        snackbar.show();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_forecast_list;
    }

    @Override
    public void onForecastRowClicked(double pressure) {
        showCustomSnackBar(pressure);
    }


    @Subscribe
    public void onForecastLoaded(OnForecastLoadedEvent e) {
        if (e != null && e.getDailyForecastList() != null) {
            ForecastListAdapter adapter =
                    new ForecastListAdapter(ForecastListActivity.this, e.getDailyForecastList());
            rvForecastList.setAdapter(adapter);
        } else {
            Toast.makeText(this, R.string.no_forecast_for_location, Toast.LENGTH_SHORT).show();
        }
    }
}
