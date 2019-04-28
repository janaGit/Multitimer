package de.softinva.multitimer.classes;


import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.Transformations;

import de.softinva.multitimer.model.RunningTimer;
import de.softinva.multitimer.repository.TimerRepository;

public abstract class AbstractDetailedTimerViewModel extends AppViewModel {
    private MutableLiveData<String> timerGroupId;
    private MutableLiveData<String> timerId;
    protected MutableLiveData<RunningTimer> timer;

    public AbstractDetailedTimerViewModel(SavedStateHandle savedStateHandle) {
        super(savedStateHandle);
    }


    public MutableLiveData<String> getTimerGroupId() {
        if (timerGroupId == null) {
            timerGroupId = state.getLiveData("groupId");
        }
        return timerGroupId;
    }

    public MutableLiveData<String> getTimerId() {
        if (timerId == null) {
            timerId = state.getLiveData("timerId");
        }
        return timerId;
    }

    public MutableLiveData<RunningTimer> getTimer(String groupId, String timerId) {
        if (timer == null) {
            timer = (MutableLiveData<RunningTimer>) Transformations.map(TimerRepository.getInstance().getTimerGroup(groupId), timerGroup-> timerGroup.getTimerMapByTimerId().get(timerId));
        }
        return timer;
    }
}
