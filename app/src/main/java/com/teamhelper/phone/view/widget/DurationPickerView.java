package com.teamhelper.phone.view.widget;

import android.content.Context;
import android.util.AttributeSet;

import com.teamhelper.phone.R;


/**
 * Copyright (c) 2023
 *
 * @Project:TeamhelperPhone
 * @Author:Finger
 * @FileName:DurationPickerView.java
 * @LastModified:2023-09-12T00:40:26.812+08:00
 */

public class DurationPickerView extends PickerViewGroup {
    private PickerView hourPickerView;
    private PickerView minutePickerView;

    public DurationPickerView(Context context) {
        this(context, null);
    }

    public DurationPickerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DurationPickerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        buildViews(context);
    }

    private void buildViews(Context context) {
        removeAllViews();

        configurePickerViews(context);
    }

    private void configurePickerViews(Context context) {
        hourPickerView = new PickerView(context);
        minutePickerView = new PickerView(context);
        settlePickerView(hourPickerView);
        settlePickerView(minutePickerView);

        runIfNotNull(() -> hourPickerView.setAdapter(new PickerView.Adapter<PickerView.PickerItem>() {
                    @Override
                    public int getItemCount() {
                        return 24;
                    }

                    @Override
                    public PickerView.PickerItem getItem(int index) {
                        return () -> String.format(context.getString(R.string.meeting_hours), index);
                    }
                })
        );

        runIfNotNull(() -> minutePickerView.setAdapter(new PickerView.Adapter<PickerView.PickerItem>() {
                    @Override
                    public int getItemCount() {
                        if (hourPickerView.getSelectedItemPosition() == 0) return 30 / 5;
                        if (hourPickerView.getSelectedItemPosition() == 23) return 60 / 5 - 2;
                        return 60 / 5;
                    }

                    @Override
                    public PickerView.PickerItem getItem(int index) {
                        if (hourPickerView.getSelectedItemPosition() == 0)
                            return () -> String.format(context.getString(R.string.meeting_minus), 30 + index * 5);
                        return () -> String.format(context.getString(R.string.meeting_minus), index * 5);
                    }
                })
        );
    }

    private void runIfNotNull(Runnable runnable, Object... objects) {
        runIfNotNull(runnable, null, objects);
    }

    private void runIfNotNull(Runnable runnable, Runnable elseRunnable, Object... objects) {
        boolean hasNull = false;
        for (Object object : objects) {
            if (object == null) {
                hasNull = true;
                break;
            }
        }
        if (!hasNull) {
            if (runnable != null) runnable.run();
        } else {
            if (elseRunnable != null) elseRunnable.run();
        }
    }

    public Integer getHour() {
        return hourPickerView.getSelectedItemPosition();
    }

    public Integer getMinutes() {
        if (hourPickerView.getSelectedItemPosition() == 0)
            return 30 + minutePickerView.getSelectedItemPosition() * 5;
        return minutePickerView.getSelectedItemPosition() * 5;
    }

    public String getDurationFormatString() {
        return getHour() == 0 ? getContext().getString(R.string.half_minutes, getMinutes()) : getContext().getString(R.string.hour_minutes, getHour(), getMinutes());
    }
}
