package com.stoyanov.onetap.ui.fragments;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.otto.Subscribe;
import com.stoyanov.onetap.R;
import com.stoyanov.onetap.adapters.ForecastListAdapter;
import com.stoyanov.onetap.adapters.listeners.OnForecastRowClickListener;
import com.stoyanov.onetap.networking.OneTabDataProvider;
import com.stoyanov.onetap.networking.events.OnForecastLoadedEvent;
import com.stoyanov.onetap.networking.events.OnLocationChangedEvent;
import com.stoyanov.onetap.utils.Constants;
import com.stoyanov.onetap.utils.SharedPrefsHelper;

import butterknife.BindView;

/**
 * Created by alexander on 6/3/16.
 */
public class ForecastFragment extends BaseFragment implements OnForecastRowClickListener {
    private static final String TAG = ForecastFragment.class.getSimpleName();
    private static final String DAYS_COUNT = "DaysCount";

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.rv_forecast)
    RecyclerView rvForecast;
    @BindView(R.id.txt_no_location)
    TextView txtNoLocation;

    public ForecastFragment() {
    }

    public static ForecastFragment newInstance(int daysCount) {
        Bundle args = new Bundle();
        args.putInt(DAYS_COUNT, daysCount);
        ForecastFragment fragment = new ForecastFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initUI() {
        rvForecast.setLayoutManager(new LinearLayoutManager(rvForecast.getContext()));
        requestForecast();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_forecast;
    }

    @Override
    protected String getFragmentTag() {
        return TAG;
    }

    private void requestForecast() {
        int daysCount = getArguments().getInt(DAYS_COUNT);
        SharedPrefsHelper prefs = SharedPrefsHelper.getInstance(getContext());
        Double longitude = Double.longBitsToDouble(prefs.getLong(Constants.LONGITUDE, -1));
        Double latitude = Double.longBitsToDouble(prefs.getLong(Constants.LATITUDE, -1));
        if (!longitude.isNaN() && !latitude.isNaN()
                && longitude != -1 && latitude != -1) {
            txtNoLocation.setVisibility(View.GONE);
            toggleProgressBar(true, progressBar, rvForecast);
            OneTabDataProvider.getInstance(getContext()).
                    getForecast(longitude, latitude, daysCount, Constants.APP_ID);
        }else{
            txtNoLocation.setVisibility(View.VISIBLE);
        }
    }

    @Subscribe
    public void onForecastLoaded(OnForecastLoadedEvent event) {
        toggleProgressBar(false, progressBar, rvForecast);
        if (event != null && event.getDailyForecastList() != null) {
            if (getArguments().getInt(DAYS_COUNT) == event.getDaysCount()) {
                ForecastListAdapter adapter =
                        new ForecastListAdapter(ForecastFragment.this, event.getDailyForecastList());
                rvForecast.setAdapter(adapter);
            }
        } else {
            Toast.makeText(getContext(), R.string.no_forecast_for_location, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onForecastRowClicked(double pressure) {
        showCustomSnackBar(pressure);
    }

    @Subscribe
    public void onLocationChanged(OnLocationChangedEvent event){
        requestForecast();
    }

    private void showCustomSnackBar(double pressure) {
        Snackbar snackbar = Snackbar.make(rvForecast, "" + pressure, Snackbar.LENGTH_LONG);
        Snackbar.SnackbarLayout layout = (Snackbar.SnackbarLayout) snackbar.getView();
        TextView textView = (TextView) layout.findViewById(android.support.design.R.id.snackbar_text);
        layout.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.color_primary));
        textView.setText(Html.fromHtml(
                String.format(getString(R.string.pressure_value), pressure)));
        snackbar.show();
    }
}
