package de.softinva.multitimer.activities.timergroup.addedit;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.SavedStateVMFactory;
import androidx.lifecycle.ViewModelProvider;


import de.softinva.multitimer.R;
import de.softinva.multitimer.classes.AbstractTimerGroupActivity;
import de.softinva.multitimer.databinding.ActivityAddeditTimerGroupBinding;
import de.softinva.multitimer.databinding.ActivityTimerGroupInfoBinding;

public class AddEditTimerGroupActivity extends AbstractTimerGroupActivity<AddEditTimerGroupViewModel> {
    public static final String GROUP_ID = "de.softinva.multitimer.groupId";

    public static void startNewActivityEdit(String groupId, String timerId, Context context) {
        Intent intent = new Intent(context, AddEditTimerGroupActivity.class);
        intent.putExtra(AddEditTimerGroupActivity.GROUP_ID, groupId);
        context.startActivity(intent);
    }
    public static void startNewActivityAdd(String groupId, String timerId, Context context) {
        Intent intent = new Intent(context, AddEditTimerGroupActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.timer_group_info_menu, menu);
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
