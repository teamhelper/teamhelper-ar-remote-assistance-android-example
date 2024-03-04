package com.teamhelper.phone.utils;

import android.util.Log;

import com.alibaba.fastjson.JSON;

/**
 * @author yanchenglong
 * @time 2021/9/27
 */
public class LogUtil {
    private static final String TAG = LogUtil.class.getSimpleName();
    private static boolean isLog = true;

    public static void setLog(boolean isLog) {
        LogUtil.isLog = isLog;
    }

    public static boolean isLog() {
        return isLog;
    }

    public static void logD(final String tag, Object message) {
        if (isLog) {
            if (message instanceof String
                    || message instanceof Integer
                    || message instanceof Long
                    || message instanceof Double
                    || message instanceof Float) {
                Log.d(TAG + "------" + tag, String.valueOf(message));
            } else {
                Log.d(TAG + "------" + tag, JSON.toJSONString(message));
            }
        }
    }

    public static void logV(final String tag, Object message) {
        if (isLog) {
            if (message instanceof String
                    || message instanceof Integer
                    || message instanceof Long
                    || message instanceof Double
                    || message instanceof Float) {
                Log.v(TAG + "------" + tag, String.valueOf(message));
            } else {
                Log.v(TAG + "------" + tag, JSON.toJSONString(message));
            }
        }
    }

    public static void logI(final String tag, Object message) {
        if (isLog) {
            if (message instanceof String
                    || message instanceof Integer
                    || message instanceof Long
                    || message instanceof Double
                    || message instanceof Float) {
                Log.i(TAG + "------" + tag, String.valueOf(message));
            } else {
                Log.i(TAG + "------" + tag, JSON.toJSONString(message));
            }
        }
    }

    public static void logW(final String tag, Object message) {
        if (isLog) {
            if (message instanceof String
                    || message instanceof Integer
                    || message instanceof Long
                    || message instanceof Double
                    || message instanceof Float) {
                Log.w(TAG + "------" + tag, String.valueOf(message));
            } else {
                Log.w(TAG + "------" + tag, JSON.toJSONString(message));
            }
        }
    }

    public static void logE(final String tag, Object message) {
        if (isLog) {
            if (message instanceof String
                    || message instanceof Integer
                    || message instanceof Long
                    || message instanceof Double
                    || message instanceof Float) {
                Log.e(TAG + "------" + tag, String.valueOf(message));
            } else {
                Log.e(TAG + "------" + tag, JSON.toJSONString(message));
            }
        }
    }
}
