package com.teamhelper.phone.view.activity;

import android.content.Intent;
import android.view.View;

import androidx.annotation.Nullable;

import com.kongzue.dialogx.dialogs.BottomDialog;
import com.kongzue.dialogx.interfaces.OnBindView;
import com.teamhelper.meeting.bean.meeting.MeetingCreateBean;
import com.teamhelper.meeting.interfaces.IMeetingCallback;
import com.teamhelper.meeting.manager.MeetingManager;
import com.teamhelper.phone.R;
import com.teamhelper.phone.databinding.ActivityMeetingReserveBinding;
import com.teamhelper.phone.manager.IntentManager;
import com.teamhelper.phone.utils.StringUtil;
import com.teamhelper.phone.utils.TimeUtils;
import com.teamhelper.phone.utils.ToastUtil;
import com.teamhelper.phone.view.widget.DateTimePickerView;
import com.teamhelper.phone.view.widget.DurationPickerView;

import java.util.ArrayList;
import java.util.Calendar;

public class MeetingReserveActivity extends BaseActivity<ActivityMeetingReserveBinding> {
    private static final int REQUEST_CODE = 1000;
    private final ArrayList<String> inviteeUserIds = new ArrayList<>();
    private long startTime = System.currentTimeMillis();
    private int hour = 0;
    private int minute = 30;

    @Override
    protected int getLayout() {
        return R.layout.activity_meeting_reserve;
    }

    @Override
    protected void initData() {
        inviteeUserIds.add(MeetingManager.getUserBean().getUserId());
    }

    @Override
    protected void initView() {
        dataBinding.ivBack.setOnClickListener(v -> finish());
        dataBinding.tvTitle.setText(R.string.meeting_task);
        dataBinding.tvSave.setOnClickListener(v -> subscribeMeeting());
        dataBinding.edContent.setHint(getString(R.string.meeting_task_content_hint, MeetingManager.getUserBean().getNickname()));
        dataBinding.ivClear.setOnClickListener(view -> dataBinding.edContent.setText(""));
        dataBinding.tvStartTime.setText(TimeUtils.timeString(Calendar.getInstance()));
        dataBinding.tvDuration.setText(getString(R.string.half_minutes, minute));
        dataBinding.tvInvitees.setText(getString(R.string.invitee_numbers, inviteeUserIds.size()));
        dataBinding.llMeetingStartTime.setOnClickListener(v -> {
            BottomDialog.show(new OnBindView<BottomDialog>(R.layout.dialog_content_datatime_picker) {
                @Override
                public void onBind(BottomDialog dialog, View v) {
                    dialog.setBottomNonSafetyAreaBySelf(true);
                    v.findViewById(R.id.iv_close).setOnClickListener(_v -> dialog.dismiss());
                    DateTimePickerView dateTimePickerView = v.findViewById(R.id.datePickerView);
                    dateTimePickerView.setStartDate(Calendar.getInstance());
                    dateTimePickerView.setSelectedDate(Calendar.getInstance());
                    Calendar calendar = Calendar.getInstance();
                    calendar.add(Calendar.YEAR, 1);
                    dateTimePickerView.setEndDate(calendar);
                    dialog.setCancelable(false);

                    v.findViewById(R.id.tv_sure).setOnClickListener(_v -> {
                        startTime = dateTimePickerView.getSelectedDate().getTimeInMillis();
                        dataBinding.tvStartTime.setText(TimeUtils.timeString(dateTimePickerView.getSelectedDate()));
                        dialog.dismiss();
                    });
                }
            });
        });
        dataBinding.llMeetingDuration.setOnClickListener(v -> BottomDialog.show(new OnBindView<BottomDialog>(R.layout.dialog_content_duration_picker) {
            @Override
            public void onBind(BottomDialog dialog, View v) {
                dialog.setBottomNonSafetyAreaBySelf(true);
                v.findViewById(R.id.iv_close).setOnClickListener(_v -> dialog.dismiss());
                DurationPickerView durationPickerView = v.findViewById(R.id.pickerView);
                dialog.setCancelable(false);

                v.findViewById(R.id.tv_sure).setOnClickListener(_v -> {
                    hour = durationPickerView.getHour();
                    minute = durationPickerView.getMinutes();
                    dataBinding.tvDuration.setText(durationPickerView.getDurationFormatString());
                    dialog.dismiss();
                });
            }
        }));
        dataBinding.llMeetingJoiner.setOnClickListener(v -> {
            IntentManager.build(MeetingMemberActivity.class)
                    .putStringArrayList("inviteeUserIds", inviteeUserIds)
                    .startActivityForResult(activity, REQUEST_CODE);
        });
    }

    private void subscribeMeeting() {
        if (inviteeUserIds.size() <= 1) {
            ToastUtil.showToast(this, getString(R.string.please_select_invitees));
            return;
        }
        showLoading();
        String meetingName = dataBinding.edContent.getText().toString();
        if (StringUtil.isEmpty(meetingName)) {
            meetingName = dataBinding.edContent.getHint().toString();
        }
        MeetingManager.subscribeMeeting(meetingName, startTime, hour, minute, inviteeUserIds, new IMeetingCallback<MeetingCreateBean>() {
            @Override
            public void onSuccess(MeetingCreateBean data) {
                dismissLoading();
                ToastUtil.showToast(activity, R.string.meeting_create_success);
                finish();
            }

            @Override
            public void onError(int code, String message) {
                dismissLoading();
                ToastUtil.showToast(activity, message);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK || data == null) return;
        if (requestCode == REQUEST_CODE) {
            inviteeUserIds.clear();
            inviteeUserIds.addAll(data.getStringArrayListExtra("inviteeUserIds"));
            dataBinding.tvInvitees.setText(getString(R.string.invitee_numbers, inviteeUserIds.size()));
        }
    }
}