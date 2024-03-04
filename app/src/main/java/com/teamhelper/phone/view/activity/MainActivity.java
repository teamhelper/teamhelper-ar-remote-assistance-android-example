package com.teamhelper.phone.view.activity;

import android.content.Intent;
import android.view.KeyEvent;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.teamhelper.meeting.bean.meeting.MeetingBean;
import com.teamhelper.meeting.bean.meeting.MeetingDetailBean;
import com.teamhelper.meeting.interfaces.IMeetingCallback;
import com.teamhelper.meeting.manager.MeetingManager;
import com.teamhelper.phone.BR;
import com.teamhelper.phone.R;
import com.teamhelper.phone.databinding.ActivityMainBinding;
import com.teamhelper.phone.manager.IntentManager;
import com.teamhelper.phone.utils.BindingAdapterUtil;
import com.teamhelper.phone.utils.StringUtil;
import com.teamhelper.phone.utils.ToastUtil;
import com.teamhelper.phone.view.adapter.CommonRecyclerAdapter;
import com.teamhelper.phone.view.dialog.InputMeetingNoDialog;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity<ActivityMainBinding> {
    private static final int REQUEST_CODE = 1000;
    private CommonRecyclerAdapter<MeetingBean> adapter;
    private final List<MeetingBean> dataList = new ArrayList<>();

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        BindingAdapterUtil.loadHead(dataBinding.ivAvatar, MeetingManager.getUserBean().getAvatarUrl());
        dataBinding.llMeetingCreate.setOnClickListener(v -> {
            IntentManager.build(MeetingMemberActivity.class)
                    .putStringArrayList("inviteeUserIds", new ArrayList<>())
                    .startActivityForResult(activity, REQUEST_CODE);
        });
        dataBinding.llMeetingJoin.setOnClickListener(v -> {
            InputMeetingNoDialog dialog = new InputMeetingNoDialog(activity);
            dialog.setOnJoinListener(meetingNo -> {
                if (StringUtil.isEmpty(meetingNo)) {
                    ToastUtil.showToast(activity, "请输入会议号");
                    return;
                }
                joinMeeting(meetingNo);
            });
            dialog.show();
        });
        dataBinding.llMeetingTask.setOnClickListener(v -> {
            IntentManager.build(MeetingReserveActivity.class).startActivity(activity);
        });
        dataBinding.llMeetingHistory.setOnClickListener(v -> {
            IntentManager.build(MeetingHistoryActivity.class).startActivity(activity);
        });
        adapter = new CommonRecyclerAdapter<>(activity, dataList, R.layout.item_meeting, BR._data);
        adapter.setOnItemClickListener((v, position, itemData) -> {
            IntentManager.build(MeetingDetailActivity.class)
                    .putString("meetingNo", itemData.getMeetingNo())
                    .startActivity(activity);
        });
        dataBinding.recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        dataBinding.recyclerView.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        getMeetingList();
    }

    private void getMeetingList() {
        MeetingManager.getMeetings(new IMeetingCallback<List<MeetingBean>>() {
            @Override
            public void onSuccess(List<MeetingBean> data) {
                dataList.clear();
                dataList.addAll(data);
                adapter.refreshData();
            }

            @Override
            public void onError(int code, String message) {
                ToastUtil.showToast(activity, message);
            }
        });
    }

    private void joinMeeting(String meetingNo) {
        MeetingManager.joinMeeting(activity, meetingNo, new IMeetingCallback<MeetingDetailBean>() {
            @Override
            public void onSuccess(MeetingDetailBean data) {

            }

            @Override
            public void onError(int code, String message) {
                ToastUtil.showToast(activity, message);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) return;
        if (requestCode == REQUEST_CODE) {
            ToastUtil.showToast(activity, "正在发起协作");
            ArrayList<String> inviteeUserIds = data.getStringArrayListExtra("inviteeUserIds");
            String meetingName = getString(R.string.meeting_task_content_hint, MeetingManager.getUserBean().getNickname());
            MeetingManager.createJoinMeeting(activity, meetingName, inviteeUserIds, new IMeetingCallback<MeetingDetailBean>() {
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
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            return true;
        }
        return false;
    }
}