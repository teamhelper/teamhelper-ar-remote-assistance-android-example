package com.teamhelper.phone.manager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Parcelable;

import androidx.fragment.app.Fragment;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author yanchenglong
 * @time 2021/8/10
 */
public class IntentManager {
    private final Intent intent;
    private final Class targetClass;

    public static IntentManager build(Class targetClass) {
        return new IntentManager(targetClass);
    }

    private IntentManager(Class targetClass) {
        this.intent = new Intent();
        this.targetClass = targetClass;
    }

    public IntentManager setFlags(int flags) {
        intent.setFlags(flags);
        return this;
    }

    public IntentManager putInt(String key, int value) {
        intent.putExtra(key, value);
        return this;
    }

    public IntentManager putLong(String key, long value) {
        intent.putExtra(key, value);
        return this;
    }

    public IntentManager putString(String key, String value) {
        intent.putExtra(key, value);
        return this;
    }

    public IntentManager putBoolean(String key, boolean value) {
        intent.putExtra(key, value);
        return this;
    }

    public IntentManager putSerializable(String key, Serializable value) {
        intent.putExtra(key, value);
        return this;
    }

    public IntentManager putParcelable(String key, Parcelable value) {
        intent.putExtra(key, value);
        return this;
    }

    public IntentManager putStrings(String key, String[] values) {
        intent.putExtra(key, values);
        return this;
    }

    public IntentManager putStringArrayList(String key, ArrayList<String> value) {
        intent.putStringArrayListExtra(key, value);
        return this;
    }

    public IntentManager putUri(Uri uri) {
        intent.setData(uri);
        return this;
    }

    public void startActivity(Context context) {
        intent.setClass(context, targetClass);
        context.startActivity(intent);
    }

    public void startActivityForResult(Activity activity, int requestCode) {
        intent.setClass(activity, targetClass);
        activity.startActivityForResult(intent, requestCode);
    }

    public void startActivityForResult(Fragment fragment, int requestCode) {
        intent.setClass(fragment.getContext(), targetClass);
        fragment.startActivityForResult(intent, requestCode);
    }

    public void startService(Context context) {
        intent.setClass(context, targetClass);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            context.startService(intent);
        } else {
            context.startForegroundService(intent);
        }
    }

    public void stopService(Context context) {
        intent.setClass(context, targetClass);
        context.stopService(intent);
    }
}
