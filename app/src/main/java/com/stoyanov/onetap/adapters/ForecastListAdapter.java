package com.stoyanov.onetap.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.stoyanov.onetap.R;
import com.stoyanov.onetap.adapters.listeners.OnForecastRowClickListener;
import com.stoyanov.onetap.model.DailyForecast;
import com.stoyanov.onetap.model.Weather;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by alexander on 6/3/16.
 */
public class ForecastListAdapter extends RecyclerView.Adapter<ForecastListAdapter.ForecastViewHolder> {

    private List<DailyForecast> dailyForecastList;
    private OnForecastRowClickListener forecastRowClickedListener;

    public ForecastListAdapter(OnForecastRowClickListener listener, List<DailyForecast> dailyForecastList) {
        this.dailyForecastList = dailyForecastList;
        forecastRowClickedListener = listener;
    }

    @Override
    public ForecastViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.row_forecast_list, parent, false);
        return new ForecastViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ForecastViewHolder holder, int position) {
        final DailyForecast dailyForecast = dailyForecastList.get(position);
        if (dailyForecast.getWeatherList() != null) {
            Weather weather = dailyForecast.getWeatherList().get(0);
            if (weather != null) {
                holder.txtForecastTitle.setText(weather.getMain());
                holder.txtForecastSubtitle.setText(weather.getDescription());

                holder.grpForecastRow.setOnClickListener(getOnClickListener(dailyForecast.getPressure()));
            }
        }

        holder.grpForecastRow.setTag(position);
    }

    public View.OnClickListener getOnClickListener(final double pressure) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forecastRowClickedListener.onForecastRowClicked(pressure);
            }
        };
    }

    @Override
    public int getItemCount() {
        return dailyForecastList.size();
    }

    public static class ForecastViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.grp_forecast_row)
        LinearLayout grpForecastRow;
        @BindView(R.id.txt_forecast_title)
        TextView txtForecastTitle;
        @BindView(R.id.txt_forecast_subtitle)
        TextView txtForecastSubtitle;

        ForecastViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
