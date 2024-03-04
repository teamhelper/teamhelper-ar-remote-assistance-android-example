package com.teamhelper.phone.view.activity;

import android.content.Intent;

import androidx.databinding.library.baseAdapters.BR;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.teamhelper.meeting.bean.ContactsBean;
import com.teamhelper.meeting.manager.MeetingManager;
import com.teamhelper.phone.R;
import com.teamhelper.phone.databinding.ActivityMeetingMemberBinding;
import com.teamhelper.phone.utils.StringUtil;
import com.teamhelper.phone.view.adapter.CommonRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 添加会与参与者
 *
 * @author yanchenglong
 * @time 2021/11/12
 */
public class MeetingMemberActivity extends BaseActivity<ActivityMeetingMemberBinding> {
    private final List<ContactsBean> dataList = new ArrayList<>();
    private final List<ContactsBean> checkedDataList = new ArrayList<>();
    private CommonRecyclerAdapter<ContactsBean> adapter;
    private CommonRecyclerAdapter<ContactsBean> checkedAdapter;

    @Override
    protected int getLayout() {
        return R.layout.activity_meeting_member;
    }

    @Override
    protected void initData() {
        ArrayList<String> userIds = getIntent().getStringArrayListExtra("inviteeUserIds");
        dataList.addAll(MeetingManager.getContactsList());
        dataList.forEach(item -> item.setCheckStatus(ContactsBean.UNSELECT));
        userIds.forEach(userId -> dataList.stream().filter(t -> StringUtil.equals(t.getUserId(), userId)).forEach(bean -> bean.setCheckStatus(ContactsBean.SELECTED)));
        dataList.stream().filter(t -> StringUtil.equals(t.getUserId(), MeetingManager.getUserBean().getUserId())).forEach(bean -> bean.setCheckStatus(ContactsBean.DISABLE));
        checkedDataList.addAll(dataList.stream().filter(t -> t.getCheckStatus() == ContactsBean.SELECTED).collect(Collectors.toList()));
        checkedDataList.addAll(dataList.stream().filter(t -> t.getCheckStatus() == ContactsBean.DISABLE).collect(Collectors.toList()));
    }

    @Override
    protected void initView() {
        dataBinding.ivBack.setOnClickListener(v -> finish());
        dataBinding.tvTitle.setText(getString(R.string.add_joiner));
        adapter = new CommonRecyclerAdapter<>(activity, dataList, R.layout.item_meeting_member, BR._data);
        adapter.setOnItemClickListener((v, position, itemData) -> {
            if (itemData.getCheckStatus() == ContactsBean.DISABLE) return;
            if (itemData.getCheckStatus() == ContactsBean.UNSELECT) {
                itemData.setCheckStatus(ContactsBean.SELECTED);
                checkedDataList.add(itemData);
            } else {
                itemData.setCheckStatus(ContactsBean.UNSELECT);
                checkedDataList.remove(itemData);
            }
            adapter.refreshItem(position);
            checkedAdapter.refreshData();
        });
        dataBinding.recyclerViewVertical.setLayoutManager(new LinearLayoutManager(activity));
        dataBinding.recyclerViewVertical.setAdapter(adapter);
        LinearLayoutManager manager = new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false);
        manager.setStackFromEnd(true);
        checkedAdapter = new CommonRecyclerAdapter<>(activity, checkedDataList, R.layout.item_meeting_member_checked, BR._data);
        dataBinding.recyclerViewHorizontal.setLayoutManager(manager);
        dataBinding.recyclerViewHorizontal.setAdapter(checkedAdapter);
        dataBinding.tvSure.setOnClickListener(v -> {
            List<String> collect = checkedDataList.stream().map(ContactsBean::getUserId).collect(Collectors.toList());
            Intent intent = new Intent();
            intent.putStringArrayListExtra("inviteeUserIds", new ArrayList<>(collect));
            setResult(RESULT_OK, intent);
            finish();
        });
    }
}
