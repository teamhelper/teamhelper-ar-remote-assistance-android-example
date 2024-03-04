package com.teamhelper.phone.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.teamhelper.phone.interfaces.IOnItemClickListener;
import com.teamhelper.phone.view.hoder.BaseViewHolder;

import java.util.List;

/**
 * @author yanchenglong
 * @time 2021/10/15
 */
public class CommonRecyclerAdapter<T> extends BaseRecyclerAdapter<BaseViewHolder> {
    private Context context;
    private List<T> dataList;
    private int layoutId;
    private int variableId;

    private IOnItemClickListener<T> onItemClickListener;

    public CommonRecyclerAdapter(Context context, List<T> dataList, int layoutId, int variableId) {
        this.context = context;
        this.dataList = dataList;
        this.layoutId = layoutId;
        this.variableId = variableId;
    }

    public void setOnItemClickListener(IOnItemClickListener<T> onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewDataBinding dataBinding = DataBindingUtil.inflate(LayoutInflater.from(context), layoutId, parent, false);
        return new BaseViewHolder(dataBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.getDataBinding().setVariable(variableId, getItem(position));
        if (onItemClickListener != null) {
            holder.getDataBinding().getRoot().setOnClickListener(v -> {
                onItemClickListener.onItemClick(v, position, getItem(position));
            });
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public T getItem(int position) {
        return dataList.get(position);
    }

    public void refreshData() {
        notifyDataSetChanged();
    }

    public void refreshData(List<T> dataList) {
        this.dataList = dataList;
        notifyDataSetChanged();
    }

    public void refreshItem(int position) {
        notifyItemChanged(position);
    }

    public void refreshItem(int position, T data) {
        notifyItemChanged(position, data);
    }
}
