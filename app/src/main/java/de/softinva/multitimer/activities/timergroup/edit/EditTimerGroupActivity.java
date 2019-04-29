package de.softinva.multitimer.activities.timergroup.edit;

import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.SavedStateVMFactory;
import androidx.lifecycle.ViewModelProvider;


import de.softinva.multitimer.R;
import de.softinva.multitimer.activities.timergroup.AbstractTimerGroupActivity;
import de.softinva.multitimer.databinding.ActivityAddeditTimerGroupBinding;
import de.softinva.multitimer.model.TimerGroup;

public class EditTimerGroupActivity extends AbstractTimerGroupActivity<EditTimerGroupViewModel> {

    public static void startNewActivityEdit(String groupId, Context context) {
        Intent intent = new Intent(context, EditTimerGroupActivity.class);
        intent.putExtra(EditTimerGroupActivity.GROUP_ID, groupId);
        context.startActivity(intent);
    }

    @Override
    protected void setModel() {
        model = new ViewModelProvider(this, new SavedStateVMFactory(this))
                .get(EditTimerGroupViewModel.class);
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
        inflater.inflate(R.menu.timer_group_add_menu, menu);
        return true;
    }
    @Override
    protected void setClassSpecificObjects() {
        super.setClassSpecificObjects();
        timerGroup$.observe(this, timerGroup -> {
            if (model.getTimerGroup().getId() == null) {
                setTimerGroup(timerGroup);
            }
        });
    }

    protected void setTimerGroup(TimerGroup timerGroup) {
        timerGroup.toCopy(model.getTimerGroup());
    }
}
