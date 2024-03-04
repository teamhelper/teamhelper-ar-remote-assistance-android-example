package com.teamhelper.phone.view.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.databinding.DataBindingUtil;

import com.teamhelper.phone.R;
import com.teamhelper.phone.databinding.DialogInputMeetingNoBinding;

import java.util.function.Consumer;

/**
 * @author yanchenglong
 * @time 2021/10/19
 */
public class InputMeetingNoDialog extends Dialog {
    private DialogInputMeetingNoBinding dataBinding;

    private Consumer<String> onJoinListener;

    public InputMeetingNoDialog(Activity activity) {
        super(activity, R.style.base_full_screen_Dialog);
        dataBinding = DataBindingUtil.inflate(LayoutInflater.from(activity), R.layout.dialog_input_meeting_no, null, false);
    }

    public void setOnJoinListener(Consumer<String> onJoinListener) {
        this.onJoinListener = onJoinListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(dataBinding.getRoot());
        dataBinding.ivClose.setOnClickListener(v -> dismiss());
        dataBinding.tvJoinMeeting.setOnClickListener(v -> {
            String content = dataBinding.edContent.getText().toString();
            if (onJoinListener != null) onJoinListener.accept(content);
        });
    }
}
