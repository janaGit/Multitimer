package de.softinva.multitimer.activities.detailedtimer;


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

    public AbstractDetailedTimerViewModel(SavedStateHandle savedStateHandle) {
        super(savedStateHandle);
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
            runningTimer$ = (MutableLiveData<RunningTimer>) Transformations.map(TimerRepository.getInstance().getTimerGroup(groupId), timerGroup->
                    timerGroup.getTimerMapByTimerId().get(timerId));
        }
        return runningTimer$;
    }
}
