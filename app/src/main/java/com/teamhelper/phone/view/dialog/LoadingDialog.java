package com.teamhelper.phone.view.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.bumptech.glide.Glide;
import com.teamhelper.phone.R;
import com.teamhelper.phone.databinding.DialogLoadingBinding;

/**
 * @author yanchenglong
 * @time 2022/7/21
 */
public class LoadingDialog extends Dialog {
    private DialogLoadingBinding dataBinding;

    public LoadingDialog(AppCompatActivity activity) {
        super(activity, R.style.base_full_screen_Dialog);
        dataBinding = DataBindingUtil.inflate(LayoutInflater.from(activity), R.layout.dialog_loading, null, false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(dataBinding.getRoot());
        Glide.with(getContext()).load(R.drawable.ic_loading).into(dataBinding.ivLoading);
    }
}
