package com.teamhelper.phone.utils;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

import com.teamhelper.phone.manager.HandlerThreadManager;

/**
 * @author yanchenglong
 * @time 2020-04-20
 */
public class ToastUtil {

    /**
     * ToastSingle，展示信息提示
     *
     * @param content 展示内容
     */
    public static void showToast(Context context, String content) {
        HandlerThreadManager.getInstance().toMainThread(() -> {
            Toast toast = Toast.makeText(context, content, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        });
    }

    /**
     * ToastSingle，展示信息提示
     *
     * @param stringsId 展示内容
     */
    public static void showToast(Context context, int stringsId) {
        HandlerThreadManager.getInstance().toMainThread(() -> {
            Toast toast = Toast.makeText(context, stringsId, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        });
    }
}
