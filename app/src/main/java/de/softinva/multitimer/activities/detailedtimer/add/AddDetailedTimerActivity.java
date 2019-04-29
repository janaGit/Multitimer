package de.softinva.multitimer.activities.detailedtimer.add;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.SavedStateVMFactory;
import androidx.lifecycle.ViewModelProvider;

import de.softinva.multitimer.R;
import de.softinva.multitimer.classes.AbstractDetailedTimerActivity;
import de.softinva.multitimer.classes.AppActivity;
import de.softinva.multitimer.databinding.ActivityAddeditDetailedTimerBinding;
import de.softinva.multitimer.model.DetailedTimer;
import de.softinva.multitimer.model.RunningTimer;
import de.softinva.multitimer.model.Timer;

public class AddDetailedTimerActivity extends AppActivity<AddDetailedTimerViewModel> {
    static final String ACTION_ADD = "de.softinva.multitimer.AddDetailedTimerActivity.StartActivityAdd";
    public static final String GROUP_ID = "de.softinva.multitimer.groupId";

    public static void startNewActivity(String groupId, Context context) {
        Intent intent = new Intent(context, AddDetailedTimerActivity.class);
        intent.setAction(AddDetailedTimerActivity.ACTION_ADD);
        intent.putExtra(AddDetailedTimerActivity.GROUP_ID, groupId);
        context.startActivity(intent);
    }

    @Override
    protected void setActionBar() {
        setSupportActionBar(((ActivityAddeditDetailedTimerBinding) binding).appBar);
    }

    @Override
    protected void setModel() {
        model = new ViewModelProvider(this, new SavedStateVMFactory(this))
                .get(AddDetailedTimerViewModel.class);
    }

    @Override
    protected void setBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_addedit_detailed_timer);
    }

    @Override
    protected void setTitle() {
        setTitle(model.detailedTimer.getTitle());
    }

    @Override
    protected void setClassSpecificObjects() {
        DetailedTimer timer = createNewDetailedTimer();
        model.getTimerId().setValue(timer.getId());
    }

    protected DetailedTimer createNewDetailedTimer() {
        DetailedTimer timer = model.createNewDetailedTimer(getIntent().getStringExtra(GROUP_ID));
        timer.setTitle(getString(R.string.new_detailed_timer));
        timer.setDescription(getString(R.string.description));
        return timer;
    }

}
