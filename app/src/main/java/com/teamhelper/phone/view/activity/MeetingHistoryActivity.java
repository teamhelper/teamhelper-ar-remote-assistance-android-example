package com.teamhelper.phone.view.activity;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.teamhelper.meeting.bean.meeting.MeetingBean;
import com.teamhelper.meeting.bean.meeting.MeetingHistoryBean;
import com.teamhelper.meeting.interfaces.IMeetingCallback;
import com.teamhelper.meeting.manager.MeetingManager;
import com.teamhelper.phone.BR;
import com.teamhelper.phone.R;
import com.teamhelper.phone.databinding.ActivityMeetingHistoryBinding;
import com.teamhelper.phone.manager.IntentManager;
import com.teamhelper.phone.utils.ToastUtil;
import com.teamhelper.phone.view.adapter.CommonRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

public class MeetingHistoryActivity extends BaseActivity<ActivityMeetingHistoryBinding> {
    private final List<MeetingBean> dataList = new ArrayList<>();
    private CommonRecyclerAdapter<MeetingBean> adapter;

    @Override
    protected int getLayout() {
        return R.layout.activity_meeting_history;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        dataBinding.ivBack.setOnClickListener(v -> finish());
        adapter = new CommonRecyclerAdapter<>(activity, dataList, R.layout.item_meeting_history, BR._data);
        adapter.setOnItemClickListener((v, position, itemData) -> {
            IntentManager.build(MeetingDetailActivity.class)
                    .putString("meetingNo", itemData.getMeetingNo())
                    .startActivity(activity);
        });
        dataBinding.recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        dataBinding.recyclerView.setAdapter(adapter);
        getHistoryMeetings();
    }

    private void getHistoryMeetings() {
        MeetingManager.getHistoryMeetings(1, 100, new IMeetingCallback<MeetingHistoryBean>() {
            @Override
            public void onSuccess(MeetingHistoryBean data) {
                dataList.clear();
                data.getData().forEach(item -> {
                    dataList.addAll(item.getMeetingList());
                });
                adapter.refreshData();
            }

            @Override
            public void onError(int code, String message) {
                ToastUtil.showToast(activity, message);
            }
        });
    }
}