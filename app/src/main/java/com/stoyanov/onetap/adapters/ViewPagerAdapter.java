package com.stoyanov.onetap.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.stoyanov.onetap.R;

/**
 * Created by alexander on 6/3/16.
 */
public class ViewPagerAdapter extends PagerAdapter {

    private Context context;
    private int[] resources;

    public ViewPagerAdapter(Context mContext, int[] resources) {
        this.context = mContext;
        this.resources = resources;
    }

    @Override
    public int getCount() {
        return resources.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = LayoutInflater.from(context).
                inflate(R.layout.item_header_pager, container, false);

        ImageView imageView = (ImageView) itemView.findViewById(R.id.img_pager_item);

        Glide.with(imageView.getContext())
                .load(resources[position])
                .fitCenter()
                .into(imageView);

        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}