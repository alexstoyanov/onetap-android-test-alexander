package com.stoyanov.onetap.ui.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.stoyanov.onetap.R;
import com.stoyanov.onetap.adapters.TabsPagerAdapter;
import com.stoyanov.onetap.adapters.ViewPagerAdapter;
import com.stoyanov.onetap.ui.fragments.ForecastFragment;
import com.stoyanov.onetap.ui.views.IndicatorViewPager;

import butterknife.BindView;

public class CatsActivity extends BaseActivity {
    private static final int FIVE = 5;
    private static final int SIXTEEN = 16;

    @BindView(R.id.pager_header)
    ViewPager pagerHeader;
    @BindView(R.id.grp_pager_indicator)
    IndicatorViewPager pagerIndicator;
    @BindView(R.id.pager_tabs)
    ViewPager pagerTabs;
    @BindView(R.id.tabs)
    TabLayout tabLayout;

    private int[] imageResources = {
            R.drawable.cat0,
            R.drawable.cat1,
            R.drawable.cat2,
            R.drawable.cat3,
            R.drawable.cat4,
            R.drawable.cat5,
            R.drawable.cat6,
            R.drawable.cat7
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ViewPagerAdapter headerImagesAdapter = new ViewPagerAdapter(CatsActivity.this, imageResources);
        pagerIndicator.setDotsCount(headerImagesAdapter.getCount());
        pagerHeader.setAdapter(headerImagesAdapter);
        pagerHeader.setCurrentItem(0);
        pagerHeader.addOnPageChangeListener(pagerIndicator);

        if (pagerTabs != null) {
            setupTabPager(pagerTabs);
        }

        if (tabLayout != null) {
            tabLayout.setupWithViewPager(pagerTabs);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_cats;
    }

    private void setupTabPager(ViewPager viewPager) {
        TabsPagerAdapter TabPagerAdapter = new TabsPagerAdapter(getSupportFragmentManager());
        TabPagerAdapter.addFragment(ForecastFragment.newInstance(FIVE), getString(R.string.five_days));
        TabPagerAdapter.addFragment(ForecastFragment.newInstance(SIXTEEN), getString(R.string.sixteen_days));
        viewPager.setAdapter(TabPagerAdapter);
    }
}
