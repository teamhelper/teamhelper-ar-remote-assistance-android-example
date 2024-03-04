package com.teamhelper.phone.view.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.teamhelper.phone.view.dialog.LoadingDialog;

public abstract class BaseActivity<DB extends ViewDataBinding> extends AppCompatActivity {
    protected AppCompatActivity activity;
    protected DB dataBinding;
    private LoadingDialog loadingDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.activity = this;
        getWindow().setStatusBarColor(Color.WHITE);
        int vis = getWindow().getDecorView().getSystemUiVisibility();
        getWindow().getDecorView().setSystemUiVisibility(vis | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        dataBinding = DataBindingUtil.setContentView(this, getLayout());
        initData();
        initView();
    }

    protected abstract int getLayout();

    protected abstract void initData();

    protected abstract void initView();

    public void showLoading() {
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(activity);
            loadingDialog.setCancelable(false);
        }
        loadingDialog.show();
    }

    public void showLoadingFullScreen() {
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(activity);
            loadingDialog.setCancelable(false);
        }
        loadingDialog.show();
    }

    public void dismissLoading() {
        if (loadingDialog != null) {
            loadingDialog.dismiss();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dismissLoading();
    }
}
