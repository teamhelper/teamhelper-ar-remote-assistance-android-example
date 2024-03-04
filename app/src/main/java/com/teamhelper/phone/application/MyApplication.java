package com.teamhelper.phone.application;

import android.app.Activity;
import android.app.Application;

import com.kongzue.dialogx.DialogX;
import com.kongzue.dialogx.style.MIUIStyle;
import com.teamhelper.im.IMConfig;
import com.teamhelper.meeting.MeetingConfig;
import com.teamhelper.meeting.manager.MeetingManager;
import com.teamhelper.phone.BuildConfig;
import com.teamhelper.phone.Config;
import com.teamhelper.phone.manager.IntentManager;
import com.teamhelper.phone.view.activity.LoginActivity;
import com.teamhelper.tools.ActivityStackManager;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        MeetingManager.init(this);
        MeetingManager.addTokenWillExpire(unused -> {
            //token即将失效的回调
            long timestamp = System.currentTimeMillis() + (5 * 60 * 60 * 1000);
            MeetingManager.exampleRefreshToken(Config.APP_KEY, Config.ACCESS_KEY, Config.ACCESS_SECRET, timestamp, null);
        });
        MeetingManager.addKickOutCallback(kickOut -> {
            //被踢下线回调
            Activity currentActivity = ActivityStackManager.getInstance().getCurrentActivity();
            ActivityStackManager.getInstance().finishActivityList();
            IntentManager.build(LoginActivity.class).startActivity(currentActivity);
        });
        initDialogX();
    }

    private void initDialogX() {
        DialogX.init(this);
        DialogX.globalStyle = MIUIStyle.style();
        DialogX.globalTheme = DialogX.THEME.LIGHT;
    }
}
