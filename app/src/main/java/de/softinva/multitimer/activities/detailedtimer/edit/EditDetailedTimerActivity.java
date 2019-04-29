package de.softinva.multitimer.activities.detailedtimer.edit;

import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.SavedStateVMFactory;
import androidx.lifecycle.ViewModelProvider;

import de.softinva.multitimer.R;
import de.softinva.multitimer.classes.AbstractDetailedTimerActivity;
import de.softinva.multitimer.databinding.ActivityAddeditDetailedTimerBinding;
import de.softinva.multitimer.model.DetailedTimer;
import de.softinva.multitimer.model.RunningTimer;
import de.softinva.multitimer.model.Timer;

public class EditDetailedTimerActivity extends AbstractDetailedTimerActivity<EditDetailedTimerViewModel> {
    static final String ACTION_EDIT = "de.softinva.multitimer.AddDetailedTimerActivity.StartActivityEdit";

    public static void startNewActivity(String groupId, String timerId, Context context) {
        Intent intent = new Intent(context, EditDetailedTimerActivity.class);
        intent.setAction(EditDetailedTimerActivity.ACTION_EDIT);
        intent.putExtra(EditDetailedTimerActivity.GROUP_ID, groupId);
        intent.putExtra(EditDetailedTimerActivity.TIMER_ID, timerId);
        context.startActivity(intent);
    }

    @Override
    protected void setActionBar() {
        setSupportActionBar(((ActivityAddeditDetailedTimerBinding) binding).appBar);
    }

    @Override
    protected void setModel() {
        model = new ViewModelProvider(this, new SavedStateVMFactory(this))
                .get(EditDetailedTimerViewModel.class);
    }

    @Override
    protected void setBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_addedit_detailed_timer);
    }

    @Override
    protected void setClassSpecificObjects() {
        super.setClassSpecificObjects();
        runningTimer$.observe(this, runningTimer -> {
            if (model.getDetailedTimer().getId() == null) {
                setDetailedTimer(runningTimer);
            }
        });


    }

    protected void setDetailedTimer(RunningTimer runningTimer) {
        Timer timerFromRunning = runningTimer.getTimer();
        if (!(timerFromRunning instanceof DetailedTimer)) {
            throw new Error("Timer should be of instance DetailedTimer but is not!");
        }
        ((DetailedTimer) timerFromRunning).toCopy(model.getDetailedTimer());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.detailed_timer_add_menu, menu);
        return true;
    }


}
