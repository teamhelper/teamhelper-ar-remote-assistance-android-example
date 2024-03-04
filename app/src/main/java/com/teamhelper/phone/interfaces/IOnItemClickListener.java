package com.teamhelper.phone.interfaces;

import android.view.View;

/**
 * @author yanchenglong
 * @time 2021/10/15
 */
public interface IOnItemClickListener<T> {
    void onItemClick(View view, int position, T itemData);
}
