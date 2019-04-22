package de.softinva.multitimer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProviders;

import de.softinva.multitimer.classes.AppActivity;
import de.softinva.multitimer.databinding.DetailedTimerInfoActivityBinding;
import de.softinva.multitimer.model.RunningTimer;

public class DetailedTimerInfoActivity extends AppActivity {
    public static final String GROUP_ID = "de.softinva.multitimer.groupId";
    public static final String TIMER_ID = "de.softinva.multitimer.timerId";

    DetailedTimerInfoViewModel model;

    public static void startNewActivity(String groupId, String timerId, Context context) {
        Intent intent = new Intent(context, DetailedTimerInfoActivity.class);
        intent.putExtra(DetailedTimerInfoActivity.GROUP_ID, groupId);
        intent.putExtra(DetailedTimerInfoActivity.TIMER_ID, timerId);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DetailedTimerInfoActivityBinding binding = DataBindingUtil.setContentView(this, R.layout.detailed_timer_info_activity);

        model = ViewModelProviders.of(this).get(DetailedTimerInfoViewModel.class);

        Intent intent = getIntent();
        String groupId = intent.getStringExtra(GROUP_ID);
        String timerId = intent.getStringExtra(TIMER_ID);

        MutableLiveData<RunningTimer> runningTimer$ = model.getTimer(groupId, timerId);

        runningTimer$.observe(this, rtimer -> {
            setTitle(rtimer.getTimer().title);
            binding.setRunningTimer(rtimer);
        });

    }
}
