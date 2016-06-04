package com.stoyanov.onetap.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.stoyanov.onetap.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by alexander on 6/4/16.
 */
public class CatNamesAdapter extends RecyclerView.Adapter<CatNamesAdapter.CatListViewHolder> {
    private Context context;
    private String[] catNames;

    public CatNamesAdapter(Context context, String[] catNames) {
        this.context = context;
        this.catNames = catNames;
    }

    @Override
    public CatListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.row_cat_names, parent, false);
        return new CatListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CatListViewHolder holder, int position) {
        final String catString = catNames[position];
        if (!TextUtils.isEmpty(catString)) {
            holder.txtCatName.setText(catString);
        }
        holder.grpCatListRow.setTag(position);
    }

    @Override
    public int getItemCount() {
        return catNames.length;
    }

    public static class CatListViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.grp_cat_name)
        LinearLayout grpCatListRow;
        @BindView(R.id.txt_cat_name)
        TextView txtCatName;

        CatListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
