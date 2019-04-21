package de.softinva.multitimer;

import android.os.Bundle;


import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import de.softinva.multitimer.classes.AppTabsActivity;
import de.softinva.multitimer.fragments.list.running.RunningTimerList;
import de.softinva.multitimer.fragments.list.timer.DetailedTimerList;
import de.softinva.multitimer.model.TIMER_GROUP_ACTIVITY_TABS;


public class TimerGroupActivity extends AppTabsActivity {
    public static final String GROUP_ID = "de.softinva.multitimer.groupId";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer_group);

        setViewIfOrientationLandscape();

        setGroupIdAndTitle();
    }

    protected void setGroupIdAndTitle() {
        String groupId = intent.getStringExtra(GROUP_ID);

        if (groupId != "") {
            TimerGroupViewModel model = (TimerGroupViewModel) this.model;
            model.getTimerGroupId().setValue(groupId);
            model.getTimerGroup().observe(this, timerGroup->{
                setTitle(timerGroup.title);
            });

        } else {
            throw new Error("groupId is '' !");
        }
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
        model = ViewModelProviders.of(this).get(TimerGroupViewModel.class);
    }

    @Override
    protected void setActiveTab() {
        if(model.getActiveTab().getValue() == null){
            model.getActiveTab().setValue(TIMER_GROUP_ACTIVITY_TABS.List);
        }
    }

}
