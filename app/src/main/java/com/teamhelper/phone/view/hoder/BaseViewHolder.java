package com.teamhelper.phone.view.hoder;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author yanchenglong
 * @time 2021/10/15
 */
public class BaseViewHolder extends RecyclerView.ViewHolder {
    private ViewDataBinding dataBinding;

    public BaseViewHolder(@NonNull ViewDataBinding dataBinding) {
        super(dataBinding.getRoot());
        this.dataBinding = dataBinding;
    }

    public ViewDataBinding getDataBinding() {
        return dataBinding;
    }
}
