package com.stoyanov.onetap.ui.views;

import android.content.Context;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.stoyanov.onetap.R;

/**
 * Created by alexander on 6/3/16.
 */
public class IndicatorViewPager extends LinearLayout implements ViewPager.OnPageChangeListener {
    private int dotsCount;
    private ImageView[] dots;
    private Context context;

    public IndicatorViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public IndicatorViewPager(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }

    public IndicatorViewPager(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        for (int i = 0; i < dotsCount; i++) {
            dots[i].setImageDrawable(ResourcesCompat.
                    getDrawable(getResources(), R.drawable.not_selected_dot, null));
        }

        dots[position].setImageDrawable(ResourcesCompat.
                getDrawable(getResources(), R.drawable.selected_dot, null));
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public void setDotsCount(int dotsCount) {
        this.dotsCount = dotsCount;
        dots = new ImageView[dotsCount];

        for (int i = 0; i < dotsCount; i++) {
            dots[i] = new ImageView(context);
            dots[i].setImageDrawable(ResourcesCompat.
                    getDrawable(getResources(), R.drawable.not_selected_dot, null));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );

            params.setMargins(4, 0, 4, 0);

            addView(dots[i], params);
        }

        dots[0].setImageDrawable(ResourcesCompat.
                getDrawable(getResources(), R.drawable.selected_dot, null));
    }
}
