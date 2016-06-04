package com.stoyanov.onetap.ui.fragments;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.stoyanov.onetap.R;
import com.stoyanov.onetap.adapters.CatNamesAdapter;

import butterknife.BindView;

/**
 * Created by alexander on 6/3/16.
 */
public class CatNamesFragment extends BaseFragment {
    private static final String TAG = CatNamesFragment.class.getSimpleName();
    @BindView(R.id.rv_cat_names)
    RecyclerView rvCatNames;

    public CatNamesFragment() {
        // Required empty public constructor
    }

    @Override
    protected void initUI() {
        rvCatNames.setLayoutManager(new LinearLayoutManager(rvCatNames.getContext()));
        String[] catNames = getContext().getResources().getStringArray(R.array.cat_names);
        rvCatNames.setAdapter(new CatNamesAdapter(getContext(), catNames));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_cat_names;
    }

    @Override
    protected String getFragmentTag() {
        return TAG;
    }

}
