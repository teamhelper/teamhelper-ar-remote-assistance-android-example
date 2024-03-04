package com.teamhelper.phone.utils;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;

public class Utils {

    public static int pixelOfScaled(Context c, int sp) {
        Resources r = c.getResources();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, r.getDisplayMetrics());
    }

    public static int pixelOfDp(Context c, int dp) {
        Resources r = c.getResources();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics());
    }

    /**
     * 与getDimension()一样，但只保留整数部分。返回值类型为int。
     *
     * @param context
     * @param dimensId
     * @return
     */
    public static int getDimensionPixelOffset(Context context, int dimensId) {
        return context.getResources().getDimensionPixelOffset(dimensId);
    }
}
