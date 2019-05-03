package de.softinva.multitimer.activities.detailedtimer;


import android.app.Application;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.Transformations;

import de.softinva.multitimer.classes.AppViewModel;
import de.softinva.multitimer.model.RunningTimer;
import de.softinva.multitimer.repository.TimerRepository;

public abstract class AbstractDetailedTimerViewModel extends AppViewModel {
    private MutableLiveData<String> timerGroupId$;
    private MutableLiveData<String> timerId$;
    private MutableLiveData<RunningTimer> runningTimer$;

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

    public MutableLiveData<RunningTimer> getRunningTimer(String groupId, String timerId) {
        if (runningTimer$ == null) {
            runningTimer$ = (MutableLiveData<RunningTimer>) Transformations.map(new TimerRepository(getApplication()).getTimerGroup(groupId), timerGroup ->
                    timerGroup.getTimerMapByTimerId().get(timerId));
        }
        return runningTimer$;
    }
}
