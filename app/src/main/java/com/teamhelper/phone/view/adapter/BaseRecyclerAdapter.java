package com.teamhelper.phone.view.adapter;

import androidx.recyclerview.widget.RecyclerView;

/**
 * @author yanchenglong
 * @time 2022/6/30
 */
public abstract class BaseRecyclerAdapter<T extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<T> {
    public BaseRecyclerAdapter() {
        setHasStableIds(true);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
