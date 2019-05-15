package de.softinva.multitimer.fragments.addTempTimerDialog;

import android.app.Application;

import androidx.lifecycle.SavedStateHandle;

import java.util.TreeMap;

import de.softinva.multitimer.R;
import de.softinva.multitimer.classes.AppViewModel;
import de.softinva.multitimer.model.RunningTimer;
import de.softinva.multitimer.model.TempTimer;
import de.softinva.multitimer.repository.TimerRepository;

public class AddTempTimerDialogViewModel extends AppViewModel {
    TempTimer tempTimer;

    public AddTempTimerDialogViewModel(Application application, SavedStateHandle savedStateHandle) {
        super(application, savedStateHandle);
        TreeMap<Long, RunningTimer> map = new TimerRepository(getApplication()).getRunningTimerByFinishTimeMap().getValue();
        tempTimer = new TempTimer();
        tempTimer.setTitle(application.getString(R.string.temp_timer) + " " + (map.size() + 1));
    }
}