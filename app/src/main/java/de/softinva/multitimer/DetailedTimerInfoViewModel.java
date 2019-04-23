package de.softinva.multitimer;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import de.softinva.multitimer.model.RunningTimer;
import de.softinva.multitimer.repository.TimerRepository;

public class DetailedTimerInfoViewModel extends ViewModel {
    SavedStateHandle state;
    private MutableLiveData<String> timerGroupId;
    private MutableLiveData<String> timerId;
    protected MutableLiveData<RunningTimer> timer;


    public DetailedTimerInfoViewModel(SavedStateHandle savedStateHandle) {
        state = savedStateHandle;
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
            timer = (MutableLiveData<RunningTimer>) Transformations.map(TimerRepository.getInstance().getTimerGroup(groupId), timerGroup-> timerGroup.timerMapByTimerId.get(timerId));
        }
        return timer;
    }
}
