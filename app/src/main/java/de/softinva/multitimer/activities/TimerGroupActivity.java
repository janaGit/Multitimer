package de.softinva.multitimer.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;


import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.SavedStateVMFactory;
import androidx.lifecycle.ViewModelProvider;

import de.softinva.multitimer.R;
import de.softinva.multitimer.activities.detailedtimer.add.AddDetailedTimerActivity;
import de.softinva.multitimer.activities.detailedtimer.edit.EditDetailedTimerActivity;
import de.softinva.multitimer.classes.AppTabsActivity;
import de.softinva.multitimer.databinding.ActivityTimerGroupBinding;
import de.softinva.multitimer.fragments.list.running.RunningTimerList;
import de.softinva.multitimer.fragments.list.timer.DetailedTimerList;
import de.softinva.multitimer.model.TIMER_GROUP_ACTIVITY_TABS;
import de.softinva.multitimer.repository.TimerRepository;


public class TimerGroupActivity extends AppTabsActivity<TimerGroupViewModel> {
    public static final String GROUP_ID = "de.softinva.multitimer.groupId";

    public static void startNewActivity(String groupId, Context context) {
        Intent intent = new Intent(context, TimerGroupActivity.class);
        intent.putExtra(TimerGroupActivity.GROUP_ID, groupId);
        context.startActivity(intent);
    }

    public static void startNewActivity(String groupId, Context context, boolean clearBackStack) {
        Intent intent = new Intent(context, TimerGroupActivity.class);
        intent.putExtra(TimerGroupActivity.GROUP_ID, groupId);
        if (clearBackStack) {
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        }
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setViewObject() {
        viewObject = new TimerGroupViewObject(model.getTimerGroup(model.getTimerGroupId$().getValue()));
    }

    @Override
    protected void setClassSpecificObjects() {
        super.setClassSpecificObjects();
        setGroupId();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.timer_group_activity_menu, menu);

        model.doDisabledTimersExists().observe(this, doDisabledTimersExists -> {
            if (doDisabledTimersExists) {
                menu.findItem(R.id.action_enable_all_timer).setVisible(true);
            } else {
                menu.findItem(R.id.action_enable_all_timer).setVisible(false);
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    protected void setGroupId() {
        String groupId = model.getTimerGroupId$().getValue();

        if (groupId == null) {

            groupId = getIntent().getStringExtra(GROUP_ID);

            if (groupId != null) {
                model.getTimerGroupId$().setValue(groupId);
            } else {
                throw new Error("groupId is null !");
            }
        }
    }

    @Override
    protected void setActionBar() {
        setSupportActionBar(((ActivityTimerGroupBinding) binding).appBar);
    }

    @Override
    protected void setTitle() {
        model.getTimerGroup(model.getTimerGroupId$().getValue()).observe(this, timerGroup -> {
            setTitle(timerGroup.getTitle());
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
        binding = DataBindingUtil.setContentView(this, R.layout.activity_timer_group);
    }

    @Override
    protected void setActiveTab() {
        if (model.getActiveTab().getValue() == null) {
            model.getActiveTab().setValue(TIMER_GROUP_ACTIVITY_TABS.List);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_new_detailed_timer:
                AddDetailedTimerActivity.startNewActivity(model.getTimerGroupId$().getValue(), this);
                return true;
            case R.id.action_enable_all_timer:
                new TimerRepository(getApplication()).enableAllDetailedTimer(model.getTimerGroupId$().getValue());
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }
}
