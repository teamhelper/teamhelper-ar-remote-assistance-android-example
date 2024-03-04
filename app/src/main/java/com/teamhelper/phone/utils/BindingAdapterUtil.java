package com.teamhelper.phone.utils;

import android.text.TextUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.teamhelper.meeting.bean.meeting.MeetingDetailBean;
import com.teamhelper.meeting.manager.MeetingManager;
import com.teamhelper.meeting.view.widget.CircleImageView;
import com.teamhelper.phone.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * 给xml内view设置动态属性
 *
 * @author yanchenglong
 * @time 2021/6/26
 */
public class BindingAdapterUtil {

    /**
     * 加载个人头像
     *
     * @param imageView
     */
    @BindingAdapter({"loadHead"})
    public static void loadHead(ImageView imageView, String avatarUrl) {
        Glide.with(imageView.getContext()).load(avatarUrl).placeholder(R.mipmap.ic_meeting_avatar).into(imageView);
    }

    @BindingAdapter({"loadAvatarGroup"})
    public static void loadAvatarGroup(FrameLayout frameLayout, List<MeetingDetailBean.UserInfo> joinMeetingUserInfoList) {
        if (joinMeetingUserInfoList == null || joinMeetingUserInfoList.isEmpty()) return;
        for (int i = 0; i < joinMeetingUserInfoList.size(); i++) {
            CircleImageView circleImageView = new CircleImageView(frameLayout.getContext());
            int dp19 = Utils.getDimensionPixelOffset(frameLayout.getContext(), R.dimen.dp_19);
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(dp19, dp19);
            int dp12 = Utils.getDimensionPixelOffset(frameLayout.getContext(), R.dimen.dp_12);
            layoutParams.setMargins(dp12 * i, 0, 0, 0);
            circleImageView.setLayoutParams(layoutParams);
            int dp1 = Utils.getDimensionPixelOffset(frameLayout.getContext(), R.dimen.dp_1);
            circleImageView.setBorderWidth(dp1);
            circleImageView.setBorderColor(frameLayout.getContext().getResources().getColor(R.color.white));
            circleImageView.setPadding(dp1, dp1, dp1, dp1);
            Glide.with(frameLayout.getContext()).load(joinMeetingUserInfoList.get(i).getUrl()).placeholder(R.mipmap.ic_meeting_avatar).into(circleImageView);
            frameLayout.addView(circleImageView);
        }
    }

    @BindingAdapter({"setMeetingNoWithId"})
    public static void setMeetingNoWithId(TextView textView, String meetingNo) {
        //将数字字符串每三位插入一个空格
        if (TextUtils.isEmpty(meetingNo)) {
            return;
        }
        StringBuilder sb = new StringBuilder(meetingNo);
        int len = sb.length();
        int i = len / 3;
        int j = len % 3;
        int index = 0;
        if (j == 0) {
            index = i - 1;
        } else {
            index = i;
        }
        for (int k = 0; k < index; k++) {
            sb.insert((k + 1) * 3 + k, " ");
        }
        textView.setText(String.format("ID: %s", sb));
    }

    @BindingAdapter({"setMeetingNo"})
    public static void setMeetingNo(TextView textView, String meetingNo) {
        //将数字字符串每三位插入一个空格
        if (TextUtils.isEmpty(meetingNo)) {
            return;
        }
        StringBuilder sb = new StringBuilder(meetingNo);
        int len = sb.length();
        int i = len / 3;
        int j = len % 3;
        int index = 0;
        if (j == 0) {
            index = i - 1;
        } else {
            index = i;
        }
        for (int k = 0; k < index; k++) {
            sb.insert((k + 1) * 3 + k, " ");
        }
        textView.setText(sb.toString());
    }

    @BindingAdapter({"setMeetingJoinTime"})
    public static void setMeetingJoinTime(TextView textView, Long joinTime) {
        if (joinTime == null || joinTime == 0) {
            textView.setText("-/- -:-");
            return;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd HH:mm", Locale.getDefault());
        textView.setText(sdf.format(new Date(joinTime)));
    }

    @BindingAdapter({"setJoinDuration"})
    public static void setJoinDuration(TextView textView, Long duration) {
        if (duration == null || duration == 0) {
            textView.setText("00:00");
            return;
        }
        long hour = duration / 60;
        long minute = duration % 60;
        textView.setText(textView.getContext().getString(R.string._2d_2d, hour, minute));
    }

    @BindingAdapter({"setMeetingDuration"})
    public static void setMeetingDuration(TextView textView, Long duration) {
        if (duration == null || duration == 0) return;
        long tmp = duration / 1000 / 60;
        long hour = tmp / 60;
        long minute = tmp % 60;
        textView.setText(hour == 0 ? textView.getContext().getString(R.string.half_minutes, minute) : textView.getContext().getString(R.string.hour_minutes, hour, minute));
    }

    @BindingAdapter({"setMeetingDateRange"})
    public static void setMeetingDateRange(TextView textView, MeetingDetailBean meetingDetail) {
        if (meetingDetail == null || meetingDetail.getStartTime() == 0 || meetingDetail.getEndTime() == 0)
            return;

        StringBuilder sb = new StringBuilder();
        SimpleDateFormat sdf = new SimpleDateFormat(StringUtil.equals(MeetingManager.getLanguage().getLanguage(), Locale.ENGLISH.getLanguage()) ? "MM-dd" : "MM月dd日", Locale.getDefault());
        sb.append(sdf.format(new Date(meetingDetail.getStartTime())));
        SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm", Locale.getDefault());
        sb.append(" ").append(sdf2.format(new Date(meetingDetail.getStartTime()))).append("-").append(sdf2.format(new Date(meetingDetail.getEndTime())));
        textView.setText(sb.toString());
    }
}
