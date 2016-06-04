package com.stoyanov.onetap.ui.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.stoyanov.onetap.R;
import com.stoyanov.onetap.adapters.TabsPagerAdapter;
import com.stoyanov.onetap.adapters.ViewPagerAdapter;
import com.stoyanov.onetap.ui.fragments.CatNamesFragment;
import com.stoyanov.onetap.ui.views.IndicatorViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class CatsActivity extends BaseActivity {
    @BindView(R.id.pager_header)
    ViewPager pagerHeader;
    @BindView(R.id.grp_pager_indicator)
    IndicatorViewPager pagerIndicator;
    @BindView(R.id.pager_tabs)
    ViewPager pagerTabs;
    @BindView(R.id.tabs)
    TabLayout tabLayout;

    private int[] mImageResources = {
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

        ViewPagerAdapter headerImagesAdapter = new ViewPagerAdapter(CatsActivity.this, mImageResources);
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
        TabPagerAdapter.addFragment(new CatNamesFragment(), "tab 1");
        TabPagerAdapter.addFragment(new CatNamesFragment(), "tab 2");
        TabPagerAdapter.addFragment(new CatNamesFragment(), "tab 3");
        viewPager.setAdapter(TabPagerAdapter);
    }
}
