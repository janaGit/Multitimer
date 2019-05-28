package de.softinva.multitimer.activities.timergroup.add;

import android.content.Context;
import android.content.Intent;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.SavedStateVMFactory;
import androidx.lifecycle.ViewModelProvider;

import de.softinva.multitimer.R;
import de.softinva.multitimer.activities.timergroup.AddEditTimerGroupViewObject;
import de.softinva.multitimer.classes.abstract_classes.AppActivity;
import de.softinva.multitimer.databinding.ActivityAddeditTimerGroupBinding;
import de.softinva.multitimer.model.TimerGroup;

public class AddTimerGroupActivity extends AppActivity<AddTimerGroupViewModel> {
    public static void startNewActivity(Context context) {
        Intent intent = new Intent(context, AddTimerGroupActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void setClassSpecificObjects() {
        TimerGroup timerGroup = model.createNewTimerGroup();
        timerGroup.setTitle(getString(R.string.new_timer_group));
        timerGroup.setDescription(getString(R.string.description));
    }

    @Override
    protected void setModel() {
        model = new ViewModelProvider(this, new SavedStateVMFactory(this))
                .get(AddTimerGroupViewModel.class);
    }

    @Override
    protected void setBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_addedit_timer_group);
    }

    @Override
    protected void setTitle() {
        setTitle(model.timerGroup.getTitle());
    }

    @Override
    protected void setActionBar() {
        setSupportActionBar(((ActivityAddeditTimerGroupBinding) binding).appBar);
    }

    @Override
    protected void setViewObject() {
        viewObject = new AddEditTimerGroupViewObject(false, model.getTimerGroup());
    }

    @Override
    protected void setHomeUpButton() {

    }
}
