package com.teamhelper.phone.manager;

import android.os.Handler;
import android.os.Looper;

public class HandlerThreadManager {
    private static final HandlerThreadManager instance;
    private final Handler MAIN_HANDLER = new Handler(Looper.getMainLooper());

    static {
        instance = new HandlerThreadManager();
    }

    public static HandlerThreadManager getInstance() {
        return instance;
    }

    public void toMainThread(Runnable runnable) {
        MAIN_HANDLER.post(runnable);
    }

    public void speedToMainThread(Runnable runnable, long speed) {
        MAIN_HANDLER.postDelayed(runnable, speed);
    }

    public void toChildThread(Runnable runnable) {
        new Thread(runnable).start();
    }

    public void speedToChildThread(Runnable runnable, long speed) {
        MAIN_HANDLER.postDelayed(() -> new Thread(runnable).start(), speed);
    }

    public void removeRunnable(Runnable runnable) {
        MAIN_HANDLER.removeCallbacks(runnable);
    }

    public Handler getMainHandler() {
        return MAIN_HANDLER;
    }
}
