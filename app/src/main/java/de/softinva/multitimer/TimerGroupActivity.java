package de.softinva.multitimer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;


import androidx.fragment.app.Fragment;
import androidx.lifecycle.SavedStateVMFactory;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import de.softinva.multitimer.classes.AppTabsActivity;
import de.softinva.multitimer.fragments.list.running.RunningTimerList;
import de.softinva.multitimer.fragments.list.timer.DetailedTimerList;
import de.softinva.multitimer.model.TIMER_GROUP_ACTIVITY_TABS;


public class TimerGroupActivity extends AppTabsActivity {
    public static final String GROUP_ID = "de.softinva.multitimer.groupId";

    public static void startNewActivity(String groupId, Context context) {
        Intent intent = new Intent(context, TimerGroupActivity.class);
        intent.putExtra(TimerGroupActivity.GROUP_ID, groupId);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer_group);

        setViewIfOrientationLandscape();

        setGroupId();
        setTitle();
    }

    protected void setGroupId() {
        TimerGroupViewModel model = (TimerGroupViewModel) this.model;
        String groupId = model.getTimerGroupId().getValue();

        if (groupId == null) {

            groupId = intent.getStringExtra(GROUP_ID);

            if (groupId != null) {
                model.getTimerGroupId().setValue(groupId);
            } else {
                throw new Error("groupId is null !");
            }
        }
    }

    protected void setTitle() {
        ((TimerGroupViewModel) model).getTimerGroup().observe(this, timerGroup -> {
            setTitle(timerGroup.title);
        });
    }

    @Override
    protected Fragment selectFragment() {
        Fragment fragment;

        TIMER_GROUP_ACTIVITY_TABS tab = (TIMER_GROUP_ACTIVITY_TABS) model.getActiveTab().getValue();
        switch (tab) {
            case List:
                fragment = new DetailedTimerList();
                break;
            case Running:
                fragment = new RunningTimerList();
                break;
            default:
                throw new Error("active tab is not supported!");
        }
        return fragment;
    }

    @Override
    protected void setModel() {
        model = new ViewModelProvider(this, new SavedStateVMFactory(this))
                .get(TimerGroupViewModel.class);
    }

    @Override
    protected void setActiveTab() {
        if (model.getActiveTab().getValue() == null) {
            model.getActiveTab().setValue(TIMER_GROUP_ACTIVITY_TABS.List);
        }
    }

}
