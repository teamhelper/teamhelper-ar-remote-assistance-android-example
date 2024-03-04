package com.teamhelper.phone.utils;

/**
 * @author yanchenglong
 * @time 2022/4/28
 */
public class StringUtil {
    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    public static boolean equals(String str, String str2) {
        if (isEmpty(str) && isEmpty(str2)) {
            return true;
        }
        if (isEmpty(str) || isEmpty(str2)) {
            return false;
        }
        return str.equals(str2);
    }

    public static boolean contains(String str, String str2) {
        if (isEmpty(str) && isEmpty(str2)) {
            return true;
        }
        if (isEmpty(str) || isEmpty(str2)) {
            return false;
        }
        return str.contains(str2);
    }
}
