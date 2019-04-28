package de.softinva.multitimer.activities.timergroup.addedit;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateVMFactory;
import androidx.lifecycle.ViewModelProvider;


import de.softinva.multitimer.R;
import de.softinva.multitimer.classes.AbstractTimerGroupActivity;
import de.softinva.multitimer.databinding.ActivityAddeditTimerGroupBinding;
import de.softinva.multitimer.model.TimerGroup;

public class AddEditTimerGroupActivity extends AbstractTimerGroupActivity<AddEditTimerGroupViewModel> {
    static final String ACTION_EDIT = "de.softinva.multitimer.AddEditTimerGroupActivity.StartActivityEdit";
    static final String ACTION_ADD = "de.softinva.multitimer.AddEditTimerGroupActivity.StartActivityAdd";
    protected String action;

    public static void startNewActivityEdit(String groupId, Context context) {
        Intent intent = new Intent(context, AddEditTimerGroupActivity.class);
        intent.setAction(AddEditTimerGroupActivity.ACTION_EDIT);
        intent.putExtra(AddEditTimerGroupActivity.GROUP_ID, groupId);
        context.startActivity(intent);
    }

    public static void startNewActivityAdd(Context context) {
        Intent intent = new Intent(context, AddEditTimerGroupActivity.class);
        intent.setAction(AddEditTimerGroupActivity.ACTION_ADD);
        context.startActivity(intent);
    }

    @Override
    protected void setClassSpecificObjects() {
        action = getIntent().getAction();
        switch (action) {
            case ACTION_EDIT:
                super.setClassSpecificObjects();
                break;
            case ACTION_ADD:
                MutableLiveData<TimerGroup> timerGroup = model.createNewTimerGroup();
                timerGroup.getValue().setTitle(getString(R.string.new_timer_group));
                break;
            default:
                throw new Error("Intent with action " + action + " not supported!");
        }
        super.setClassSpecificObjects();
    }

    @Override
    protected void setModel() {
        model = new ViewModelProvider(this, new SavedStateVMFactory(this))
                .get(AddEditTimerGroupViewModel.class);
    }

    @Override
    protected void setBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_addedit_timer_group);
    }

    @Override
    protected void setActionBar() {
        setSupportActionBar(((ActivityAddeditTimerGroupBinding) binding).appBar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (action == ACTION_EDIT) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.timer_group_addedit_menu, menu);
        }
        return true;
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
}
