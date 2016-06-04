package com.stoyanov.onetap.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.stoyanov.onetap.utils.BusProvider;

import butterknife.ButterKnife;

/**
 * Created by alexander on 6/3/16.
 */
public abstract class BaseFragment extends Fragment {

    protected abstract void initUI();

    protected abstract int getLayoutId();

    protected abstract String getFragmentTag();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(getLayoutId(), container, false);
        ButterKnife.bind(this, rootView);
        initUI();

        return rootView;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        BusProvider.getInstance().register(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        BusProvider.getInstance().unregister(this);
    }
}
