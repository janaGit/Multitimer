package de.softinva.multitimer.fragments.dialogaddtemptimer;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;

import java.util.TreeMap;

import de.softinva.multitimer.R;
import de.softinva.multitimer.classes.abstract_classes.AppViewModel;
import de.softinva.multitimer.model.RunningTimer;
import de.softinva.multitimer.model.TempTimer;
import de.softinva.multitimer.repository.TimerRepository;

public class AddTempTimerDialogViewModel extends AppViewModel {
    TempTimer tempTimer;
    private MutableLiveData<Integer> durationinSec$;

    public AddTempTimerDialogViewModel(Application application, SavedStateHandle savedStateHandle) {
        super(application, savedStateHandle);
        TreeMap<Long, RunningTimer> map = TimerRepository.getInstance(getApplication()).getRunningTimerByFinishTimeMap().getValue();
        tempTimer = new TempTimer();
        tempTimer.setTitle(application.getString(R.string.temp_timer) + " " + (map.size() + 1));
        getDurationinSec$().setValue(30);
    }

    public MutableLiveData<Integer> getDurationinSec$() {
        if (durationinSec$ == null) {
            durationinSec$ = state.getLiveData("durationInSec");
        }
        return durationinSec$;
    }
}
