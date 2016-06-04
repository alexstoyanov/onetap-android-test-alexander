package com.stoyanov.onetap.ui.activities;

import android.content.Intent;
import android.os.Bundle;

import com.stoyanov.onetap.R;

import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @OnClick(R.id.btn_cats)
    public void onBtnCatsClicked() {
        Intent toCatsActivity = new Intent(this, CatsActivity.class);
        startActivity(toCatsActivity);
    }

    @OnClick(R.id.btn_forecast)
    public void onBtnForecastClicked() {
        Intent toForecastActivity = new Intent(this, ForecastListActivity.class);
        startActivity(toForecastActivity);
    }
}
