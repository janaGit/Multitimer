package de.softinva.multitimer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateVMFactory;
import androidx.lifecycle.ViewModelProvider;

import de.softinva.multitimer.classes.AppActivity;
import de.softinva.multitimer.databinding.ActivityTimerGroupInfoBinding;
import de.softinva.multitimer.model.TimerGroup;

public class TimerGroupInfoActivity extends AppActivity {
    public static final String GROUP_ID = "de.softinva.multitimer.groupId";

    TimerGroupInfoViewModel model;

    public static void startNewActivity(String groupId, Context context) {
        Intent intent = new Intent(context, TimerGroupInfoActivity.class);
        intent.putExtra(TimerGroupInfoActivity.GROUP_ID, groupId);
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityTimerGroupInfoBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_timer_group_info);

        model = new ViewModelProvider(this, new SavedStateVMFactory(this))
                .get(TimerGroupInfoViewModel.class);

        setGroupId();

        MutableLiveData<TimerGroup> timerGroup$ = model.getTimerGroup(model.getTimerGroupId().getValue());
        timerGroup$.observe(this, tGroup -> {
            setTitle(tGroup.title);
            binding.setTimerGroup(tGroup);
        });

    }

    protected void setGroupId() {
        String groupId = model.getTimerGroupId().getValue();

        if (groupId == null) {

            groupId = getIntent().getStringExtra(GROUP_ID);

            if (groupId != null) {
                model.getTimerGroupId().setValue(groupId);
            } else {
                throw new Error("groupId is null !");
            }
        }
    }
}
