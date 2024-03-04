package com.teamhelper.phone.view.activity;


import android.content.ClipboardManager;
import android.content.Context;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;

import com.teamhelper.meeting.bean.meeting.MeetingDetailBean;
import com.teamhelper.meeting.interfaces.IMeetingCallback;
import com.teamhelper.meeting.manager.MeetingManager;
import com.teamhelper.phone.R;
import com.teamhelper.phone.databinding.ActivityMeetingDetailBinding;
import com.teamhelper.phone.utils.TimeUtils;
import com.teamhelper.phone.utils.ToastUtil;

public class MeetingDetailActivity extends BaseActivity<ActivityMeetingDetailBinding> {
    private String meetingNo;

    @Override
    protected int getLayout() {
        return R.layout.activity_meeting_detail;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        dataBinding = DataBindingUtil.setContentView(activity, R.layout.activity_meeting_detail);
        meetingNo = getIntent().getStringExtra("meetingNo");
        dataBinding.ivBack.setOnClickListener(v -> finish());
        getMeetingDetail();
    }

    private void getMeetingDetail() {
        MeetingManager.getMeetingDetail(meetingNo, new IMeetingCallback<MeetingDetailBean>() {
            @Override
            public void onSuccess(MeetingDetailBean data) {
                dataBinding.setData(data);
                dataBinding.tvStartTime.setText(TimeUtils.convertTimestampToTime(data.getStartTime()));
                dataBinding.tvEndTime.setText(TimeUtils.convertTimestampToTime(data.getEndTime()));
                dataBinding.tvStartDate.setText(TimeUtils.convertTimestampToDate(data.getStartTime()));
                dataBinding.tvEndDate.setText(TimeUtils.convertTimestampToDate(data.getEndTime()));
                dataBinding.llCopy.setOnClickListener(v -> {
                    copyToClipboard(activity, data.getMeetingNo());
                });
                dataBinding.tvMeetingChat.setOnClickListener(v -> {
                    MeetingManager.jumpToGroupChatActivity(activity, data.getGroupId(), data.getMeetingName(), data.getStatus());
                });

                dataBinding.tvMeetingHistoryChat.setOnClickListener(v -> {
                    MeetingManager.jumpToGroupChatActivity(activity, data.getGroupId(), data.getMeetingName(), data.getStatus());
                });
                dataBinding.tvJoinMeeting.setOnClickListener(v -> {
                    showLoading();
                    MeetingManager.joinMeeting(activity, data, new IMeetingCallback<MeetingDetailBean>() {
                        @Override
                        public void onSuccess(MeetingDetailBean data) {
                            dismissLoading();
                        }

                        @Override
                        public void onError(int code, String message) {
                            dismissLoading();
                            ToastUtil.showToast(activity, message);
                        }
                    });
                });
            }

            @Override
            public void onError(int code, String message) {
                ToastUtil.showToast(activity, message);
            }
        });
    }

    // 将文本复制到剪切板
    private void copyToClipboard(Context context, String content) {
        // 从 API11 开始 android 推荐使用 android.content.ClipboardManager
        // 为了兼容低版本我们这里使用旧版的 android.text.ClipboardManager，虽然提示 deprecated，但不影响使用。
        ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        // 将文本内容放到系统剪贴板里。
        cm.setText(content);
        Toast.makeText(context, "已复制到剪切板", Toast.LENGTH_SHORT).show();
    }
}
