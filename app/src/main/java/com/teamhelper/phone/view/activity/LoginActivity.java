package com.teamhelper.phone.view.activity;

import com.teamhelper.meeting.bean.UserBean;
import com.teamhelper.meeting.interfaces.IMeetingCallback;
import com.teamhelper.meeting.manager.MeetingManager;
import com.teamhelper.phone.BuildConfig;
import com.teamhelper.phone.Config;
import com.teamhelper.phone.R;
import com.teamhelper.phone.databinding.ActivityLoginBinding;
import com.teamhelper.phone.manager.IntentManager;
import com.teamhelper.phone.utils.StringUtil;
import com.teamhelper.phone.utils.ToastUtil;

public class LoginActivity extends BaseActivity<ActivityLoginBinding> {
    public static UserBean userBean;

    @Override
    protected int getLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        dataBinding.btnLogin.setOnClickListener(v -> {
            String userId = dataBinding.etUserId.getText().toString();
            if (StringUtil.isEmpty(userId)) {
                ToastUtil.showToast(activity, "请输入用户ID");
                return;
            }
            if (StringUtil.isEmpty(Config.APP_KEY)) {
                ToastUtil.showToast(activity, "请配置appKey");
                return;
            }
            if (StringUtil.isEmpty(Config.ACCESS_KEY)) {
                ToastUtil.showToast(activity, "请配置accessKey");
                return;
            }
            if (StringUtil.isEmpty(Config.ACCESS_SECRET)) {
                ToastUtil.showToast(activity, "请配置accessSecret");
                return;
            }
            showLoading();
            //5个小时后token到期
            long timestamp = System.currentTimeMillis() + (5 * 60 * 60 * 1000);
            MeetingManager.exampleLogin(userId, Config.APP_KEY, Config.ACCESS_KEY, Config.ACCESS_SECRET, timestamp, new IMeetingCallback<UserBean>() {
                @Override
                public void onSuccess(UserBean data) {
                    dismissLoading();
                    userBean = data;
                    IntentManager.build(MainActivity.class).startActivity(LoginActivity.this);
                    finish();
                }

                @Override
                public void onError(int code, String message) {
                    dismissLoading();
                    ToastUtil.showToast(activity, message);
                }
            });
        });
    }
}
