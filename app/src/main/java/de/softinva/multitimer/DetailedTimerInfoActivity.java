package de.softinva.multitimer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;

import androidx.appcompat.app.ActionBar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateVMFactory;
import androidx.lifecycle.ViewModelProvider;

import de.softinva.multitimer.classes.AppActivity;
import de.softinva.multitimer.databinding.ActivityDetailedTimerInfoBinding;
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

        ActivityDetailedTimerInfoBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_detailed_timer_info);
        setSupportActionBar(binding.appBar);

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        model = new ViewModelProvider(this, new SavedStateVMFactory(this))
                .get(DetailedTimerInfoViewModel.class);

        setGroupId();
        setTimerId();

        MutableLiveData<RunningTimer> runningTimer$ = model.getTimer(model.getTimerGroupId().getValue(), model.getTimerId().getValue());

        runningTimer$.observe(this, rtimer -> {
            setTitle(rtimer.getTimer().title);
            binding.setRunningTimer(rtimer);
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.detailed_timer_info_menu, menu);
        return true;
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

    protected void setTimerId() {
        String timerId = model.getTimerId().getValue();

        if (timerId == null) {

            timerId = getIntent().getStringExtra(TIMER_ID);

            if (timerId != null) {
                model.getTimerId().setValue(timerId);
            } else {
                throw new Error("timerId is null !");
            }
        }
    }
}
