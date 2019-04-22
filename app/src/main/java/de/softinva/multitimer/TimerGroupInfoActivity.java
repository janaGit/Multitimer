package de.softinva.multitimer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProviders;

import de.softinva.multitimer.classes.AppActivity;
import de.softinva.multitimer.databinding.TimerGroupInfoActivityBinding;
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
