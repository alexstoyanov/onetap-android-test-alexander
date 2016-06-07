package com.stoyanov.onetap.ui.activities;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.view.Display;
import android.view.View;
import android.view.Window;

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
    @BindView(R.id.bottom_sheet)
    NestedScrollView bottomSheet;

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
        bottomSheet.setFillViewport(true);
        final BottomSheetBehavior behavior = BottomSheetBehavior.from(bottomSheet);
        behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        behavior.setPeekHeight(getPeekHeight());
        bottomSheet.setNestedScrollingEnabled(true);
        behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(View bottomSheet, int newState) {
            }

            @Override
            public void onSlide(View bottomSheet, float slideOffset) {
            }
        });

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

    public int getPeekHeight() {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int statusBarHeight = getStatusBarHeight(this);
        int height = (int) (size.y - getResources().getDimension(R.dimen.expanded_header_height) - statusBarHeight);
        return height;
    }

    public static int getStatusBarHeight(Context context) {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            return resources.getDimensionPixelSize(resourceId);
        }
        return 0;
    }
}
