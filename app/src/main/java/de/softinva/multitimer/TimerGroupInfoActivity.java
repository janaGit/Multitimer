package de.softinva.multitimer;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProviders;

import de.softinva.multitimer.databinding.TimerGroupInfoActivityBinding;
import de.softinva.multitimer.model.TimerGroup;

public class TimerGroupInfoActivity extends AppCompatActivity {
    public static final String GROUP_ID = "de.softinva.multitimer.groupId";
    TimerGroupInfoViewModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TimerGroupInfoActivityBinding binding = DataBindingUtil.setContentView(this, R.layout.timer_group_info_activity);

        model = ViewModelProviders.of(this).get(TimerGroupInfoViewModel.class);

        Intent intent = getIntent();
        String groupId = intent.getStringExtra(GROUP_ID);

        MutableLiveData<TimerGroup> timerGroup$ = model.getTimerGroup(groupId);

        timerGroup$.observe(this, tGroup -> {
            setTitle(tGroup.title);
            binding.setTimerGroup(tGroup);
        });

    }

}
