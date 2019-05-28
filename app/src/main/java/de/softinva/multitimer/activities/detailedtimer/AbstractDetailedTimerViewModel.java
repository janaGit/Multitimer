package de.softinva.multitimer.activities.detailedtimer;


import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.Transformations;

import de.softinva.multitimer.classes.abstract_classes.AppViewModel;
import de.softinva.multitimer.model.RunningTimer;
import de.softinva.multitimer.repository.TimerRepository;

public abstract class AbstractDetailedTimerViewModel extends AppViewModel {
    private MutableLiveData<String> timerGroupId$;
    private MutableLiveData<String> timerId$;
    private LiveData<RunningTimer> runningTimer$;

    public AbstractDetailedTimerViewModel(Application application, SavedStateHandle savedStateHandle) {
        super(application, savedStateHandle);
    }


    public MutableLiveData<String> getTimerGroupId() {
        if (timerGroupId$ == null) {
            timerGroupId$ = state.getLiveData("groupId");
        }
        return timerGroupId$;
    }

    public MutableLiveData<String> getTimerId() {
        if (timerId$ == null) {
            timerId$ = state.getLiveData("timerId");
        }
        return timerId$;
    }

    public LiveData<RunningTimer> getRunningTimer(String groupId, String timerId) {
        if (runningTimer$ == null) {
            runningTimer$ = Transformations.map(new TimerRepository(getApplication()).getDetailedTimer(groupId, timerId), timer ->
                    new RunningTimer(timer)
            );
        }
        return runningTimer$;
    }
}
