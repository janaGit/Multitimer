package de.softinva.multitimer.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;


import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.SavedStateVMFactory;
import androidx.lifecycle.ViewModelProvider;

import de.softinva.multitimer.R;
import de.softinva.multitimer.classes.AppTabsActivity;
import de.softinva.multitimer.databinding.ActivityTimerGroupBinding;
import de.softinva.multitimer.fragments.list.running.RunningTimerList;
import de.softinva.multitimer.fragments.list.timer.DetailedTimerList;
import de.softinva.multitimer.model.TIMER_GROUP_ACTIVITY_TABS;


public class TimerGroupActivity extends AppTabsActivity<TimerGroupViewModel> {
    public static final String GROUP_ID = "de.softinva.multitimer.groupId";

    public static void startNewActivity(String groupId, Context context) {
        Intent intent = new Intent(context, TimerGroupActivity.class);
        intent.putExtra(TimerGroupActivity.GROUP_ID, groupId);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setClassSpecificObjects() {
        super.setClassSpecificObjects();
        setGroupId();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.timer_group_activity_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    protected void setGroupId() {
        String groupId = model.getTimerGroupId$().getValue();

        if (groupId == null) {

            groupId = intent.getStringExtra(GROUP_ID);

            if (groupId != null) {
                model.getTimerGroupId$().setValue(groupId);
            } else {
                throw new Error("groupId is null !");
            }
        }
    }
    @Override
    protected void setActionBar() {
        setSupportActionBar(((ActivityTimerGroupBinding)binding).appBar);
    }
    @Override
    protected void setTitle() {
        model.getTimerGroup( model.getTimerGroupId$().getValue()).observe(this, timerGroup -> {
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
    protected void setBinding() {
        binding =  DataBindingUtil.setContentView(this, R.layout.activity_timer_group);
    }

    @Override
    protected void setActiveTab() {
        if (model.getActiveTab().getValue() == null) {
            model.getActiveTab().setValue(TIMER_GROUP_ACTIVITY_TABS.List);
        }
    }

}
